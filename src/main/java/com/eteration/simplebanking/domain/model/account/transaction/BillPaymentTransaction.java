package com.eteration.simplebanking.domain.model.account.transaction;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE) //For hibernate
@Entity(name = "BILL_PAYMENT_TRANSACTION")
public class BillPaymentTransaction extends Transaction {
    @Column(name = "BILL_NUMBER")
    private String billNumber;

    @Column(name = "PAYEE")
    private String payee;

    public BillPaymentTransaction(Amount amount, String billNumber, String payee) {
        super(amount, TransactionType.BILL_PAYMENT);
        this.billNumber = billNumber;
        this.payee = payee;
    }

    @Override
    protected void makeChangesOnBankAccount(BankAccount bankAccount) {
        bankAccount.withdraw(this.amount);
    }
}
