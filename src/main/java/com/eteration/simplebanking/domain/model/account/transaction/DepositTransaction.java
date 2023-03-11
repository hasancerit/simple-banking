package com.eteration.simplebanking.domain.model.account.transaction;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity(name = "DEPOSIT_TRANSACTION")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
