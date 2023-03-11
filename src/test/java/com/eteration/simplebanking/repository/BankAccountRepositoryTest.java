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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class BankAccountRepositoryTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void givenPersistedBankAccountId_whenBankAccountRepositoryGet_thenReturnSameBankAccount() {
        DepositTransaction depositTransaction1 = new DepositTransaction(Amount.of(10.0));
        depositTransaction1.setApprovalCode(UUID.randomUUID().toString());
        WithdrawTransaction withdrawTransaction1 = new WithdrawTransaction(Amount.of(5.0));
        withdrawTransaction1.setApprovalCode(UUID.randomUUID().toString());

        final BankAccount persistedBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("111-2222"))
                .transactions(List.of(depositTransaction1, withdrawTransaction1))
                .balance(Amount.of(5.0))
                .owner("Hasan")
                .build();

        EntityManagerUtil.persistAndFlush(entityManagerFactory.createEntityManager(), persistedBankAccount);

        final BankAccount bankAccountFromRepository =
                bankAccountRepository.get(persistedBankAccount.getAccountNumber()).orElse(null);

        assertNotNull(bankAccountFromRepository);
        assertEquals(persistedBankAccount.getAccountNumber(), bankAccountFromRepository.getAccountNumber());
        assertEquals(persistedBankAccount.getBalance(), bankAccountFromRepository.getBalance());
        assertEquals(persistedBankAccount.getOwner(), bankAccountFromRepository.getOwner());
        assertNotNull(bankAccountFromRepository.getCreatedDate());

        assertNotNull(bankAccountFromRepository.getTransactions());
        assertEquals(2, bankAccountFromRepository.getTransactions().size());
        assertEqualsTransaction(depositTransaction1, bankAccountFromRepository.getTransactions().get(0));
        assertEqualsTransaction(withdrawTransaction1, bankAccountFromRepository.getTransactions().get(1));
    }

    @Test
    void givenNotPersistedRandomBankAccountId_whenBankAccountRepositoryGet_thenReturnNull() {
        final BankAccount bankAccountFromRepository = bankAccountRepository.get(AccountNumber.of("444-5555")).orElse(null);
        assertNull(bankAccountFromRepository);
    }


    @Test
    void givenPersistedThenUpdatedBankAccount_whenBankAccountRepositoryUpdate_thenUpdatePersistence() {
        DepositTransaction depositTransaction1 = new DepositTransaction(Amount.of(10.0));
        depositTransaction1.setApprovalCode(UUID.randomUUID().toString());

        final BankAccount persistedBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("123-1234"))
                .transactions(List.of(depositTransaction1))
                .balance(Amount.of(10.0))
                .owner("Hasan")
                .build();

        EntityManagerUtil.persistAndFlush(entityManagerFactory.createEntityManager(), persistedBankAccount);


        final BankAccount updatedBankAccount =
                EntityManagerUtil.findAndDetach(entityManagerFactory.createEntityManager(), BankAccount.class, persistedBankAccount.getAccountNumber());
        WithdrawTransaction withdrawTransaction1 = new WithdrawTransaction(Amount.of(5.0));
        withdrawTransaction1.setApprovalCode(UUID.randomUUID().toString());
        WithdrawTransaction withdrawTransaction2 = new WithdrawTransaction(Amount.of(3.0));
        withdrawTransaction2.setApprovalCode(UUID.randomUUID().toString());

        updatedBankAccount.getTransactions().add(withdrawTransaction1);
        updatedBankAccount.getTransactions().add(withdrawTransaction2);
        updatedBankAccount.setBalance(Amount.of(2.0));

        bankAccountRepository.update(updatedBankAccount);

        BankAccount bankAccountFromPersistence =
                EntityManagerUtil.findAndDetach(entityManagerFactory.createEntityManager(), BankAccount.class, persistedBankAccount.getAccountNumber());

        assertNotNull(bankAccountFromPersistence);
        assertEquals(updatedBankAccount.getAccountNumber(), bankAccountFromPersistence.getAccountNumber());
        assertEquals(updatedBankAccount.getBalance(), bankAccountFromPersistence.getBalance());
        assertEquals(updatedBankAccount.getOwner(), bankAccountFromPersistence.getOwner());
        assertNotNull(bankAccountFromPersistence.getCreatedDate());

        assertNotNull(bankAccountFromPersistence.getTransactions());
        assertEquals(3, bankAccountFromPersistence.getTransactions().size());
        assertEqualsTransaction(depositTransaction1, bankAccountFromPersistence.getTransactions().get(0));
        assertEqualsTransaction(withdrawTransaction1, bankAccountFromPersistence.getTransactions().get(1));
        assertEqualsTransaction(withdrawTransaction2, bankAccountFromPersistence.getTransactions().get(2));
    }

    @Test
    void givenNotPersistedBankAccountId_whenBankAccountRepositoryUpdate_thenUpdatePersistence2() {
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
        assertEquals(expectedTransaction.getId(), actualTransaction.getId());
        assertEquals(expectedTransaction.getAmount(), actualTransaction.getAmount());
        assertEquals(expectedTransaction.getApprovalCode(), actualTransaction.getApprovalCode());
        assertNotNull(actualTransaction.getCreatedDate());
        //TODO: Assert Type here!
    }
}
