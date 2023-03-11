package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
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
    @Builder.Default
    private Amount balance = Amount.ZERO;

    @NonNull
    @Column(name = "OWNER")
    private String owner;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="BANK_ACCOUNT_NUMBER")
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();

    @PrePersist
    protected void onPersist() {
        createdDate = LocalDateTime.now();
    }

    public void deposit(Amount depositAmount) {
        this.balance = balance.add(depositAmount);
    }

    public void withdraw(Amount withdrawAmount) {
        this.balance = balance.subtract(withdrawAmount);
    }

    public String post(Transaction transaction) {
        String transactionApprovalCode = transaction.executeTransactionIn(this);
        transactions.add(transaction);
        return transactionApprovalCode;
    }
}
