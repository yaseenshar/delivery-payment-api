package com.paymentapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
public class LogErrorRequest {

    @JsonProperty("payment_id")
    private String paymentId;
    @JsonProperty("error_type")
    private String errorType;
    @JsonProperty("error_description")
    private String errorDescription;
    @JsonProperty("created_at")
    private Timestamp createdOn;

    public LogErrorRequest(String paymentId, String errorType, String errorDescription) {
        this.paymentId = paymentId;
        this.errorType = errorType;
        this.errorDescription = errorDescription;
    }

    @Override
    public String toString() {
        return "LogErrorRequest{" +
                "paymentId='" + paymentId + '\'' +
                ", errorType='" + errorType + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
