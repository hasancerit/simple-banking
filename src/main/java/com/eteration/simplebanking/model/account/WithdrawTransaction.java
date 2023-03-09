package com.eteration.simplebanking.model.account;

import com.eteration.simplebanking.model.Amount;

public class WithdrawTransaction extends Transaction {
    public WithdrawTransaction(Amount amount) {
        super(amount);
    }

    @Override
    protected void makeChangesOnBankAccount() {
        super.makeChangesOnBankAccount();
        this.bankAccount.withdraw(this.amount);
    }
}
