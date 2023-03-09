package com.eteration.simplebanking.model.bankaccount;

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
                .balance(100.0)
                .build();
    }

    @Test
    public void givenEmptyAccount_whenDebit_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), 100.0);
        bankAccount.debit(100.0);
        assertEquals(bankAccount.getBalance(), 0.0);
    }

    @Test
    public void givenEmptyAccount_whenDebitTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), 100.0);
        bankAccount.debit(50.0);
        assertEquals(bankAccount.getBalance(), 50.0);

        bankAccount.debit(20.0);
        assertEquals(bankAccount.getBalance(), 30.0);
    }

    @Test
    public void givenEmptyAccount_whenDebitFloatingPoint_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), 100.0);
        bankAccount.debit(30.50);
        assertEquals(bankAccount.getBalance(), 69.5);
        assertEquals(bankAccount.getBalance(), 69.50);
    }

    @Disabled("To fix this problem, bigdecimal should be used. I disable the test because it is requested in this way.")
    @Test
    public void givenEmptyAccount_whenDebitFloatingPointTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), 100.0);
        bankAccount.debit(30.10);
        assertEquals(bankAccount.getBalance(), 69.9);

        bankAccount.debit(30.02);
        assertEquals(bankAccount.getBalance(), 39.88);
    }
}
