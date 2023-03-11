package com.eteration.simplebanking.domain.exception;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException(Double amount) {
        super("Balance value must grater than zero or equals to zero. Amount:" + amount);
    }
}