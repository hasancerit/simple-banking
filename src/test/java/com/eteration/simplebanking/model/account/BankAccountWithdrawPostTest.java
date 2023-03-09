package com.eteration.simplebanking.model.account;

import com.eteration.simplebanking.model.Amount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountWithdrawPostTest {
    private BankAccount bankAccount;

    @BeforeEach
    public void createAccountWithHundredBalance() {
        bankAccount = BankAccount.builder()
                .balance(Amount.of(100.0))
                .accountNumber(AccountNumber.of("111-2222"))
                .build();
    }

    @Test
    public void givenAccountWithHundredBalance_whenPostWithdraw_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Amount.of(100.0));
        assertEquals(bankAccount.getTransactions().size(), 0);

        bankAccount.post(new WithdrawTransaction(Amount.of(55.0)));
        assertEquals(bankAccount.getBalance(), Amount.of(45.0));
        assertEquals(bankAccount.getTransactions().size(), 1);
    }

    @Test
    public void givenAccountWithHundredBalance_whenPostWithdrawTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Amount.of(100.0));
        assertEquals(bankAccount.getTransactions().size(), 0);

        bankAccount.post(new WithdrawTransaction(Amount.of(30.0)));
        assertEquals(bankAccount.getBalance(), Amount.of(70.0));
        assertEquals(bankAccount.getTransactions().size(), 1);

        bankAccount.post(new WithdrawTransaction(Amount.of(33.50)));
        assertEquals(bankAccount.getBalance(), Amount.of(36.50));
        assertEquals(bankAccount.getTransactions().size(), 2);
    }
}
