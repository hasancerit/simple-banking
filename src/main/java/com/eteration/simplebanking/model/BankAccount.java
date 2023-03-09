package com.eteration.simplebanking.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class BankAccount {
    @NonNull private Balance balance;
    @NonNull private AccountNumber accountNumber;

    public void credit(Double creditAmount) {
        this.balance = balance.add(creditAmount);
    }

    public void debit(Double debitAmount) {
        this.balance = balance.subtract(debitAmount);
    }
}
