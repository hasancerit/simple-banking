package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity(name = "BANK_ACCOUNT")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankAccount {
    @NonNull
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "ACCOUNT_NUMBER"))
    private AccountNumber accountNumber;

    @NonNull
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "BALANCE"))
    private Amount balance;

    @Transient
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();

    public void deposit(Amount depositAmount) {
        this.balance = balance.add(depositAmount);
    }

    public void withdraw(Amount withdrawAmount) {
        this.balance = balance.subtract(withdrawAmount);
    }

    public void post(Transaction transaction) {
        transaction.setBankAccount(this);
        transaction.makeChangesOnBankAccount();
        transactions.add(transaction);
    }
}
