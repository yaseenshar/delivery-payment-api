package com.paymentapi.srevice;

import com.paymentapi.config.AppConfig;
import com.paymentapi.config.AppProperties;
import com.paymentapi.model.LogErrorRequest;
import com.paymentapi.util.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class LogService {

    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    private final AppConfig appConfig;

    public LogService(RestTemplate restTemplate, AppConfig appConfig, AppProperties appProperties) {
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
        this.appConfig = appConfig;
    }

    public void logErrors(String paymentId, String errorType, String message) {

        try {

            LogErrorRequest logErrorRequest = new LogErrorRequest(paymentId, errorType, message);

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
