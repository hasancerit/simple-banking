package com.eteration.simplebanking.model.account;

import com.eteration.simplebanking.model.Amount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class Transaction {
    protected final Amount amount;
    protected BankAccount bankAccount;

    protected void makeChangesOnBankAccount() { //TODO: Better name!
        if(this.bankAccount == null) {
            throw new RuntimeException("Transaction's bank account is null!");
        }
    }
}
