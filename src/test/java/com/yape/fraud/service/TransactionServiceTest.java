package com.yape.fraud.service;

import com.yape.fraud.dto.TransactionDto;
import com.yape.fraud.dto.TransactionMapper;
import com.yape.fraud.dto.TransactionRequest;
import com.yape.fraud.dto.TransactionStatusDto;
import com.yape.fraud.enums.TransactionStatus;
import com.yape.fraud.model.Transaction;
import com.yape.fraud.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testCreateTransaction() {

        TransactionRequest request = new TransactionRequest();
        request.setAccountExternalIdDebit("Guid");
        request.setAccountExternalIdCredit("Guid");
        request.setTranferTypeId(1);
        request.setValue(120);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setValue(120);

        TransactionStatusDto transactionStatusDto = new TransactionStatusDto();
        transactionStatusDto.setName(TransactionStatus.PENDING.name());
        transactionDto.setTransactionStatus(transactionStatusDto);

        when(transactionMapper.toModel(any(TransactionRequest.class))).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDto);

        TransactionDto result = transactionService.createTransaction(request);

        Assert.assertEquals("PENDING", result.getTransactionStatus().getName());
    }

    @Test
    public void testGetTransaction() {

        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setValue(120);
        transaction.setTransactionStatus(TransactionStatus.APPROVED);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setValue(120);
        TransactionStatusDto transactionStatusDto = new TransactionStatusDto();
        transactionStatusDto.setName(TransactionStatus.APPROVED.name());
        transactionDto.setTransactionStatus(transactionStatusDto);

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDto);

        TransactionDto result = transactionService.getTransaction(transactionId);

        Assert.assertNotNull(result);
        Assert.assertEquals("APPROVED", result.getTransactionStatus().getName());
    }
}

