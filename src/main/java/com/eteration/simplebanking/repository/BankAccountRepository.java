package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.account.BankAccount;

import java.util.Optional;

public interface BankAccountRepository {
    Optional<BankAccount> get(AccountNumber accountNumber);
    BankAccount update(BankAccount bankAccount);
}
