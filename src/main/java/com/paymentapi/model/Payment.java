package com.paymentapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("account_id")
    private Integer accountId;

    @JsonProperty("payment_type")
    private PaymentType paymentType;

    @JsonProperty("credit_card")
    private String creditCard;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("delay")
    private Long createdOn;

    public enum PaymentType {
        @JsonProperty("online")
        ONLINE,
        @JsonProperty("offline")
        OFFLINE
    }
}
