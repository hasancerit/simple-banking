package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.model.account.BankAccount;

public interface AccountService {
    BankAccount debit(String accountNumber, Double amount);
    BankAccount credit(String accountNumber, Double amount);
}
