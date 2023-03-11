package com.eteration.simplebanking.domain.model.account.transaction;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE) //For hibernate
@Entity(name = "WITHDRAW_TRANSACTION")
public class WithdrawTransaction extends Transaction {
    public WithdrawTransaction(Amount amount) {
        super(amount, "Withdrawal");
    }

    @Override
    protected void makeChangesOnBankAccount(BankAccount bankAccount) {
        bankAccount.withdraw(this.amount);
    }
}
