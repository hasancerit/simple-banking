package com.eteration.simplebanking.domain.model.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountCreationTest {
    @Test
    void givenNullBalance_whenCreatingAccount_thenThrowNullPointerError() {
        assertThrows(NullPointerException.class, () -> BankAccount.builder()
                .balance(null)
                .build());
    }

    @Test
    void givenNullAccountNumber_whenCreatingAccount_thenThrowNullPointerError() {
        assertThrows(NullPointerException.class, () -> BankAccount.builder()
                .accountNumber(null)
                .build());
    }
}
