package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.BankAccountRepository;
import com.eteration.simplebanking.domain.model.account.Transaction;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
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
public class BankAccountRepositoryTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void givenPersistedBankAccountId_whenBankAccountRepositoryGet_thenReturnSameBankAccount() {
        DepositTransaction depositTransaction1 = DepositTransaction.of(Amount.of(10.0));
        DepositTransaction depositTransaction2 = DepositTransaction.of(Amount.of(20.0));
        WithdrawTransaction withdrawTransaction1 = WithdrawTransaction.of(Amount.of(20.0));

        final BankAccount persistedBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("111-2222"))
                .transactions(List.of(depositTransaction1, depositTransaction2, withdrawTransaction1))
                .balance(Amount.of(10.0))
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

        assertNotNull(bankAccountFromRepository.getTransactions());
        assertEquals(3, bankAccountFromRepository.getTransactions().size());
        assertEqualsTransaction(bankAccountFromRepository.getTransactions().get(0), depositTransaction1);
        assertEqualsTransaction(bankAccountFromRepository.getTransactions().get(1), depositTransaction2);
        assertEqualsTransaction(bankAccountFromRepository.getTransactions().get(2), withdrawTransaction1);
    }

    @Test
    public void givenNotPersistedBankAccountId_whenBankAccountRepositoryGet_thenReturnNull() {
        DepositTransaction depositTransaction1 = DepositTransaction.of(Amount.of(10.0));
        final BankAccount persistedBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("222-3333"))
                .transactions(List.of(depositTransaction1))
                .balance(Amount.of(10.0))
                .build();
        depositTransaction1.setBankAccount(persistedBankAccount);

        EntityManagerUtil.persistAndFlush(entityManagerFactory.createEntityManager(), persistedBankAccount);

        final BankAccount bankAccountFromRepository = bankAccountRepository.get(AccountNumber.of("333-4444")).orElse(null);
        assertNull(bankAccountFromRepository);
    }

    @Test
    public void givenNotPersistedRandomBankAccountId_whenBankAccountRepositoryGet_thenReturnNull() {
        final BankAccount bankAccountFromRepository = bankAccountRepository.get(AccountNumber.of("444-5555")).orElse(null);
        assertNull(bankAccountFromRepository);
    }


    @Test
    public void givenPersistedBankAccountAndUpdate_whenBankAccountRepositoryUpdate_thenUpdatePersistence() {
        DepositTransaction depositTransaction1 = DepositTransaction.of(Amount.of(10.0));
        DepositTransaction depositTransaction2 = DepositTransaction.of(Amount.of(20.0));
        WithdrawTransaction withdrawTransaction1 = WithdrawTransaction.of(Amount.of(20.0));

        final BankAccount persistedBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("123-1234"))
                .transactions(List.of(depositTransaction1, depositTransaction2, withdrawTransaction1))
                .balance(Amount.of(10.0))
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
        assertEquals(bankAccountFromPersistence.getAccountNumber(), updatedBankAccount.getAccountNumber());
        assertEquals(bankAccountFromPersistence.getBalance(), updatedBankAccount.getBalance());

        assertNotNull(bankAccountFromPersistence.getTransactions());
        assertEquals(4, bankAccountFromPersistence.getTransactions().size());
        assertEqualsTransaction(bankAccountFromPersistence.getTransactions().get(0), depositTransaction1);
        assertEqualsTransaction(bankAccountFromPersistence.getTransactions().get(1), depositTransaction2);
        assertEqualsTransaction(bankAccountFromPersistence.getTransactions().get(2), withdrawTransaction1);
        assertEqualsTransaction(bankAccountFromPersistence.getTransactions().get(3), withdrawTransaction2);
    }

    @Test
    public void givenNotPersistedBankAccount_whenBankAccountRepositoryUpdate_thenUpdatePersistence2() {
        final BankAccount notPersistedBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("123-1234"))
                .balance(Amount.of(10.0))
                .build();

        assertThrows(RuntimeException.class, () -> bankAccountRepository.update(notPersistedBankAccount));

        BankAccount bankAccountFromPersistence =
                EntityManagerUtil.findAndDetach(entityManagerFactory.createEntityManager(), BankAccount.class, notPersistedBankAccount.getAccountNumber());

        assertNull(bankAccountFromPersistence);
    }

    private void assertEqualsTransaction(Transaction expectedTransaction, Transaction actualTransaction) {
        assertEquals(expectedTransaction.getId(), actualTransaction.getId());
        assertEquals(expectedTransaction.getBankAccount().getAccountNumber(), actualTransaction.getBankAccount().getAccountNumber());
        assertEquals(expectedTransaction.getAmount(), actualTransaction.getAmount());
    }
}
