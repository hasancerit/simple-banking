package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.util.BankAccountTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountDepositPostTest {
    private final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

    @Test
    void givenEmptyAccount_whenPostDeposit_thenIncreaseBalance() {
        assertEquals(Amount.ZERO, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(55.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
    }

    @Test
    void givenEmptyAccount_whenPostDepositTwice_thenIncreaseBalance() {
        assertEquals(Amount.ZERO, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(55.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());

        bankAccount.post(new DepositTransaction(Amount.of(33.50)));
        assertEquals(Amount.of(88.50), bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());
    }
}
