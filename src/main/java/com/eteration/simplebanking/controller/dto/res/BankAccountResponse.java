package com.eteration.simplebanking.controller.dto.res;


import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BankAccountResponse(String accountNumber,
                                  String owner,
                                  Double balance,
                                  LocalDateTime createdDate,
                                  List<TransactionResponse> transactions) {
    @JsonCreator
    public BankAccountResponse {
    }

    public static BankAccountResponse from(BankAccount bankAccount) {
        return BankAccountResponse.builder()
                .accountNumber(bankAccount.getAccountNumber().value())
                .balance(bankAccount.getBalance().value())
                .owner(bankAccount.getOwner())
                .transactions(TransactionResponse.from(bankAccount.getTransactions()))
                .createdDate(bankAccount.getCreatedDate())
                .build();
    }
}
