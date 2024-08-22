package com.yape.fraud.service;

import com.yape.fraud.model.Transaction;
import com.yape.fraud.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private TransactionRepository transactionRepository;

    @KafkaListener(topics = "transaction-topic", groupId = "transaction-group")
    public void consume(String message) {
        Long transactionId = Long.parseLong(message);
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);

        if (transaction != null) {
            transactionRepository.save(transaction);
        }
    }
}
