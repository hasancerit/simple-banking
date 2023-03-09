package com.eteration.simplebanking.model.account;

import com.eteration.simplebanking.model.Amount;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class BankAccount {
    @NonNull private Amount balance;
    @NonNull private AccountNumber accountNumber;

    public void deposit(Double depositAmount) {
        this.balance = balance.add(depositAmount);
    }

    public void withdraw(Double withdrawAmount) {
        this.balance = balance.subtract(withdrawAmount);
    }
}
