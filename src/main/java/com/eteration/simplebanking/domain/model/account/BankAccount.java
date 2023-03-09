package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class BankAccount {
    @NonNull private Amount balance;
    @NonNull private AccountNumber accountNumber;
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();

    public void deposit(Amount depositAmount) {
        this.balance = balance.add(depositAmount);
    }

    public void withdraw(Amount withdrawAmount) {
        this.balance = balance.subtract(withdrawAmount);
    }

    public void post(Transaction transaction) {
        transaction.setBankAccount(this);
        transaction.makeChangesOnBankAccount();
        transactions.add(transaction);
    }
}
