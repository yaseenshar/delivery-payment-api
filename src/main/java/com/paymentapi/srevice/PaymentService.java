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
    private final LogService logService;

    public PaymentService(PaymentRepository paymentRepository, RestTemplate restTemplate, AppConfig appConfig,
                          AppProperties appProperties, LogService logService, Mapper mapper) {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
        this.appConfig = appConfig;
        this.logService = logService;
        this.mapper = mapper;
    }

    public void savePayment(com.paymentapi.model.Payment payment) throws SQLException {

        try {
            Payment paymentEntity = mapper.mapToPaymentEntity(payment);
            paymentRepository.save(paymentEntity);
        } catch (Exception ex) {
            throw new SQLException(ex.getMessage());
        }

    }

    public void addPayment(com.paymentapi.model.Payment payment, boolean validatePayment) {

        try {

            if (validatePayment) {
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

                if (statusCode.equals(HttpStatus.OK)) {

                    savePayment(payment);
                }
                else if (responseEntity.getBody() != null) {

                    logService.logErrors(responseEntity.getBody().getPaymentId(), responseEntity.getBody().getErrorType(), responseEntity.getBody().getErrorDescription());
                }
            } else {
                savePayment(payment);
            }
        } catch (SQLException e) {
            // Handle other SQLExceptions
            log.error("SQLException: " + e.getMessage());

            logService.logErrors(payment.getPaymentId(), AppConstant.EXCEPTION_DATABASE, e.getMessage());
        } catch (RestClientException e) {
            // Handle Rest Client (e.g., timeout, connectivity issues)
            log.error("Error: Network issue - " + e.getMessage());

            logService.logErrors(payment.getPaymentId(), AppConstant.EXCEPTION_NETWORK, e.getMessage());
        } catch (Exception e) {
            // Handle general exceptions
            log.error("Exception: " + e.getMessage());

            logService.logErrors(payment.getPaymentId(), AppConstant.EXCEPTION_REST, e.getMessage());
        }
    }
}
