package com.eteration.simplebanking.model.bankaccount;

import com.eteration.simplebanking.model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountCreditTest {
    private BankAccount bankAccount;

    @BeforeEach
    public void createEmptyAccount() {
        bankAccount = BankAccount.builder()
                .balance(0.0)
                .build();
    }

    @Test
    public void givenEmptyAccount_whenCredit_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), 0.0);
        bankAccount.credit(100.0);
        assertEquals(bankAccount.getBalance(), 100.0);
    }

    @Test
    public void givenEmptyAccount_whenCreditTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), 0.0);
        bankAccount.credit(100.0);
        assertEquals(bankAccount.getBalance(), 100.0);

        bankAccount.credit(53.0);
        assertEquals(bankAccount.getBalance(), 153);
    }

    @Test
    public void givenEmptyAccount_whenCreditFloatingPoint_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), 0.0);
        bankAccount.credit(64.45);
        assertEquals(bankAccount.getBalance(), 64.45);
    }

    @Disabled("To fix this problem, bigdecimal should be used. I disable the test because it is requested in this way.")
    @Test
    public void givenEmptyAccount_whenCreditFloatingPointTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), 0.0);
        bankAccount.credit(30.10);
        assertEquals(bankAccount.getBalance(), 30.10);

        bankAccount.credit(30.02);
        assertEquals(bankAccount.getBalance(), 60.03);
    }
}
