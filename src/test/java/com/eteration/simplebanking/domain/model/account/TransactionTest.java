package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {
    @Test
    public void givenTransactionsAccountIsNotSet_whenMakeChangesOnBankAccount_thenReturnException() {
        Transaction transaction = new DepositTransaction(Amount.of(100.0));
        assertThrows(RuntimeException.class, transaction::makeChangesOnBankAccount);
    }
}
