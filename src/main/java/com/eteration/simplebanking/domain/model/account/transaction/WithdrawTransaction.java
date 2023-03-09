package com.eteration.simplebanking.domain.model.account.transaction;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.Transaction;

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
