package com.paymentapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppProperties {

    @Value(value = "${rest.api.url}")
    private String apiUrl;
}
