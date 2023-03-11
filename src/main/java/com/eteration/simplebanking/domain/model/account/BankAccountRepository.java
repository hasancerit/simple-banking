package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.AccountNumber;

import java.util.Optional;

public interface BankAccountRepository {
    Optional<BankAccount> get(AccountNumber accountNumber);
    BankAccount update(BankAccount bankAccount);
}
