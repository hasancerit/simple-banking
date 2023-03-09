package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountDepositPostTest {
    private BankAccount bankAccount;

    @BeforeEach
    public void createEmptyAccount() {
        bankAccount = BankAccount.builder()
                .balance(Amount.ZERO)
                .accountNumber(AccountNumber.of("111-2222"))
                .build();
    }

    @Test
    public void givenEmptyAccount_whenPostDeposit_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        assertEquals(bankAccount.getTransactions().size(), 0);

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(bankAccount.getBalance(), Amount.of(55.0));
        assertEquals(bankAccount.getTransactions().size(), 1);
    }

    @Test
    public void givenEmptyAccount_whenPostDepositTwice_thenIncreaseBalance() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        assertEquals(bankAccount.getTransactions().size(), 0);

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(bankAccount.getBalance(), Amount.of(55.0));
        assertEquals(bankAccount.getTransactions().size(), 1);

        bankAccount.post(new DepositTransaction(Amount.of(33.50)));
        assertEquals(bankAccount.getBalance(), Amount.of(88.50));
        assertEquals(bankAccount.getTransactions().size(), 2);
    }
}
