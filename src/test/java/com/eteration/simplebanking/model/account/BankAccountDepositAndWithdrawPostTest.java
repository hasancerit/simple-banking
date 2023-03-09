package com.eteration.simplebanking.model.account;

import com.eteration.simplebanking.model.Amount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountDepositAndWithdrawPostTest {
    private final BankAccount bankAccount = BankAccount.builder()
            .balance(Amount.ZERO)
            .accountNumber(AccountNumber.of("111-2222"))
            .build();

    @Test
    public void givenEmptyAccount_whenDepositAndWithdrawPost_thenCalculateTheFinalAmount() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        assertEquals(bankAccount.getTransactions().size(), 0);

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(bankAccount.getBalance(), Amount.of(55.0));
        assertEquals(bankAccount.getTransactions().size(), 1);

        bankAccount.post(new WithdrawTransaction(Amount.of(30.0)));
        assertEquals(bankAccount.getBalance(), Amount.of(25.0));
        assertEquals(bankAccount.getTransactions().size(), 2);
    }
}
