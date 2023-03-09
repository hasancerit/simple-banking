package com.eteration.simplebanking.model.account;

import com.eteration.simplebanking.model.InsufficientBalanceException;

//TODO: Is it must be value object?
public record Balance(Double amount) {
    public static Balance ZERO = Balance.of(0.0);

    public static Balance of(Double amount) {
        if(amount < 0) {
            throw new RuntimeException("Balance amount must grater than zero or equals to zero");
        }
        return new Balance(amount);
    }

    public Balance add(Double addition) {
        return new Balance(this.amount + addition);
    }

    public Balance subtract(Double subtraction) {
        if(subtraction > amount) {
            throw new InsufficientBalanceException();
        }
        return new Balance(this.amount - subtraction);
    }
}
