package com.yape.fraud.service;

import com.yape.fraud.dto.TransactionDto;
import com.yape.fraud.dto.TransactionMapper;
import com.yape.fraud.dto.TransactionRequest;
import com.yape.fraud.enums.TransactionStatus;
import com.yape.fraud.model.Transaction;
import com.yape.fraud.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionMapper mapper;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public TransactionDto createTransaction(TransactionRequest request) {
        Transaction newTransaction = mapper.toModel(request);
        newTransaction.setTransactionStatus(TransactionStatus.PENDING);
        Transaction savedTransaction = repository.save(newTransaction);

        kafkaProducerService.sendTransactionUpdate(savedTransaction.getId().toString());

        return mapper.toDto(repository.save(savedTransaction));
    }

    public TransactionDto getTransaction(Long id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }
}
