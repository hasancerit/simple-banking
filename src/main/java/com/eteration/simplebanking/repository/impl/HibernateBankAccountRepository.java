package com.eteration.simplebanking.repository.impl;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import com.eteration.simplebanking.repository.BankAccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

//TODO: Better Implementation!
@Repository
@RequiredArgsConstructor
public class HibernateBankAccountRepository implements BankAccountRepository {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<BankAccount> get(AccountNumber accountNumber) {
        BankAccount bankAccount;
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            bankAccount = entityManager.find(BankAccount.class, accountNumber);
        }
        return Optional.ofNullable(bankAccount);
    }

    @Override
    public BankAccount update(BankAccount bankAccount) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            BankAccount existingBankAccount = entityManager.find(BankAccount.class, bankAccount.getAccountNumber());
            if(existingBankAccount == null) {
                throw new RuntimeException("BankAccount with bankAccount could not found");
            }

            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            bankAccount.getTransactions().forEach(t -> {
                if(!existingBankAccount.getTransactions().stream().map(Transaction::getId).collect(Collectors.toSet()).contains(t.getId())) {
                    existingBankAccount.getTransactions().add(t);
                }
            });
            existingBankAccount.setBalance(bankAccount.getBalance());

            transaction.commit();
            return existingBankAccount;
        }
    }
}