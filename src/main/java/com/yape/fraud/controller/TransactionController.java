package com.yape.fraud.controller;

import com.yape.fraud.dto.TransactionDto;
import com.yape.fraud.dto.TransactionRequest;
import com.yape.fraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionRequest transactionRequest) {
        try {
            TransactionDto result = transactionService.createTransaction(transactionRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (ResponseStatusException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", ex.getReason());
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable Long id) {
        TransactionDto result = transactionService.getTransaction(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
