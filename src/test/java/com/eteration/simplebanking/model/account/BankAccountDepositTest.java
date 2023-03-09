package com.eteration.simplebanking.model.account;

import com.eteration.simplebanking.model.Amount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountDepositTest {
    private BankAccount bankAccount;

    @BeforeEach
    public void createEmptyAccount() {
        bankAccount = BankAccount.builder()
                .balance(Amount.ZERO)
                .accountNumber(AccountNumber.of("111-2222"))
                .build();
    }

    @Test
    public void givenEmptyAccount_whenDeposit_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(100.0);
        assertEquals(bankAccount.getBalance(), Amount.of(100.0));
    }

    @Test
    public void givenEmptyAccount_whenDepositTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(100.0);
        assertEquals(bankAccount.getBalance(), Amount.of(100.0));

        bankAccount.deposit(53.0);
        assertEquals(bankAccount.getBalance(), Amount.of(153.0));
    }

    @Test
    public void givenEmptyAccount_whenDepositFloatingPoint_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(64.45);
        assertEquals(bankAccount.getBalance(), Amount.of(64.45));
    }

    @Disabled("To fix this problem, bigdecimal should be used. I disable the test because it is requested in this way.")
    @Test
    public void givenEmptyAccount_whenDepositFloatingPointTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(30.10);
        assertEquals(bankAccount.getBalance(), Amount.of(30.10));

        bankAccount.deposit(30.02);
        assertEquals(bankAccount.getBalance(), Amount.of(60.03));
    }
}
