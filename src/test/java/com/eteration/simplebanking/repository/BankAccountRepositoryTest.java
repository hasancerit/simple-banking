package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import com.eteration.simplebanking.service.exception.BankAccountNotFoundException;
import com.eteration.simplebanking.util.EntityManagerUtil;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class BankAccountRepositoryTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void givenPersistedBankAccountId_whenBankAccountRepositoryGet_thenReturnSameBankAccount() {
        DepositTransaction depositTransaction1 = DepositTransaction.of(Amount.of(10.0));
        DepositTransaction depositTransaction2 = DepositTransaction.of(Amount.of(20.0));
        WithdrawTransaction withdrawTransaction1 = WithdrawTransaction.of(Amount.of(20.0));

        final BankAccount persistedBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("111-2222"))
                .transactions(List.of(depositTransaction1, depositTransaction2, withdrawTransaction1))
                .balance(Amount.of(10.0))
                .owner("Hasan")
                .build();

        //TODO: Dont these
        depositTransaction1.setBankAccount(persistedBankAccount);
        depositTransaction2.setBankAccount(persistedBankAccount);
        withdrawTransaction1.setBankAccount(persistedBankAccount);

        EntityManagerUtil.persistAndFlush(entityManagerFactory.createEntityManager(), persistedBankAccount);

        final BankAccount bankAccountFromRepository =
                bankAccountRepository.get(persistedBankAccount.getAccountNumber()).orElse(null);

        assertNotNull(bankAccountFromRepository);
        assertEquals(persistedBankAccount.getAccountNumber(), bankAccountFromRepository.getAccountNumber());
        assertEquals(persistedBankAccount.getBalance(), bankAccountFromRepository.getBalance());
        assertEquals(persistedBankAccount.getOwner(), bankAccountFromRepository.getOwner());
        assertNotNull(persistedBankAccount.getCreatedDate());

        assertNotNull(bankAccountFromRepository.getTransactions());
        assertEquals(3, bankAccountFromRepository.getTransactions().size());
        assertEqualsTransaction(depositTransaction1, bankAccountFromRepository.getTransactions().get(0));
        assertEqualsTransaction(depositTransaction2, bankAccountFromRepository.getTransactions().get(1));
        assertEqualsTransaction(withdrawTransaction1, bankAccountFromRepository.getTransactions().get(2));
    }

    @Test
    void givenNotPersistedBankAccountId_whenBankAccountRepositoryGet_thenReturnNull() {
        DepositTransaction depositTransaction1 = DepositTransaction.of(Amount.of(10.0));
        final BankAccount persistedBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("222-3333"))
                .transactions(List.of(depositTransaction1))
                .balance(Amount.of(10.0))
                .owner("Hasan")
                .build();
        depositTransaction1.setBankAccount(persistedBankAccount);

        EntityManagerUtil.persistAndFlush(entityManagerFactory.createEntityManager(), persistedBankAccount);

        final BankAccount bankAccountFromRepository = bankAccountRepository.get(AccountNumber.of("333-4444")).orElse(null);
        assertNull(bankAccountFromRepository);
    }

    @Test
    void givenNotPersistedRandomBankAccountId_whenBankAccountRepositoryGet_thenReturnNull() {
        final BankAccount bankAccountFromRepository = bankAccountRepository.get(AccountNumber.of("444-5555")).orElse(null);
        assertNull(bankAccountFromRepository);
    }


    @Test
    void givenPersistedBankAccountAndUpdate_whenBankAccountRepositoryUpdate_thenUpdatePersistence() {
        DepositTransaction depositTransaction1 = DepositTransaction.of(Amount.of(10.0));
        DepositTransaction depositTransaction2 = DepositTransaction.of(Amount.of(20.0));
        WithdrawTransaction withdrawTransaction1 = WithdrawTransaction.of(Amount.of(20.0));

        final BankAccount persistedBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("123-1234"))
                .transactions(List.of(depositTransaction1, depositTransaction2, withdrawTransaction1))
                .balance(Amount.of(10.0))
                .owner("Hasan")
                .build();

        //TODO: Dont these
        depositTransaction1.setBankAccount(persistedBankAccount);
        depositTransaction2.setBankAccount(persistedBankAccount);
        withdrawTransaction1.setBankAccount(persistedBankAccount);

        EntityManagerUtil.persistAndFlush(entityManagerFactory.createEntityManager(), persistedBankAccount);


        final BankAccount updatedBankAccount =
                EntityManagerUtil.findAndDetach(entityManagerFactory.createEntityManager(), BankAccount.class, persistedBankAccount.getAccountNumber());
        WithdrawTransaction withdrawTransaction2 = WithdrawTransaction.of(Amount.of(5.0));
        updatedBankAccount.getTransactions().add(withdrawTransaction2);
        withdrawTransaction2.setBankAccount(updatedBankAccount);
        updatedBankAccount.setBalance(Amount.of(5.0));
        bankAccountRepository.update(updatedBankAccount);

        BankAccount bankAccountFromPersistence =
                EntityManagerUtil.findAndDetach(entityManagerFactory.createEntityManager(), BankAccount.class, persistedBankAccount.getAccountNumber());

        assertNotNull(bankAccountFromPersistence);
        assertEquals(updatedBankAccount.getAccountNumber(), bankAccountFromPersistence.getAccountNumber());
        assertEquals(updatedBankAccount.getBalance(), bankAccountFromPersistence.getBalance());
        assertEquals(updatedBankAccount.getOwner(), bankAccountFromPersistence.getOwner());
        assertNotNull(updatedBankAccount.getCreatedDate());

        assertNotNull(bankAccountFromPersistence.getTransactions());
        assertEquals(4, bankAccountFromPersistence.getTransactions().size());
        assertEqualsTransaction(depositTransaction1, bankAccountFromPersistence.getTransactions().get(0));
        assertEqualsTransaction(depositTransaction2, bankAccountFromPersistence.getTransactions().get(1));
        assertEqualsTransaction(withdrawTransaction1, bankAccountFromPersistence.getTransactions().get(2));
        assertEqualsTransaction(withdrawTransaction2, bankAccountFromPersistence.getTransactions().get(3));
    }

    @Test
    void givenNotPersistedBankAccount_whenBankAccountRepositoryUpdate_thenUpdatePersistence2() {
        final BankAccount notPersistedBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("123-1234"))
                .balance(Amount.of(10.0))
                .owner("Hasan")
                .build();

        assertThrows(BankAccountNotFoundException.class, () -> bankAccountRepository.update(notPersistedBankAccount));

        BankAccount bankAccountFromPersistence =
                EntityManagerUtil.findAndDetach(entityManagerFactory.createEntityManager(), BankAccount.class, notPersistedBankAccount.getAccountNumber());

        assertNull(bankAccountFromPersistence);
    }

    private void assertEqualsTransaction(Transaction expectedTransaction, Transaction actualTransaction) {
        assertNotNull(actualTransaction.getCreatedDate());
        assertEquals(expectedTransaction.getId(), actualTransaction.getId());
        assertEquals(expectedTransaction.getBankAccount().getAccountNumber(), actualTransaction.getBankAccount().getAccountNumber());
        assertEquals(expectedTransaction.getAmount(), actualTransaction.getAmount());
    }
}
