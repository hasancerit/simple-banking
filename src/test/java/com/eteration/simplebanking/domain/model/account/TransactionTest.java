package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {
    @Test
    public void givenTransactionsAccountIsNotSet_whenDepositMakeChangesOnBankAccount_thenReturnException() {
        Transaction transaction = new DepositTransaction(Amount.of(100.0));
        assertThrows(RuntimeException.class, transaction::makeChangesOnBankAccount);
    }

    @Test
    public void givenTransactionsAccountIsNotSet_whenWithdrawMakeChangesOnBankAccount_thenReturnException() {
        Transaction transaction = new WithdrawTransaction(Amount.of(100.0));
        assertThrows(RuntimeException.class, transaction::makeChangesOnBankAccount);
    }
}
