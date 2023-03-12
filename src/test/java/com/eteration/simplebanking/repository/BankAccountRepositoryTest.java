package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import com.eteration.simplebanking.service.exception.BankAccountNotFoundException;
import com.eteration.simplebanking.util.builder.BankAccountTestDataBuilder;
import com.eteration.simplebanking.util.EntityManagerUtil;
import com.eteration.simplebanking.util.builder.TransactionTestDataBuilder;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import static org.junit.jupiter.api.Assertions.*;

//TODO: Refactor this test class
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class BankAccountRepositoryTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void givenPersistedBankAccountId_whenBankAccountRepositoryGet_thenReturnSameBankAccount() {
        final BankAccount persistedBankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
                Amount.of(5.0),
                TransactionTestDataBuilder.approvedDepositTransaction(Amount.of(10.0)),
                TransactionTestDataBuilder.approvedWithdrawTransaction(Amount.of(5.0))
        );

        EntityManagerUtil.persistAndFlush(entityManagerFactory.createEntityManager(), persistedBankAccount);

        final BankAccount bankAccountFromRepository =
                bankAccountRepository.get(persistedBankAccount.getAccountNumber()).orElse(null);

        assertNotNull(bankAccountFromRepository);
        assertEquals(persistedBankAccount.getAccountNumber(), bankAccountFromRepository.getAccountNumber());
        assertEquals(persistedBankAccount.getBalance(), bankAccountFromRepository.getBalance());
        assertEquals(persistedBankAccount.getOwner(), bankAccountFromRepository.getOwner());
        assertNotNull(bankAccountFromRepository.getCreatedDate());

        assertNotNull(bankAccountFromRepository.getTransactions());
        assertEquals(persistedBankAccount.getTransactions().size(), bankAccountFromRepository.getTransactions().size());
        assertEqualsTransaction(persistedBankAccount.getTransactions().get(0), bankAccountFromRepository.getTransactions().get(0));
        assertEqualsTransaction(persistedBankAccount.getTransactions().get(1), bankAccountFromRepository.getTransactions().get(1));
    }

    @Test
    void givenNotPersistedRandomBankAccountId_whenBankAccountRepositoryGet_thenReturnNull() {
        final String accountNumber = BankAccountTestDataBuilder.generateValidAccountNumber();
        final BankAccount bankAccountFromRepository =
                bankAccountRepository.get(AccountNumber.of(accountNumber)).orElse(null);
        assertNull(bankAccountFromRepository);
    }


    @Test
    void givenPersistedThenUpdatedBankAccount_whenBankAccountRepositoryUpdate_thenUpdatePersistence() {
        final BankAccount persistedBankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
                Amount.of(10.0),
                TransactionTestDataBuilder.approvedDepositTransaction(Amount.of(10.0))
        );

        EntityManagerUtil.persistAndFlush(entityManagerFactory.createEntityManager(), persistedBankAccount);


        final BankAccount updatedBankAccount = EntityManagerUtil.findAndDetach(
                entityManagerFactory.createEntityManager(),
                BankAccount.class,
                persistedBankAccount.getAccountNumber()
        );

        updatedBankAccount.getTransactions().add(
                TransactionTestDataBuilder.approvedWithdrawTransaction(Amount.of(5.0))
        );
        updatedBankAccount.getTransactions().add(
                TransactionTestDataBuilder.approvedWithdrawTransaction(Amount.of(3.0))
        );
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
        assertEquals(updatedBankAccount.getTransactions().size(), bankAccountFromPersistence.getTransactions().size());
        assertEqualsTransaction(updatedBankAccount.getTransactions().get(0), bankAccountFromPersistence.getTransactions().get(0));
        assertEqualsTransaction(updatedBankAccount.getTransactions().get(1), bankAccountFromPersistence.getTransactions().get(1));
        assertEqualsTransaction(updatedBankAccount.getTransactions().get(2), bankAccountFromPersistence.getTransactions().get(2));
    }

    @Test
    void givenNotPersistedBankAccount_whenBankAccountRepositoryUpdate_thenThrowBankAccountNotFoundExceptionAndDontPersist() {
        final BankAccount notPersistedBankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

        assertThrows(BankAccountNotFoundException.class, () -> bankAccountRepository.update(notPersistedBankAccount));

        final BankAccount bankAccountFromPersistence =
                EntityManagerUtil.findAndDetach(entityManagerFactory.createEntityManager(), BankAccount.class, notPersistedBankAccount.getAccountNumber());

        assertNull(bankAccountFromPersistence);
    }

    private void assertEqualsTransaction(Transaction expectedTransaction, Transaction actualTransaction) {
        assertEquals(expectedTransaction.getId(), actualTransaction.getId());
        assertEquals(expectedTransaction.getAmount(), actualTransaction.getAmount());
        assertEquals(expectedTransaction.getApprovalCode(), actualTransaction.getApprovalCode());
        assertEquals(expectedTransaction.getType(), actualTransaction.getType());
        assertNotNull(actualTransaction.getCreatedDate());
    }
}
