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
        BankAccount bankAccount = BankAccountTestDataBuilder.emptyTransactionBankAccount();

        Transaction depositTransaction = new DepositTransaction(Amount.of(10.0));

        depositTransaction.executeTransactionIn(bankAccount);

        assertEquals(bankAccount.getBalance(), Amount.of(10.0));
        //assertEquals(bankAccount.getTransactions().size(), 1);
        //BankAccount.transactions'a Transaction ekleme işini makeChangesOnBankAccount yapmıyor. O işi BankAccount.Post'a bıraktım.
    }

    @Test
    void givenAnAccountToWithdrawalTransaction_whenExecuteTransaction_thenReduceBalanceOfAccount() {
        BankAccount bankAccount = BankAccountTestDataBuilder.notEmptyTransactionBankAccount(
                Amount.of(20.0)
        );

        Transaction withdrawTransaction = new WithdrawTransaction(Amount.of(20.0));

        withdrawTransaction.executeTransactionIn(bankAccount);

        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        //assertEquals(bankAccount.getTransactions().size(), 1);
        //BankAccount.transactions'a Transaction ekleme işini makeChangesOnBankAccount yapmıyor. O işi BankAccount.Post'a bıraktım.
    }
}
