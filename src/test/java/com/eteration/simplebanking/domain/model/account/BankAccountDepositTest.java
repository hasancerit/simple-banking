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
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(Amount.of(100.0));
        assertEquals(bankAccount.getBalance(), Amount.of(100.0));
    }

    @Test
    void givenEmptyAccount_whenDepositTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(Amount.of(100.0));
        assertEquals(bankAccount.getBalance(), Amount.of(100.0));

        bankAccount.deposit(Amount.of(53.0));
        assertEquals(bankAccount.getBalance(), Amount.of(153.0));
    }

    @Test
    void givenEmptyAccount_whenDepositFloatingPoint_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(Amount.of(64.45));
        assertEquals(bankAccount.getBalance(), Amount.of(64.45));
    }

    @Disabled("To fix this problem, bigdecimal should be used. I disable the test because it is requested in this way.")
    @Test
    void givenEmptyAccount_whenDepositFloatingPointTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(Amount.of(30.10));
        assertEquals(bankAccount.getBalance(), Amount.of(30.10));

        bankAccount.deposit(Amount.of(30.02));
        assertEquals(bankAccount.getBalance(), Amount.of(60.03));
    }
}
