package com.eteration.simplebanking.domain.model.account.transaction;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity(name = "WITHDRAW_TRANSACTION")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WithdrawTransaction extends Transaction {
    public WithdrawTransaction(Amount amount) {
        super(amount);
    }

    public static WithdrawTransaction of(Amount amount) {
        return new WithdrawTransaction(amount);
    }

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        type = "Withdrawal";
    }

    @Override
    protected void makeChangesOnBankAccount() {
        super.makeChangesOnBankAccount();
        this.bankAccount.withdraw(this.amount);
    }
}
