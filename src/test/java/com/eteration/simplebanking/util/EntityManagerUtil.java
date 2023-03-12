package com.eteration.simplebanking.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityManagerUtil {
    public static <T> void persistAndFlush(EntityManager entityManager, T bankAccount) {
        entityManager.clear();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(bankAccount);

        transaction.commit();
        entityManager.close();
    }

    public static <T> T findAndDetach(EntityManager entityManager, Class<T> clazz, Object id) {
        entityManager.clear();
        T entity = entityManager.find(clazz, id);
        entityManager.close();
        return entity;
    }
}
