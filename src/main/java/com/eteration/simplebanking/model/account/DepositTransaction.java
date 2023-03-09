package com.eteration.simplebanking.model.account;

import com.eteration.simplebanking.model.Amount;

public class DepositTransaction extends Transaction {
    public DepositTransaction(Amount amount) {
        super(amount);
    }

    @Override
    protected void makeChangesOnBankAccount() {
        super.makeChangesOnBankAccount();
        this.bankAccount.deposit(this.amount);
    }
}
