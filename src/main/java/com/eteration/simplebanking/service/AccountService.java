package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.model.account.BankAccount;

public interface AccountService {
    BankAccount get(String accountNumber);
    String debit(String accountNumber, Double amount);
    String credit(String accountNumber, Double amount);
}
