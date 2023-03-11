package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name = "BANK_ACCOUNT")
public class BankAccount {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "ACCOUNT_NUMBER"))
    private AccountNumber accountNumber;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "BALANCE"))
    private Amount balance = Amount.ZERO;

    @Column(name = "OWNER")
    private String owner;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "BANK_ACCOUNT_NUMBER")
    private List<Transaction> transactions = new ArrayList<>();

    @PrePersist
    private void onPersist() {
        this.createdDate = LocalDateTime.now();
    }

    public void deposit(Amount depositAmount) {
        this.balance = balance.add(depositAmount);
    }

    public void withdraw(Amount withdrawAmount) {
        this.balance = balance.subtract(withdrawAmount);
    }

    public String post(Transaction transaction) {
        final String transactionApprovalCode = transaction.executeTransactionIn(this);
        this.transactions.add(transaction);
        return transactionApprovalCode;
    }
}
