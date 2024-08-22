package com.yape.fraud.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionDto {
    private Long transactionExternalId;
    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private int tranferTypeId;
    private double value;
    private TransactionTypeDto transactionType;
    private TransactionStatusDto transactionStatus;
    private Date createdAt;
}
