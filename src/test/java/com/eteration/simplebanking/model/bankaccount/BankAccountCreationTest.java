package com.eteration.simplebanking.model.bankaccount;

import com.eteration.simplebanking.model.BankAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountCreationTest {
    @Test
    public void givenNullBalance_whenCreatingAccount_thenThrowNullPointerError() {
        assertThrows(NullPointerException.class, () -> BankAccount.builder()
                .balance(null)
                .build());
    }
}
