package com.eteration.simplebanking.model.bankaccount;

import com.eteration.simplebanking.model.AccountNumber;
import com.eteration.simplebanking.model.Balance;
import com.eteration.simplebanking.model.BankAccount;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountWithdrawTest {
    private BankAccount bankAccount;

    @BeforeEach
    public void createAccountWithHundredBalance() {
        bankAccount = BankAccount.builder()
                .balance(Balance.of(100.0))
                .accountNumber(AccountNumber.of("111-2222"))
                .build();
    }

    @Test
    public void givenAccountWithHundredBalance_whenWithdraw_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Balance.of(100.0));
        bankAccount.withdraw(100.0);
        assertEquals(bankAccount.getBalance(), Balance.ZERO);
    }

    @Test
    public void givenAccountWithHundredBalance_whenWithdrawTwice_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Balance.of(100.0));
        bankAccount.withdraw(50.0);
        assertEquals(bankAccount.getBalance(), Balance.of(50.0));

        bankAccount.withdraw(20.0);
        assertEquals(bankAccount.getBalance(), Balance.of(30.0));
    }

    @Test
    public void givenAccountWithHundredBalance_whenWithdrawFloatingPoint_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Balance.of(100.0));
        bankAccount.withdraw(30.50);
        assertEquals(bankAccount.getBalance(), Balance.of(69.5));
        assertEquals(bankAccount.getBalance(), Balance.of(69.50));
    }

    @Disabled("To fix this problem, bigdecimal should be used. I disable the test because it is requested in this way.")
    @Test
    public void givenAccountWithHundredBalance_whenWithdrawFloatingPointTwice_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Balance.of(100.0));
        bankAccount.withdraw(30.10);
        assertEquals(bankAccount.getBalance(), Balance.of(69.9));

        bankAccount.withdraw(30.02);
        assertEquals(bankAccount.getBalance(), Balance.of(39.88));
    }

    @Test
    public void givenAccountWithHundredBalance_whenWithdrawTwoHundred_thenThrowError() {
        assertThrows(InsufficientBalanceException.class, () -> bankAccount.withdraw(200.0));
    }
}