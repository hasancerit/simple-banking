package com.eteration.simplebanking.controller.dto.res;


import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;

import java.util.List;

@Builder
public record BankAccountResponse(String accountNumber, Double balance, List<TransactionResponse> transactions) {
    @JsonCreator
    public BankAccountResponse {
    }

    public static BankAccountResponse from(BankAccount bankAccount) {
        return BankAccountResponse.builder()
                .accountNumber(bankAccount.getAccountNumber().value())
                .balance(bankAccount.getBalance().amount())
                .transactions(TransactionResponse.from(bankAccount.getTransactions()))
                .build();
    }
}
