package com.eteration.simplebanking.repository;


import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.BankAccountRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HibernateBankAccountRepository implements BankAccountRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Optional<BankAccount> get(AccountNumber accountNumber) {
        try (EntityManager entityManager = this.createEntityManager()) {
            return Optional.ofNullable(entityManager.find(BankAccount.class, accountNumber));
        }
    }

    private EntityManager createEntityManager() {
        return sessionFactory.openSession().getEntityManagerFactory().createEntityManager();
    }
}
