package com.eteration.simplebanking.domain.model.account.transaction;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import com.eteration.simplebanking.domain.model.account.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE) //For hibernate
@Entity(name = "DEPOSIT_TRANSACTION")
public class DepositTransaction extends Transaction {
    @Column(name = "DEPOSIT_SPECIFIC_FIELD")
    private String depositSpecificFields;

    public DepositTransaction(Amount amount) {
        super(amount, TransactionType.DEPOSIT);
    }

    @Override
    protected void makeChangesOnBankAccount(BankAccount bankAccount) {
        bankAccount.deposit(this.amount);
    }
}
