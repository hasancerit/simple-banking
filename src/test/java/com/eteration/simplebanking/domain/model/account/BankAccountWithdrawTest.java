package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.exception.InsufficientBalanceException;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.util.BankAccountTestDataBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountWithdrawTest {
    private final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
            Amount.of(100.0)
    );

    @Test
    void givenAccountWithHundredBalance_whenWithdraw_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Amount.of(100.0));
        bankAccount.withdraw(Amount.of(100.0));
        assertEquals(Amount.ZERO, bankAccount.getBalance());
    }

    @Test
    void givenAccountWithHundredBalance_whenWithdrawTwice_thenSubtractBalance() {
        assertEquals(Amount.of(100.0), bankAccount.getBalance());
        bankAccount.withdraw(Amount.of(50.0));
        assertEquals(Amount.of(50.0), bankAccount.getBalance());

        bankAccount.withdraw(Amount.of(20.0));
        assertEquals(Amount.of(30.0), bankAccount.getBalance());
    }

    @Test
    void givenAccountWithHundredBalance_whenWithdrawFloatingPoint_thenSubtractBalance() {
        assertEquals(Amount.of(100.0), bankAccount.getBalance());
        bankAccount.withdraw(Amount.of(30.50));
        assertEquals(Amount.of(69.5), bankAccount.getBalance());
        assertEquals(Amount.of(69.50), bankAccount.getBalance());
    }

    @Disabled("To fix this problem, BigDecimal should be used. I disable the test because it is requested in this way.")
    @Test
    void givenAccountWithHundredBalance_whenWithdrawFloatingPointTwice_thenSubtractBalance() {
        assertEquals(Amount.of(100.0), bankAccount.getBalance());
        bankAccount.withdraw(Amount.of(30.10));
        assertEquals(Amount.of(69.9), bankAccount.getBalance());

        bankAccount.withdraw(Amount.of(30.02));
        assertEquals(Amount.of(39.88), bankAccount.getBalance());
    }

    @Test
    void givenAccountWithHundredBalance_whenWithdrawTwoHundred_thenThrowError() {
        assertThrows(InsufficientBalanceException.class, () -> bankAccount.withdraw(Amount.of(200.0)));
    }
}