package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name="BANK_ACCOUNT_NUMBER")
    protected BankAccount bankAccount;

    public Transaction(Amount amount) {
        this.amount = amount;
    }

    protected void makeChangesOnBankAccount() { //TODO: Better name!
        if (this.bankAccount == null) {
            throw new RuntimeException("Transaction's bank account is null!");
        }
    }
}
