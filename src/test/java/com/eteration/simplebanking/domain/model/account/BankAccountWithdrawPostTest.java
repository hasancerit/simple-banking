package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountWithdrawPostTest {
    private BankAccount bankAccount;

    @BeforeEach
    void createAccountWithHundredBalance() {
        bankAccount = BankAccount.builder()
                .balance(Amount.of(100.0))
                .accountNumber(AccountNumber.of("111-2222"))
                .owner("Hasan")
                .createdDate(LocalDateTime.now())
                .build();
    }

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
