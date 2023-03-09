package com.eteration.simplebanking.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BankAccount {
    private Double balance;

    public void credit(Double debitAmount) {
        this.balance += debitAmount;
    }

    public void debit(Double debitAmount) {
        this.balance -= debitAmount;
    }
}
