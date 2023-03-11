package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.BankAccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public class BankAccountRepositoryTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void whenPersistBankAccount_givenPersistedIdToBankAccountRepositoryGet_thenReturnSameBankAccount() {
        final BankAccount givenBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("111-2222"))
                .balance(Amount.of(10.5))
                .build();

        this.saveBankAccount(givenBankAccount);

        final BankAccount bankAccountExpected = bankAccountRepository.get(givenBankAccount.getAccountNumber()).orElse(null);
        assertNotNull(bankAccountExpected);
        assertEquals(givenBankAccount.getAccountNumber(), bankAccountExpected.getAccountNumber());
        assertEquals(givenBankAccount.getBalance(), bankAccountExpected.getBalance());
    }

    @Test
    public void whenPersistBankAccount_givenNotPersistedIdToBankAccountRepositoryGet_thenReturnSameBankAccount() {
        final BankAccount givenBankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("222-3333"))
                .balance(Amount.of(10.5))
                .build();

        this.saveBankAccount(givenBankAccount);

        final BankAccount bankAccountExpected = bankAccountRepository.get(AccountNumber.of("333-4444")).orElse(null);
        assertNull(bankAccountExpected);
    }

    @Test
    public void whenNotPersistBankAccount_givenRandomIdToBankAccountRepositoryGet_thenReturnSameBankAccount() {
        final BankAccount bankAccountExpected = bankAccountRepository.get(AccountNumber.of("444-5555")).orElse(null);
        assertNull(bankAccountExpected);
    }


    private void saveBankAccount(BankAccount bankAccount) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(bankAccount);
        transaction.commit();
        entityManager.close();
    }
}
