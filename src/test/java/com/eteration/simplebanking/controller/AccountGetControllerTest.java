package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.controller.dto.response.BankAccountResponse;
import com.eteration.simplebanking.controller.dto.response.TransactionResponse;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import com.eteration.simplebanking.service.AccountService;
import com.eteration.simplebanking.service.exception.BankAccountNotFoundException;
import com.eteration.simplebanking.util.BankAccountTestDataBuilder;
import com.eteration.simplebanking.util.CopyObjectUtil;
import com.eteration.simplebanking.util.TransactionTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.eteration.simplebanking.util.ObjectMapperUtil.fromJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AccountController.class)
class AccountGetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void givenServiceReturnAnBankAccount_whenGetApiCall_thenDoesntChangeItAndReturnAsAccountResponse() throws Exception {
        final BankAccount bankAccount = BankAccountTestDataBuilder.bankAccountWithTransaction(
                Amount.of(10.0),
                TransactionTestDataBuilder.approvedAndPersistedDepositTransaction(Amount.of(10.0))
        );

        when(accountService.get(bankAccount.getAccountNumber().value()))
                .thenReturn(CopyObjectUtil.deepCopy(bankAccount, BankAccount.class));

        final MockHttpServletResponse response = sendRequestGetAccount(bankAccount.getAccountNumber().value());

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response.getContentAsString());

        final BankAccountResponse accountResponse =
                fromJsonString(response.getContentAsString(), BankAccountResponse.class);
        assertEquals(bankAccount.getAccountNumber().value(), accountResponse.accountNumber());
        assertEquals(bankAccount.getOwner(), accountResponse.owner());
        assertEquals(bankAccount.getBalance().value(), accountResponse.balance());
        assertEquals(bankAccount.getCreatedDate(), accountResponse.createdDate());

        final List<TransactionResponse> transactionResponsesOfAccountResponse = accountResponse.transactions();
        assertEquals(bankAccount.getTransactions().size(), transactionResponsesOfAccountResponse.size());
        this.assertTransactionResponseOfAccountResponse(
                bankAccount.getTransactions().get(0),
                transactionResponsesOfAccountResponse.get(0)
        );
    }

    private void assertTransactionResponseOfAccountResponse(Transaction expectedTransaction,
                                                            TransactionResponse actualTransactionResponse) {
        assertEquals(expectedTransaction.getAmount().value(), actualTransactionResponse.amount());
        assertEquals(expectedTransaction.getType(), actualTransactionResponse.type());
        assertNotNull(expectedTransaction.getApprovalCode(), actualTransactionResponse.approvalCode());
        assertNotNull(actualTransactionResponse.createdDate());
    }

    @Test
    void givenServiceThrowBankAccountNotFoundException_whenGetApiCall_thenReturn404() throws Exception {
        final String notExistAccountNumber = BankAccountTestDataBuilder.generateValidAccountNumber();

        when(accountService.get(notExistAccountNumber))
                .thenThrow(new BankAccountNotFoundException(notExistAccountNumber));

        final MockHttpServletResponse response = sendRequestGetAccount(notExistAccountNumber);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


    private MockHttpServletResponse sendRequestGetAccount(String accountNumber) throws Exception {
        return mockMvc.perform(
                get("/account/v1/%s".formatted(accountNumber))
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
    }
}
