package com.eteration.simplebanking.domain.model.account;

import java.util.Optional;

public interface BankAccountRepository {
    Optional<BankAccount> get(String accountNumber);
}
