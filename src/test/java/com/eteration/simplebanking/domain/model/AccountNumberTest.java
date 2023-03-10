package com.eteration.simplebanking.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountNumberTest {
    @Test
    public void givenValidValue_whenCreatingAccountNumber_thenReturnAccountNumber() {
        AccountNumber accountNumber = AccountNumber.of("432-4234");
        assertEquals(accountNumber.value(), "432-4234");
    }

    @Test
    public void givenInvalidValue_whenCreatingAccountNumber_thenThrowError() {
        assertThrows(Exception.class, () -> AccountNumber.of("invalidvalue"));
    }

}