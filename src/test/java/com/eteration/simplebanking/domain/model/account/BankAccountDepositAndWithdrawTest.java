package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.exception.InsufficientBalanceException;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.util.BankAccountTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountDepositAndWithdrawTest {

    @Test
    void givenAccountWithoutTransaction_whenDeposit_thenIncreaseBalance() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

        bankAccount.deposit(Amount.of(100.0));
        assertEquals(Amount.of(100.0), bankAccount.getBalance());

        bankAccount.deposit(Amount.of(64.45));
        assertEquals(Amount.of(164.45), bankAccount.getBalance());
    }

    @Test
    void givenAccountWithTransaction_whenWithdrawTwice_thenSubtractBalance() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
                Amount.of(100.0)
        );

        bankAccount.withdraw(Amount.of(50.0));
        assertEquals(Amount.of(50.0), bankAccount.getBalance());

        bankAccount.withdraw(Amount.of(20.5));
        assertEquals(Amount.of(29.5), bankAccount.getBalance());
    }

    @Test
    void givenAccountWithTransaction_whenWithdrawBalancePlusOne_thenThrowError() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
                Amount.of(100.0)
        );

        final Amount balancePlusOne = Amount.of(bankAccount.getBalance().value() + 1.0);
        assertThrows(InsufficientBalanceException.class, () -> bankAccount.withdraw(balancePlusOne));
    }

    @Test
    void givenAccountWithoutTransaction_whenDepositAndWithdraw_thenCalculateTheFinalAmount() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

        bankAccount.deposit(Amount.of(55.0));
        assertEquals(Amount.of(55.0), bankAccount.getBalance());

        bankAccount.withdraw(Amount.of(30.0));
        assertEquals(Amount.of(25.0), bankAccount.getBalance());
    }
}
