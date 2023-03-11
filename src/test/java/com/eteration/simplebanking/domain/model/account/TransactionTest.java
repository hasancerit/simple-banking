package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {
    @Test
    public void givenAnAccountToDepositTransaction_whenTransactionMakeChangesOnBankAccount_thenIncreaseBalanceOfAccount() {
        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("111-2222"))
                .balance(Amount.of(0.0))
                .build();

        Transaction depositTransaction = new DepositTransaction(Amount.of(10.0));
        depositTransaction.setBankAccount(bankAccount);

        depositTransaction.makeChangesOnBankAccount();

        assertEquals(bankAccount.getBalance(), Amount.of(10.0));
        //assertEquals(bankAccount.getTransactions().size(), 1);
        //TODO: Şu an bankaccount'a Transaction ekleme işini makeChangesOnBankAccount yapmıyor. O işi BankAccount'a bıraktım.
        //Böyle mi olması gerektğini düşün, bu sınıf ilişkililerini de düşünmeyi gerektiriyor
        //Belki Transaction sınıfında BankAccount tutmayız, makeChangesOnBankAccount'a BankAccount'ı aktarırız.
        //Bu sayede makeChangesOnBankAccount farklı bir yere taşınabilir.
    }

    @Test
    public void givenAnAccountToWithdrawalTransaction_whenTransactionMakeChangesOnBankAccount_thenReduceBalanceOfAccount() {
        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("111-2222"))
                .balance(Amount.of(20.0))
                .build();

        Transaction withdrawTransaction = new WithdrawTransaction(Amount.of(20.0));
        withdrawTransaction.setBankAccount(bankAccount);

        withdrawTransaction.makeChangesOnBankAccount();

        assertEquals(bankAccount.getBalance(), Amount.of(0.0));
        //assertEquals(bankAccount.getTransactions().size(), 1);
        //TODO: Şu an bankaccount'a Transaction ekleme işini makeChangesOnBankAccount yapmıyor. O işi BankAccount'a bıraktım.
        //Böyle mi olması gerektğini düşün, bu sınıf ilişkililerini de düşünmeyi gerektiriyor
        //Belki Transaction sınıfında BankAccount tutmayız, makeChangesOnBankAccount'a BankAccount'ı aktarırız.
        //Bu sayede makeChangesOnBankAccount farklı bir yere taşınabilir.
    }

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
