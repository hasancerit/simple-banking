package com.eteration.simplebanking.model.bankaccount;

import com.eteration.simplebanking.model.AccountNumber;
import com.eteration.simplebanking.model.Balance;
import com.eteration.simplebanking.model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountDepositTest {
    private BankAccount bankAccount;

    @BeforeEach
    public void createEmptyAccount() {
        bankAccount = BankAccount.builder()
                .balance(Balance.ZERO)
                .accountNumber(AccountNumber.of("111-2222"))
                .build();
    }

    @Test
    public void givenEmptyAccount_whenDeposit_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Balance.ZERO);
        bankAccount.deposit(100.0);
        assertEquals(bankAccount.getBalance(), Balance.of(100.0));
    }

    @Test
    public void givenEmptyAccount_whenDepositTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Balance.ZERO);
        bankAccount.deposit(100.0);
        assertEquals(bankAccount.getBalance(), Balance.of(100.0));

        bankAccount.deposit(53.0);
        assertEquals(bankAccount.getBalance(), Balance.of(153.0));
    }

    @Test
    public void givenEmptyAccount_whenDepositFloatingPoint_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Balance.ZERO);
        bankAccount.deposit(64.45);
        assertEquals(bankAccount.getBalance(), Balance.of(64.45));
    }

    @Disabled("To fix this problem, bigdecimal should be used. I disable the test because it is requested in this way.")
    @Test
    public void givenEmptyAccount_whenDepositFloatingPointTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Balance.ZERO);
        bankAccount.deposit(30.10);
        assertEquals(bankAccount.getBalance(), Balance.of(30.10));

        bankAccount.deposit(30.02);
        assertEquals(bankAccount.getBalance(), Balance.of(60.03));
    }
}
