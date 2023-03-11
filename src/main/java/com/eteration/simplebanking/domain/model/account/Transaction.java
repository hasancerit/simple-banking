package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.TransactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //For hibernate
@Entity(name = "TRANSACTION")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "AMOUNT"))
    protected Amount amount;

    @Column(name = "TYPE")
    protected TransactionType type;

    @Column(name = "APPROVAL_CODE")
    protected String approvalCode;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    protected Transaction(Amount amount, TransactionType type) {
        this.amount = amount;
        this.type = type;
    }

    @PrePersist
    private void onPersist() {
        this.createdDate = LocalDateTime.now();
    }

    protected String executeTransactionIn(BankAccount bankAccount) {
        this.makeChangesOnBankAccount(bankAccount);
        this.approvalCode = UUID.randomUUID().toString();
        return approvalCode;
    }

    protected abstract void makeChangesOnBankAccount(BankAccount bankAccount);
}
