package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.account.BankAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountCreationTest {
    @Test
    public void givenNullBalance_whenCreatingAccount_thenThrowNullPointerError() {
        assertThrows(NullPointerException.class, () -> BankAccount.builder()
                .balance(null)
                .build());
    }

    @Test
    public void givenNullAccountNumber_whenCreatingAccount_thenThrowNullPointerError() {
        assertThrows(NullPointerException.class, () -> BankAccount.builder()
                .accountNumber(null)
                .build());
    }
}
