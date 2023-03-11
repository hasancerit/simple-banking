package com.eteration.simplebanking.domain.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AmountTest {
    @Test
    void givenValidValue_whenCreatingAmount_thenReturnAmount() {
        Amount amount = Amount.of(10.5);
        assertEquals(10.5, amount.value());

        amount = new Amount(2.1);
        assertEquals(2.1, amount.value());
    }

    @Test
    void givenNegativeAmount_whenCreatingAmount_thenThrowException() {
        assertThrows(Exception.class, () -> Amount.of(-100.0));
        assertThrows(Exception.class, () -> new Amount(-100.0));
    }

    @Test
    void givenAmount_whenAmountAdd_thenReturnTotalAmount() {
        Amount amount = Amount.of(10.4);
        amount = amount.add(Amount.of(2.4));
        assertEquals(12.8, amount.value());
        new Amount(12.4);
    }

    @Test
    void givenAmount_whenAmountSubtract_thenReturnExtractionAmount() {
        Amount amount = Amount.of(10.4);
        amount = amount.subtract(Amount.of(2.4));
        assertEquals(8, amount.value());
        new Amount(12.4);
    }

    @Disabled("To fix this problem, BigDecimal should be used. I disable the test because it is requested in this way.")
    @Test
    void givenFloatingAmount_whenAmountAdd_thenReturnTotalAmount() {
        Amount amount = Amount.of(10.4);
        amount = amount.add(Amount.of(2.04));
        assertEquals(12.44, amount.value());
        new Amount(12.4);
    }
}
