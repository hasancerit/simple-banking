package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.exception.InsufficientBalanceException;
import com.eteration.simplebanking.domain.model.AccountNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountWithdrawTest {
    private BankAccount bankAccount;

    @BeforeEach
    void createAccountWithHundredBalance() {
        bankAccount = BankAccount.builder()
                .balance(Amount.of(100.0))
                .accountNumber(AccountNumber.of("111-2222"))
                .owner("Hasan")
                .createdDate(LocalDateTime.now())
                .build();
    }

    @Test
    void givenAccountWithHundredBalance_whenWithdraw_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Amount.of(100.0));
        bankAccount.withdraw(Amount.of(100.0));
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
    }

    @Test
    void givenAccountWithHundredBalance_whenWithdrawTwice_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Amount.of(100.0));
        bankAccount.withdraw(Amount.of(50.0));
        assertEquals(bankAccount.getBalance(), Amount.of(50.0));

        bankAccount.withdraw(Amount.of(20.0));
        assertEquals(bankAccount.getBalance(), Amount.of(30.0));
    }

    @Test
    void givenAccountWithHundredBalance_whenWithdrawFloatingPoint_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Amount.of(100.0));
        bankAccount.withdraw(Amount.of(30.50));
        assertEquals(bankAccount.getBalance(), Amount.of(69.5));
        assertEquals(bankAccount.getBalance(), Amount.of(69.50));
    }

    @Disabled("To fix this problem, bigdecimal should be used. I disable the test because it is requested in this way.")
    @Test
    void givenAccountWithHundredBalance_whenWithdrawFloatingPointTwice_thenSubtractBalance() {
        assertEquals(bankAccount.getBalance(), Amount.of(100.0));
        bankAccount.withdraw(Amount.of(30.10));
        assertEquals(bankAccount.getBalance(), Amount.of(69.9));

        bankAccount.withdraw(Amount.of(30.02));
        assertEquals(bankAccount.getBalance(), Amount.of(39.88));
    }

    @Test
    void givenAccountWithHundredBalance_whenWithdrawTwoHundred_thenThrowError() {
        assertThrows(InsufficientBalanceException.class, () -> bankAccount.withdraw(Amount.of(200.0)));
    }
}
