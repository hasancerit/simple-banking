package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountServiceImp accountServiceImp;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    public void givenEmptyAccount_whenCredit_thenIncreaseBalance() {
        BankAccount bankAccount = BankAccount.builder()
                .balance(Amount.ZERO)
                .accountNumber(AccountNumber.of("111-2222"))
                .build();

        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        assertEquals(bankAccount.getTransactions().size(), 0);

        when(bankAccountRepository.get(bankAccount.getAccountNumber().value()))
                .thenReturn(Optional.of(bankAccount));

        accountServiceImp.credit(bankAccount.getAccountNumber().value(), 56.0);
        assertEquals(bankAccount.getBalance(), Amount.of(56.0));
        assertEquals(bankAccount.getTransactions().size(), 1);
    }

    @Test
    public void givenAccountWithHundredBalance_whenDebit_thenSubtractBalance() {
        BankAccount bankAccount = BankAccount.builder()
                .balance(Amount.of(100.0))
                .accountNumber(AccountNumber.of("111-2222"))
                .build();

        assertEquals(bankAccount.getBalance(), Amount.of(100.0));
        assertEquals(bankAccount.getTransactions().size(), 0);

        when(bankAccountRepository.get(bankAccount.getAccountNumber().value()))
                .thenReturn(Optional.of(bankAccount));

        accountServiceImp.debit(bankAccount.getAccountNumber().value(), 56.0);
        assertEquals(bankAccount.getBalance(), Amount.of(44.0));
        assertEquals(bankAccount.getTransactions().size(), 1);
    }

    @Test
    public void givenEmptyAccount_whenCreditAndDebit_thenIncreaseBalance() {
        BankAccount bankAccount = BankAccount.builder()
                .balance(Amount.ZERO)
                .accountNumber(AccountNumber.of("111-2222"))
                .build();

        assertEquals(bankAccount.getBalance(), Amount.ZERO);
        assertEquals(bankAccount.getTransactions().size(), 0);

        when(bankAccountRepository.get(bankAccount.getAccountNumber().value()))
                .thenReturn(Optional.of(bankAccount));

        accountServiceImp.credit(bankAccount.getAccountNumber().value(), 56.0);
        assertEquals(bankAccount.getBalance(), Amount.of(56.0));
        assertEquals(bankAccount.getTransactions().size(), 1);

        accountServiceImp.debit(bankAccount.getAccountNumber().value(), 10.0);
        assertEquals(bankAccount.getBalance(), Amount.of(46.0));
        assertEquals(bankAccount.getTransactions().size(), 2);
    }

    @Test
    public void givenNotExistAccountNumber_whenCredit_thenThrowError() {
        final String accountNumber = "111-2222";

        when(bankAccountRepository.get(accountNumber))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> accountServiceImp.credit(accountNumber, 56.0));
    }

    @Test
    public void givenNotExistAccountNumber_whenDebit_thenThrowError() {
        final String accountNumber = "111-2222";

        when(bankAccountRepository.get(accountNumber))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> accountServiceImp.debit(accountNumber, 56.0));
    }
}
