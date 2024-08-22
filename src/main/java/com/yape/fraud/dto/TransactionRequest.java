package com.yape.fraud.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private int tranferTypeId;
    private double value;
}
