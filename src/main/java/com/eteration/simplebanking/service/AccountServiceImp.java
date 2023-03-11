package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.repository.BankAccountRepository;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import com.eteration.simplebanking.service.exception.BankAccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImp implements AccountService {
    private final BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount get(String accountNumber) {
        return this.getBankAccountOrThrowException(accountNumber);
    }

    @Override
    public BankAccount credit(String accountNumber, Double amount) {
        BankAccount bankAccount = this.getBankAccountOrThrowException(accountNumber);
        bankAccount.post(new DepositTransaction(Amount.of(amount)));
        bankAccountRepository.update(bankAccount);
        return bankAccount;
    }

    @Override
    public BankAccount debit(String accountNumber, Double amount) {
        BankAccount bankAccount = this.getBankAccountOrThrowException(accountNumber);
        bankAccount.post(new WithdrawTransaction(Amount.of(amount)));
        bankAccount = bankAccountRepository.update(bankAccount);
        return bankAccount;
    }

    private BankAccount getBankAccountOrThrowException(String accountNumber) {
        return bankAccountRepository.get(AccountNumber.of(accountNumber))
                .orElseThrow(
                        () -> new BankAccountNotFoundException(accountNumber)
                );

    }
}
