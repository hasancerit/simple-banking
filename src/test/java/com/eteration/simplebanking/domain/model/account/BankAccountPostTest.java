package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import com.eteration.simplebanking.util.BankAccountTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountPostTest {
    @Test
    void givenEmptyAccount_whenDepositAndWithdrawPost_thenCalculateTheFinalAmount() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(55.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());

        bankAccount.post(new WithdrawTransaction(Amount.of(30.0)));
        assertEquals(Amount.of(25.0), bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());
    }

    @Test
    void givenEmptyAccount_whenPostDeposit_thenIncreaseBalance() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(55.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
    }

    @Test
    void givenEmptyAccount_whenPostDepositTwice_thenIncreaseBalance() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(55.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());

        bankAccount.post(new DepositTransaction(Amount.of(33.50)));
        assertEquals(Amount.of(88.50), bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());
    }

    @Test
    void givenAccountWithHundredBalance_whenPostWithdraw_thenIncreaseBalance() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
                Amount.of(100.0)
        );

        bankAccount.post(new WithdrawTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(45.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
    }

    @Test
    void givenAccountWithHundredBalance_whenPostWithdrawTwice_thenIncreaseBalance() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
                Amount.of(100.0)
        );

        bankAccount.post(new WithdrawTransaction(Amount.of(30.0)));
        assertEquals(Amount.of(70.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());

        bankAccount.post(new WithdrawTransaction(Amount.of(33.50)));
        assertEquals(Amount.of(36.50), bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());
    }
}
