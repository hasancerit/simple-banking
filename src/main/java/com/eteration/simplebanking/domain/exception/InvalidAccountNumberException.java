package com.eteration.simplebanking.domain.exception;

public class InvalidAccountNumberException extends RuntimeException {
    public InvalidAccountNumberException(String accountNumber) {
        super("Account Number format is invalid: " + accountNumber);
    }
}
