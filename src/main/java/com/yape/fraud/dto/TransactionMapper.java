package com.yape.fraud.dto;

import com.yape.fraud.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "transactionExternalId", source = "id")
    @Mapping(target = "transactionStatus.name", source = "transactionStatus")
    @Mapping(target = "transactionType.name", expression = "java(mapTransactionType(transaction.getTranferTypeId()))")
    TransactionDto toDto(Transaction transaction);

    Transaction toModel(TransactionRequest request);

    default String mapTransactionType(int tranferTypeId) {
        switch (tranferTypeId) {
            case 1: return "Transfer Type 1";
            case 2: return "Transfer Type 2";
            default: return "Unknown Type";
        }
    }
}
