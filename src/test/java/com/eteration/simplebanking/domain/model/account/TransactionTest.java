package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import com.eteration.simplebanking.util.BankAccountTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {
    @Test
    void givenAnAccountToDepositTransaction_whenExecuteTransaction_thenIncreaseBalanceOfAccount() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

        final Transaction depositTransaction = new DepositTransaction(Amount.of(10.0));

        depositTransaction.executeTransactionIn(bankAccount);

        assertEquals(Amount.of(10.0), bankAccount.getBalance());
        assertEquals(bankAccount.getTransactions().size(), 0);
        //DepositTransaction.makeChangesOnBankAccount function not taking the responsibility of add transaction to BankAccount.transactions
        //May be better pattern could be implemented for consistency
    }

    @Test
    void givenAnAccountToWithdrawalTransaction_whenExecuteTransaction_thenReduceBalanceOfAccount() {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
                Amount.of(20.0)
        );

        final Transaction withdrawTransaction = new WithdrawTransaction(Amount.of(20.0));

        withdrawTransaction.executeTransactionIn(bankAccount);

        assertEquals(Amount.ZERO, bankAccount.getBalance());
        assertEquals(bankAccount.getTransactions().size(), 0);
        //WithdrawalTransaction_.makeChangesOnBankAccount function not taking the responsibility of add transaction to BankAccount.transactions
        //This responsibility belong BankAccount.Post
        //May be better pattern could be implemented for consistency
    }
}
