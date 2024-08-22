package com.yape.fraud.mapper;

import com.yape.fraud.dto.TransactionDto;
import com.yape.fraud.dto.TransactionMapperImpl;
import com.yape.fraud.enums.TransactionStatus;
import com.yape.fraud.model.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionMapperTest {

    @InjectMocks
    private TransactionMapperImpl transactionMapper;

    @Test
    public void testToDto() {

        Transaction transaction = new Transaction();
        transaction.setAccountExternalIdDebit("Guid");
        transaction.setAccountExternalIdCredit("Guid");
        transaction.setValue(120);
        transaction.setTransactionStatus(TransactionStatus.APPROVED);

        TransactionDto result = transactionMapper.toDto(transaction);

        Assert.assertNotNull(result);
        Assert.assertEquals("Guid", result.getAccountExternalIdDebit());
        Assert.assertEquals("Guid", result.getAccountExternalIdCredit());
        Assert.assertEquals(120, result.getValue(), 0.01);
        Assert.assertEquals("APPROVED", result.getTransactionStatus().getName());
    }
}
