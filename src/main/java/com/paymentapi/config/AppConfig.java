package com.paymentapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public HttpHeaders headerConfig() {

        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.setContentType(MediaType.APPLICATION_JSON);
        return httpHeader;
    }

    public HttpEntity<?> getHTTPEntity(Object payload){
        if(payload == null) return new HttpEntity<>(headerConfig());
        return new HttpEntity<>(payload, headerConfig());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
