package com.eteration.simplebanking.domain.model;

import com.eteration.simplebanking.domain.exception.InsufficientBalanceException;
import com.eteration.simplebanking.domain.exception.NegativeAmountException;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record Amount(Double amount) implements Serializable {
    public static Amount ZERO = Amount.of(0.0);

    public static Amount of(Double amount) {
        if(amount < 0) {
            throw new NegativeAmountException(amount);
        }
        return new Amount(amount);
    }

    public Amount add(Amount addition) {
        return new Amount(this.amount + addition.amount);
    }

    public Amount subtract(Amount subtraction) {
        if(subtraction.amount > this.amount) {
            throw new InsufficientBalanceException();
        }
        return new Amount(this.amount - subtraction.amount);
    }
}
