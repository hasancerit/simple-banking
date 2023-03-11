package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import com.eteration.simplebanking.util.BankAccountTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountWithdrawPostTest {
    private final BankAccount bankAccount = BankAccountTestDataBuilder.notEmptyTransactionBankAccount(
            Amount.of(100.0)
    );

    @Test
    void givenAccountWithHundredBalance_whenPostWithdraw_thenIncreaseBalance() {
        assertEquals(Amount.of(100.0), bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());

        bankAccount.post(new WithdrawTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(45.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
    }

    @Test
    void givenAccountWithHundredBalance_whenPostWithdrawTwice_thenIncreaseBalance() {
        assertEquals(Amount.of(100.0), bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());

        bankAccount.post(new WithdrawTransaction(Amount.of(30.0)));
        assertEquals(Amount.of(70.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());

        bankAccount.post(new WithdrawTransaction(Amount.of(33.50)));
        assertEquals(Amount.of(36.50), bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());
    }
}
