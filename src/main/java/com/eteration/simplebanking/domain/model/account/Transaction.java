package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    protected Transaction(Amount amount) {
        this.amount = amount;
    }

    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

    protected abstract void makeChangesOnBankAccount(BankAccount bankAccount);
}
