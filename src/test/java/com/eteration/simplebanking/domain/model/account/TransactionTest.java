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
        BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

        Transaction depositTransaction = new DepositTransaction(Amount.of(10.0));

        depositTransaction.executeTransactionIn(bankAccount);

        assertEquals(bankAccount.getBalance(), Amount.of(10.0));
        //assertEquals(bankAccount.getTransactions().size(), 1);
        //Transaction.makeChangesOnBankAccount function not taking the responsibility of add transaction to BankAccount.transactions
        //This responsibility belong BankAccount.Post
    }

    @Test
    void givenAnAccountToWithdrawalTransaction_whenExecuteTransaction_thenReduceBalanceOfAccount() {
        BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
                Amount.of(20.0)
        );

        Transaction withdrawTransaction = new WithdrawTransaction(Amount.of(20.0));

        withdrawTransaction.executeTransactionIn(bankAccount);

        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        //assertEquals(bankAccount.getTransactions().size(), 1);
        //Transaction.makeChangesOnBankAccount function not taking the responsibility of add transaction to BankAccount.transactions
        //This responsibility belong BankAccount.Post
    }
}
