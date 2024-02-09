package com.paymentapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "credit_card")
    private String creditCard;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    public enum PaymentType {
        ONLINE,
        OFFLINE
    }

    @PrePersist
    protected void onCreate() {
        // Perform any actions you need before the entity is persisted
        account.setLastPaymentDate(createdOn);
    }
}
