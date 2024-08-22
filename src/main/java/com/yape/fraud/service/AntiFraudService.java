package com.yape.fraud.service;

import com.yape.fraud.enums.TransactionStatus;
import com.yape.fraud.model.Transaction;
import com.yape.fraud.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AntiFraudService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @KafkaListener(topics = "transaction-topic", groupId = "fraud-group")
    public void validateTransaction(String transactionId) {
        Long id = Long.parseLong(transactionId);
        Transaction transaction = transactionRepository.findById(id).orElse(null);

        if (transaction != null) {
            if (transaction.getValue() > 1000) {
                transaction.setTransactionStatus(TransactionStatus.REJECTED);
            } else {
                transaction.setTransactionStatus(TransactionStatus.APPROVED);
            }
            transactionRepository.save(transaction);
            kafkaProducerService.sendTransactionUpdate(transactionId);
        }
    }
}

