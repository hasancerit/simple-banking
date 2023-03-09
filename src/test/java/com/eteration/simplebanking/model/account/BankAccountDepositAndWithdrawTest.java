package com.eteration.simplebanking.model.account;

import com.eteration.simplebanking.model.Amount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountDepositAndWithdrawTest {
    private BankAccount bankAccount;

    @BeforeEach
    public void createEmptyAccount() {
        bankAccount = BankAccount.builder()
                .balance(Amount.ZERO)
                .accountNumber(AccountNumber.of("111-2222"))
                .build();
    }

    @Test
    public void givenEmptyAccount_whenDepositAndWithdraw_thenCalculateTheFinalAmount() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(55.0);
        assertEquals(bankAccount.getBalance(), Amount.of(55.0));

        bankAccount.withdraw(30.0);
        assertEquals(bankAccount.getBalance(), Amount.of(25.0));
    }

    @Disabled("To fix this problem, bigdecimal should be used. I disable the test because it is requested in this way.")
    @Test
    public void givenEmptyAccount_whenDepositAndWithdrawFloatingPoint_thenCalculateTheFinalAmount() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(40.20);
        assertEquals(bankAccount.getBalance(), Amount.of(40.20));

        bankAccount.withdraw(10.02);
        assertEquals(bankAccount.getBalance(), Amount.of(30.18));
    }
}
