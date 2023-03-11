package com.eteration.simplebanking.controller.dto.res;

import com.eteration.simplebanking.domain.model.account.Transaction;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TransactionResponse(Double amount,
                                  String type,
                                  LocalDateTime createdDate) {
    @JsonCreator
    public TransactionResponse {
    }

    public static TransactionResponse from(Transaction transaction) {
        return TransactionResponse.builder()
                .amount(transaction.getAmount().amount())
                .type(transaction.getType())
                .createdDate(transaction.getCreatedDate())
                .build();
    }

    public static List<TransactionResponse> from(List<Transaction> transactions) {
        return transactions.stream().map(TransactionResponse::from).toList();
    }
}
