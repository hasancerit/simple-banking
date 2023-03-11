package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.repository.BankAccountRepository;
import com.eteration.simplebanking.service.exception.BankAccountNotFoundException;
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
class AccountServiceTest {
    @InjectMocks
    private AccountServiceImp accountServiceImp;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    void givenEmptyAccount_whenCredit_thenIncreaseBalance() {
        BankAccount bankAccount = BankAccount.builder()
                .balance(Amount.ZERO)
                .accountNumber(AccountNumber.of("111-2222"))
                .build();

        assertEquals(Amount.ZERO, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());

        when(bankAccountRepository.get(bankAccount.getAccountNumber()))
                .thenReturn(Optional.of(bankAccount));

        accountServiceImp.credit(bankAccount.getAccountNumber().value(), 56.0);
        assertEquals(Amount.of(56.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
    }

    @Test
    void givenAccountWithHundredBalance_whenDebit_thenSubtractBalance() {
        BankAccount bankAccount = BankAccount.builder()
                .balance(Amount.of(100.0))
                .accountNumber(AccountNumber.of("111-2222"))
                .build();

        assertEquals(Amount.of(100.0), bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());

        when(bankAccountRepository.get(bankAccount.getAccountNumber()))
                .thenReturn(Optional.of(bankAccount));

        accountServiceImp.debit(bankAccount.getAccountNumber().value(), 56.0);
        assertEquals(Amount.of(44.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
    }

    @Test
    void givenEmptyAccount_whenCreditAndDebit_thenIncreaseBalance() {
        BankAccount bankAccount = BankAccount.builder()
                .balance(Amount.ZERO)
                .accountNumber(AccountNumber.of("111-2222"))
                .build();

        assertEquals(Amount.ZERO, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());

        when(bankAccountRepository.get(bankAccount.getAccountNumber()))
                .thenReturn(Optional.of(bankAccount));

        accountServiceImp.credit(bankAccount.getAccountNumber().value(), 56.0);
        assertEquals(Amount.of(56.0), bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());

        accountServiceImp.debit(bankAccount.getAccountNumber().value(), 10.0);
        assertEquals(Amount.of(46.0), bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());
    }

    @Test
    void givenNotExistAccountNumber_whenCredit_thenThrowError() {
        final String accountNumber = "111-2222";

        when(bankAccountRepository.get(AccountNumber.of(accountNumber)))
                .thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class, () -> accountServiceImp.credit(accountNumber, 56.0));
    }

    @Test
    void givenNotExistAccountNumber_whenDebit_thenThrowError() {
        final String accountNumber = "111-2222";

        when(bankAccountRepository.get(AccountNumber.of(accountNumber)))
                .thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class, () -> accountServiceImp.debit(accountNumber, 56.0));
    }
}
