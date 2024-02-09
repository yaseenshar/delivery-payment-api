package com.paymentapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "last_payment_date")
    private Timestamp lastPaymentDate;

    @Column(name = "created_on")
    private Timestamp createdOn;

}
