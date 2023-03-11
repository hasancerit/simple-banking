package com.eteration.simplebanking.service.exception;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(String accountNumber) {
        super("Bank Account Could not Found with AccountNumber: " + accountNumber);
    }
}
