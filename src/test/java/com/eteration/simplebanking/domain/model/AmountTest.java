package com.eteration.simplebanking.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AmountTest {
    @Test
    void givenNegativeAmount_whenCreatingAmount_thenThrowException() {
        assertThrows(Exception.class, () -> Amount.of(-100.0));
    }

    //TODO: I covered Balance.add and Balance.subtract cases in BankAccountDepositTest and BankAccountWithdrawTest. But must be added here.
}
