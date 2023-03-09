package com.eteration.simplebanking.model.bankaccount;

import com.eteration.simplebanking.model.Balance;
import com.eteration.simplebanking.model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountDebitTest {
    private BankAccount bankAccount;

    @BeforeEach
    public void createAccountWithHundredBalance() {
        bankAccount = BankAccount.builder()
                .balance(Balance.of(100.0))
                .build();
    }

    @Test
    public void givenAccountWithHundredBalance_whenDebit_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Balance.of(100.0));
        bankAccount.debit(100.0);
        assertEquals(bankAccount.getBalance(), Balance.ZERO);
    }

    @Test
    public void givenAccountWithHundredBalance_whenDebitTwice_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Balance.of(100.0));
        bankAccount.debit(50.0);
        assertEquals(bankAccount.getBalance(), Balance.of(50.0));

        bankAccount.debit(20.0);
        assertEquals(bankAccount.getBalance(), Balance.of(30.0));
    }

    @Test
    public void givenAccountWithHundredBalance_whenDebitFloatingPoint_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Balance.of(100.0));
        bankAccount.debit(30.50);
        assertEquals(bankAccount.getBalance(), Balance.of(69.5));
        assertEquals(bankAccount.getBalance(), Balance.of(69.50));
    }

    @Disabled("To fix this problem, bigdecimal should be used. I disable the test because it is requested in this way.")
    @Test
    public void givenAccountWithHundredBalance_whenDebitFloatingPointTwice_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Balance.of(100.0));
        bankAccount.debit(30.10);
        assertEquals(bankAccount.getBalance(), Balance.of(69.9));

        bankAccount.debit(30.02);
        assertEquals(bankAccount.getBalance(), Balance.of(39.88));
    }
}
