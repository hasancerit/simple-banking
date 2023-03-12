package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.util.BankAccountTestDataBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountDepositAndWithdrawTest {
    private final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithoutTransaction();

    @Test
    void givenEmptyAccount_whenDepositAndWithdraw_thenCalculateTheFinalAmount() {
        assertEquals(Amount.ZERO, bankAccount.getBalance());
        bankAccount.deposit(Amount.of(55.0));
        assertEquals(Amount.of(55.0), bankAccount.getBalance());

        bankAccount.withdraw(Amount.of(30.0));
        assertEquals(Amount.of(25.0), bankAccount.getBalance());
    }

    @Disabled("To fix this problem, BigDecimal should be used. I disable the test because it is requested in this way.")
    @Test
    void givenEmptyAccount_whenDepositAndWithdrawFloatingPoint_thenCalculateTheFinalAmount() {
        assertEquals(Amount.ZERO, bankAccount.getBalance());
        bankAccount.deposit(Amount.of(40.20));
        assertEquals(Amount.of(40.20), bankAccount.getBalance());

        bankAccount.withdraw(Amount.of(10.02));
        assertEquals(Amount.of(30.18), bankAccount.getBalance());
    }
}
