package com.paymentapi.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentapi.model.Payment;
import com.paymentapi.srevice.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OnlinePaymentConsumer {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "online", groupId = "payment-consumer")
    public void handleOnlinePayment(ConsumerRecord<String, String> record) {

        try {
            Payment paymentDTO = objectMapper.readValue(record.value(), Payment.class);
            paymentService.addPayment(paymentDTO, Boolean.TRUE);

            // Your logic for handling online payment messages
        } catch (Exception e) {
            // Log or handle the exception appropriately
            log.error("Error while getting online payment: {}", e.getMessage());
        }
        
    }


}
