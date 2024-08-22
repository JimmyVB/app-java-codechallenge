package com.yape.fraud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yape.fraud.dto.TransactionDto;
import com.yape.fraud.dto.TransactionRequest;
import com.yape.fraud.dto.TransactionStatusDto;
import com.yape.fraud.enums.TransactionStatus;
import com.yape.fraud.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void testCreateTransaction() throws Exception {

        TransactionRequest request = new TransactionRequest();
        request.setAccountExternalIdDebit("Guid");
        request.setAccountExternalIdCredit("Guid");
        request.setTranferTypeId(1);
        request.setValue(120);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setValue(120);
        TransactionStatusDto transactionStatusDto = new TransactionStatusDto();
        transactionStatusDto.setName(TransactionStatus.PENDING.name());
        transactionDto.setTransactionStatus(transactionStatusDto);

        when(transactionService.createTransaction(any(TransactionRequest.class))).thenReturn(transactionDto);

        mockMvc.perform(post("/v1/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.value").value(120))
                .andExpect(jsonPath("$.transactionStatus.name").value("PENDING"));
    }

    @Test
    public void testGetTransaction() throws Exception {

        Long transactionId = 1L;
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setValue(120);
        TransactionStatusDto transactionStatusDto = new TransactionStatusDto();
        transactionStatusDto.setName(TransactionStatus.APPROVED.name());
        transactionDto.setTransactionStatus(transactionStatusDto);

        when(transactionService.getTransaction(transactionId)).thenReturn(transactionDto);

        mockMvc.perform(get("/v1/transaction/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(120))
                .andExpect(jsonPath("$.transactionStatus.name").value("APPROVED"));
    }
}

