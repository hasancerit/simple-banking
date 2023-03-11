package com.eteration.simplebanking.domain.model.account.transaction;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity(name = "WITHDRAW_TRANSACTION")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WithdrawTransaction extends Transaction {
    public WithdrawTransaction(Amount amount) {
        super(amount, "Withdrawal");
    }

    @PrePersist
    @Override
    protected void onPersist() {
        super.onPersist();
    }

    @Override
    protected void makeChangesOnBankAccount(BankAccount bankAccount) {
        bankAccount.withdraw(this.amount);
    }
}
