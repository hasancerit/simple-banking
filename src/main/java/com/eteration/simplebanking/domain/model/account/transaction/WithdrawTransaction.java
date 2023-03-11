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
        super(amount);
    }

    public static WithdrawTransaction of(Amount amount) {
        return new WithdrawTransaction(amount);
    }

    @PrePersist
    @Override
    protected void onCreate() {
        super.onCreate();
        type = "Withdrawal";
    }

    @Override
    protected void makeChangesOnBankAccount(BankAccount bankAccount) {
        bankAccount.withdraw(this.amount);
    }
}
