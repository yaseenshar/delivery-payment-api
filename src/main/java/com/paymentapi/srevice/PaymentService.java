package com.paymentapi.srevice;

import com.paymentapi.config.AppConfig;
import com.paymentapi.config.AppProperties;
import com.paymentapi.entity.Payment;
import com.paymentapi.mapper.Mapper;
import com.paymentapi.model.LogErrorRequest;
import com.paymentapi.repository.PaymentRepository;
import com.paymentapi.util.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;

@Service
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final Mapper mapper;
    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    private final AppConfig appConfig;

    public PaymentService(PaymentRepository paymentRepository, RestTemplate restTemplate, AppConfig appConfig, AppProperties appProperties, Mapper mapper) {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
        this.appConfig = appConfig;
        this.mapper = mapper;
    }

    public void addPayment(com.paymentapi.model.Payment paymentDTO) throws SQLException {

        try {
            Payment payment = mapper.mapToPaymentEntity(paymentDTO);
            paymentRepository.save(payment);
        } catch (Exception ex) {
            throw new SQLException(ex.getMessage());
        }

    }

    public void validatePayment(com.paymentapi.model.Payment payment) {

        try {

            String url = new StringBuilder().append(appProperties.getApiUrl()).append(AppConstant.TargetURI.PAYMENT).toString();

            // Make an HTTP GET request using the exchange method
            ResponseEntity<LogErrorRequest> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    appConfig.getHTTPEntity(payment),
                    LogErrorRequest.class
            );

            // Inspect the response status and body
            HttpStatusCode statusCode = responseEntity.getStatusCode();

            if (statusCode.equals(HttpStatus.OK))
                addPayment(payment);
             else
                logErrors(responseEntity.getBody());


        } catch (SQLException e) {
            // Handle other SQLExceptions
            log.error("SQLException: " + e.getMessage());
            LogErrorRequest logErrorRequest = new LogErrorRequest(payment.getPaymentId(), AppConstant.EXCEPTION_DATABASE, e.getMessage());

            logErrors(logErrorRequest);
        } catch (RestClientException e) {
            // Handle Rest Client (e.g., timeout, connectivity issues)
            log.error("Error: Network issue - " + e.getMessage());

            LogErrorRequest logErrorRequest = new LogErrorRequest(payment.getPaymentId(), AppConstant.EXCEPTION_NETWORK, e.getMessage());

            logErrors(logErrorRequest);
        } catch (Exception e) {
            // Handle general exceptions
            log.error("Exception: " + e.getMessage());

            LogErrorRequest logErrorRequest = new LogErrorRequest(payment.getPaymentId(), AppConstant.EXCEPTION_REST, e.getMessage());

            logErrors(logErrorRequest);
        }


    }


    private void logErrors(LogErrorRequest logErrorRequest) {
        try {

            // Create a request entity with the headers
            HttpEntity<LogErrorRequest> requestEntity = new HttpEntity<>(logErrorRequest, appConfig.headerConfig());

            String url = new StringBuilder().append(appProperties.getApiUrl()).append(AppConstant.TargetURI.LOG).toString();

            // Make an HTTP GET request using the exchange method
            ResponseEntity<LogErrorRequest> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    LogErrorRequest.class
            );

            HttpStatusCode statusCode = responseEntity.getStatusCode();
        }
        catch (Exception exception) {
            log.error("LogAPI Exception: " + exception.getMessage());
        }

    }
}
