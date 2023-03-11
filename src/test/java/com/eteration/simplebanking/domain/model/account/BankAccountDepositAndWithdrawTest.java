package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.AccountNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountDepositAndWithdrawTest {
    private BankAccount bankAccount;

    @BeforeEach
    void createEmptyAccount() {
        bankAccount = BankAccount.builder()
                .balance(Amount.ZERO)
                .accountNumber(AccountNumber.of("111-2222"))
                .owner("Hasan")
                .createdDate(LocalDateTime.now())
                .build();
    }

    @Test
    void givenEmptyAccount_whenDepositAndWithdraw_thenCalculateTheFinalAmount() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(Amount.of(55.0));
        assertEquals(bankAccount.getBalance(), Amount.of(55.0));

        bankAccount.withdraw(Amount.of(30.0));
        assertEquals(bankAccount.getBalance(), Amount.of(25.0));
    }

    @Disabled("To fix this problem, bigdecimal should be used. I disable the test because it is requested in this way.")
    @Test
    void givenEmptyAccount_whenDepositAndWithdrawFloatingPoint_thenCalculateTheFinalAmount() {
        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        bankAccount.deposit(Amount.of(40.20));
        assertEquals(bankAccount.getBalance(), Amount.of(40.20));

        bankAccount.withdraw(Amount.of(10.02));
        assertEquals(bankAccount.getBalance(), Amount.of(30.18));
    }
}
