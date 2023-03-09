package com.eteration.simplebanking.model.bankaccount;

import com.eteration.simplebanking.model.Balance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BalanceTest {
    @Test
    public void givenNegativeAmount_whenCreatingBalance_thenThrowNullPointerError() {
        assertThrows(Exception.class, () -> Balance.of(-100.0));
    }
}
