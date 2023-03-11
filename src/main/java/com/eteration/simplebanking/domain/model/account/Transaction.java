package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "TRANSACTION")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @Column(name = "AMOUNT")
    protected Amount amount;

    @Column(name = "TYPE")
    protected String type;

    @Column(name = "APPROVAL_CODE")
    protected String approvalCode;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    protected Transaction(Amount amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    @PrePersist
    protected void onPersist() {
        createdDate = LocalDateTime.now();
    }

    protected String executeTransactionIn(BankAccount bankAccount) { //TODO: Write a domain service and delegate this flow
        makeChangesOnBankAccount(bankAccount);
        approvalCode = UUID.randomUUID().toString();
        return approvalCode;
    }

    protected abstract void makeChangesOnBankAccount(BankAccount bankAccount);
}
