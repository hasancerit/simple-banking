package com.eteration.simplebanking.model;

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
        return new Balance(this.amount - subtraction);
    }
}
