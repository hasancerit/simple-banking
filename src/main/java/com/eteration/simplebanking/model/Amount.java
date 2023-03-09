package com.eteration.simplebanking.model;

//TODO: Is it must be value object?
public record Amount(Double amount) {
    public static Amount ZERO = Amount.of(0.0);

    public static Amount of(Double amount) {
        if(amount < 0) {
            throw new RuntimeException("Balance amount must grater than zero or equals to zero");
        }
        return new Amount(amount);
    }

    public Amount add(Double addition) {
        return new Amount(this.amount + addition);
    }

    public Amount subtract(Double subtraction) {
        if(subtraction > amount) {
            throw new InsufficientBalanceException();
        }
        return new Amount(this.amount - subtraction);
    }
}
