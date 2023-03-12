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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountCreditServiceTest {
    @InjectMocks
    private AccountServiceImp accountServiceImp;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    void givenRepositoryReturnAnAccount_whenCredit_thenFlowToBankAccountAndUpdateAccountAndReturnApprovalCode() {
        final BankAccount bankAccount = mock(BankAccount.class);

        final AccountNumber accountNumber = AccountNumber.of(BankAccountTestDataBuilder.generateValidAccountNumber());
        when(bankAccountRepository.get(accountNumber)).thenReturn(Optional.of(bankAccount));

        final String approvalCodeFromBankAccount = UUID.randomUUID().toString();
        when(bankAccount.post(any())).thenReturn(approvalCodeFromBankAccount);

        final String approvalCodeFromService = accountServiceImp.credit(accountNumber.value(), 56.0);

        verify(bankAccountRepository, times(1)).get(accountNumber);
        verify(bankAccount, times(1)).post(any());
        verify(bankAccountRepository, times(1)).update(bankAccount);
        assertEquals(approvalCodeFromBankAccount, approvalCodeFromService);
        verifyNoMoreInteractions(bankAccount);
        verifyNoMoreInteractions(bankAccountRepository);
    }

    @Test
    void givenRepositoryReturnNull_whenCredit_thenThrowBankAccountNotFoundException() {
        final AccountNumber notExistAccountNumber =
                AccountNumber.of(BankAccountTestDataBuilder.generateValidAccountNumber());

        when(bankAccountRepository.get(notExistAccountNumber))
                .thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class,
                () -> accountServiceImp.credit(notExistAccountNumber.value(), 56.0));

        verify(bankAccountRepository, times(1)).get(notExistAccountNumber);
        verifyNoMoreInteractions(bankAccountRepository);
    }
}