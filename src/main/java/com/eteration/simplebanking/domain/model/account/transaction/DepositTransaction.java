package com.eteration.simplebanking.domain.model.account.transaction;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity(name = "DEPOSIT_TRANSACTION")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DepositTransaction extends Transaction {
    public DepositTransaction(Amount amount) {
        super(amount);
    }

    public static DepositTransaction of(Amount amount) {
        return new DepositTransaction(amount);
    }

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        type = "Deposit";
    }

    @Override
    protected void makeChangesOnBankAccount() {
        super.makeChangesOnBankAccount();
        this.bankAccount.deposit(this.amount);
    }
}
