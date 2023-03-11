package com.eteration.simplebanking.domain.model;

import com.eteration.simplebanking.domain.exception.InvalidAccountNumberException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountNumberTest {
    @Test
    void givenValidValue_whenCreatingAccountNumber_thenReturnAccountNumber() {
        AccountNumber accountNumber = AccountNumber.of("432-4234");
        assertEquals("432-4234", accountNumber.value());
    }

    @Test
    void givenInvalidValue_whenCreatingAccountNumber_thenThrowError() {
        assertThrows(InvalidAccountNumberException.class, () -> AccountNumber.of("invalidvalue"));
    }

}
