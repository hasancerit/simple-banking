package com.eteration.simplebanking.domain.model.account;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountDepositAndWithdrawPostTest {
    private final BankAccount bankAccount = BankAccount.builder()
            .balance(Amount.ZERO)
            .accountNumber(AccountNumber.of("111-2222"))
            .build();

    @Test
    void givenEmptyAccount_whenDepositAndWithdrawPost_thenCalculateTheFinalAmount() {
        assertEquals(Amount.ZERO, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());

        bankAccount.post(new DepositTransaction(Amount.of(55.0)));
        assertEquals(Amount.of(55.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());

        bankAccount.post(new WithdrawTransaction(Amount.of(30.0)));
        assertEquals(Amount.of(25.0), bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());
    }
}
