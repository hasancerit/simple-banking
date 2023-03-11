package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {
    @Test
    void givenAnAccountToDepositTransaction_whenTransactionMakeChangesOnBankAccount_thenIncreaseBalanceOfAccount() {
        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("111-2222"))
                .owner("Hasan")
                .build();

        Transaction depositTransaction = new DepositTransaction(Amount.of(10.0));

        depositTransaction.makeChangesOnBankAccount(bankAccount);

        assertEquals(bankAccount.getBalance(), Amount.of(10.0));
        //assertEquals(bankAccount.getTransactions().size(), 1);
        //BankAccount.transactions'a Transaction ekleme işini makeChangesOnBankAccount yapmıyor. O işi BankAccount.Post'a bıraktım.
    }

    @Test
    void givenAnAccountToWithdrawalTransaction_whenTransactionMakeChangesOnBankAccount_thenReduceBalanceOfAccount() {
        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("111-2222"))
                .balance(Amount.of(20.0))
                .owner("Hasan")
                .build();

        Transaction withdrawTransaction = new WithdrawTransaction(Amount.of(20.0));

        withdrawTransaction.makeChangesOnBankAccount(bankAccount);

        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        //assertEquals(bankAccount.getTransactions().size(), 1);
        //BankAccount.transactions'a Transaction ekleme işini makeChangesOnBankAccount yapmıyor. O işi BankAccount.Post'a bıraktım.
    }
}
