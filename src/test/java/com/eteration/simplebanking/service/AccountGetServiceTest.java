package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.repository.BankAccountRepository;
import com.eteration.simplebanking.service.exception.BankAccountNotFoundException;
import com.eteration.simplebanking.service.impl.AccountServiceImp;
import com.eteration.simplebanking.util.builder.BankAccountTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AccountGetServiceTest {
    @InjectMocks
    private AccountServiceImp accountServiceImp;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    void givenRepositoryReturnAnAccount_whenGet_thenDirectlyReturnAccount() {
        final BankAccount bankAccount = mock(BankAccount.class);

        final AccountNumber accountNumber = AccountNumber.of(BankAccountTestDataBuilder.generateValidAccountNumber());
        when(bankAccountRepository.get(accountNumber)).thenReturn(Optional.of(bankAccount));

        final BankAccount bankAccountFromService = accountServiceImp.get(accountNumber.value());

        assertEquals(bankAccount, bankAccountFromService);
        verify(bankAccountRepository, times(1)).get(accountNumber);
        verifyNoMoreInteractions(bankAccount);
        verifyNoMoreInteractions(bankAccountRepository);
    }

    @Test
    void givenRepositoryReturnNul_thenThrowBankAccountNotFoundException() {
        final AccountNumber notExistAccountNumber =
                AccountNumber.of(BankAccountTestDataBuilder.generateValidAccountNumber());

        when(bankAccountRepository.get(notExistAccountNumber))
                .thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class,
                () -> accountServiceImp.get(notExistAccountNumber.value()));

        verify(bankAccountRepository, times(1)).get(notExistAccountNumber);
        verifyNoMoreInteractions(bankAccountRepository);
    }
}