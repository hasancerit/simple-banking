package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import com.eteration.simplebanking.repository.BankAccountRepository;
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
    public String credit(String accountNumber, Double amount) {
        return this.post(accountNumber, new DepositTransaction(Amount.of(amount)));
    }

    @Override
    public String debit(String accountNumber, Double amount) {
        return this.post(accountNumber, new WithdrawTransaction(Amount.of(amount)));
    }

    private String post(String accountNumber, Transaction transaction) {
        BankAccount bankAccount = this.getBankAccountOrThrowException(accountNumber);
        String transactionApprovalCode = bankAccount.post(transaction);
        bankAccountRepository.update(bankAccount);
        return transactionApprovalCode;
    }

    private BankAccount getBankAccountOrThrowException(String accountNumber) {
        return bankAccountRepository.get(AccountNumber.of(accountNumber))
                .orElseThrow(
                        () -> new BankAccountNotFoundException(accountNumber)
                );

    }
}
