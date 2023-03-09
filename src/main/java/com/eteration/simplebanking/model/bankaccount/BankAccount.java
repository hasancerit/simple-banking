package com.eteration.simplebanking.model.bankaccount;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BankAccount {
    private Double balance;

    public void credit(Double debitAmount) {
        this.balance += debitAmount;
    }
}
