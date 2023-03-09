package com.eteration.simplebanking.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BankAccount {
    private Balance balance;

    public void credit(Double creditAmount) {
        this.balance = balance.add(creditAmount);
    }

    public void debit(Double debitAmount) {
        this.balance = balance.subtract(debitAmount);
    }
}
