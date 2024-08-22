package com.yape.fraud.model;

import com.yape.fraud.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private int tranferTypeId;
    private double value;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
