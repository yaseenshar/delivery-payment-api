package com.paymentapi.mapper;

import com.paymentapi.entity.Payment;
import com.paymentapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class Mapper {

    public final AccountRepository accountRepository;

    public Payment mapToPaymentEntity(com.paymentapi.model.Payment paymentDTO) {

        Payment payment = Payment.builder()
                .paymentId(paymentDTO.getPaymentId())
                .account(accountRepository.getReferenceById(paymentDTO.getAccountId()))
                .creditCard(paymentDTO.getCreditCard())
                .amount(paymentDTO.getAmount())
                .paymentType(Payment.PaymentType.valueOf(paymentDTO.getPaymentType().name()))
                .createdOn(new Timestamp(System.currentTimeMillis()))
                .build();

        return payment;
    }
}
