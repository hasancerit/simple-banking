package com.eteration.simplebanking.domain.model;

import com.eteration.simplebanking.domain.exception.InsufficientBalanceException;
import com.eteration.simplebanking.domain.exception.NegativeAmountException;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record Amount(Double value) implements Serializable {
    public static Amount ZERO = Amount.of(0.0);

    public Amount {
        if(value < 0) {
            throw new NegativeAmountException(value);
        }
    }

    public static Amount of(Double amount) {
        return new Amount(amount);
    }

    public Amount add(Amount addition) {
        return new Amount(this.value + addition.value);
    }

    public Amount subtract(Amount subtraction) {
        if(subtraction.value > this.value) {
            throw new InsufficientBalanceException();
        }
        return new Amount(this.value - subtraction.value);
    }
}
