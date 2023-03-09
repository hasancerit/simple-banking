package com.eteration.simplebanking.model;

import com.eteration.simplebanking.model.Amount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AmountTest {
    @Test
    public void givenNegativeAmount_whenCreatingBalance_thenThrowException() {
        assertThrows(Exception.class, () -> Amount.of(-100.0));
    }

    //TODO: I covered Balance.add and Balance.subtract cases in BankAccountDepositTest and BankAccountWithdrawTest. But must be added here.
}
