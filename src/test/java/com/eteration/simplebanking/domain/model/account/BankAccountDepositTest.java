package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.util.BankAccountTestDataBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountDepositTest {
    private final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

    @Test
    void givenEmptyAccount_whenDeposit_thenIncreaseBalance() {
        assertEquals(Amount.ZERO, bankAccount.getBalance());
        bankAccount.deposit(Amount.of(100.0));
        assertEquals(Amount.of(100.0), bankAccount.getBalance());
    }

    @Test
    void givenEmptyAccount_whenDepositTwice_thenIncreaseBalance() {
        assertEquals(Amount.ZERO, bankAccount.getBalance());
        bankAccount.deposit(Amount.of(100.0));
        assertEquals(Amount.of(100.0), bankAccount.getBalance());

        bankAccount.deposit(Amount.of(53.0));
        assertEquals(Amount.of(153.0), bankAccount.getBalance());
    }

    @Test
    void givenEmptyAccount_whenDepositFloatingPoint_thenIncreaseBalance() {
        assertEquals(Amount.ZERO, bankAccount.getBalance());
        bankAccount.deposit(Amount.of(64.45));
        assertEquals(Amount.of(64.45), bankAccount.getBalance());
    }

    @Disabled("To fix this problem, BigDecimal should be used. I disable the test because it is requested in this way.")
    @Test
    void givenEmptyAccount_whenDepositFloatingPointTwice_thenIncreaseBalance() {
        assertEquals(Amount.ZERO, bankAccount.getBalance());
        bankAccount.deposit(Amount.of(30.10));
        assertEquals(Amount.of(30.10), bankAccount.getBalance());

        bankAccount.deposit(Amount.of(30.02));
        assertEquals(Amount.of(60.03), bankAccount.getBalance());
    }
}
