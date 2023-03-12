package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.BillPaymentTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import com.eteration.simplebanking.util.builder.BankAccountTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountPostTest {
    @Test
    void givenAccountWithoutTransaction_whenDepositAndWithdrawPost_thenCalculateTheFinalAmount() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(55.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());

        bankAccount.post(new WithdrawTransaction(Amount.of(30.0)));
        assertEquals(Amount.of(25.0), bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());
    }

    @Test
    void givenAccountWithoutTransaction_whenPostDeposit_thenIncreaseBalance() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(55.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
    }

    @Test
    void givenAccountWithoutTransaction_whenPostDepositTwice_thenIncreaseBalance() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(55.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());

        bankAccount.post(new DepositTransaction(Amount.of(33.50)));
        assertEquals(Amount.of(88.50), bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());
    }

    @Test
    void givenAccountAccountWithTransaction_whenPostWithdraw_thenIncreaseBalance() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
                Amount.of(100.0)
        );

        bankAccount.post(new WithdrawTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(45.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
    }

    @Test
    void givenAccountAccountWithTransaction_whenPostWithdrawTwice_thenIncreaseBalance() {
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

    @Test
    void givenAccountWithTransaction_whenPostDepositWithdrawAndBillPayment_thenCalculateBalance() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
                Amount.of(100.0)
        );

        bankAccount.post(new DepositTransaction(Amount.of(60.0)));
        bankAccount.post(new WithdrawTransaction(Amount.of(15.0)));
        bankAccount.post(new BillPaymentTransaction(Amount.of(30.0), "1234", "Vodafone"));

        assertEquals(Amount.of(115.0), bankAccount.getBalance());
        assertEquals(3, bankAccount.getTransactions().size());
    }
}
