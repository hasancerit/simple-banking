package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.controller.dto.req.TransactionRequest;
import com.eteration.simplebanking.controller.dto.res.TransactionResultResponse;
import com.eteration.simplebanking.domain.exception.InsufficientBalanceException;
import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.service.AccountService;
import com.eteration.simplebanking.service.exception.BankAccountNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.eteration.simplebanking.util.ObjectMapperUtil.fromJsonString;
import static com.eteration.simplebanking.util.ObjectMapperUtil.toJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(AccountController.class)
class AccountDebitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void givenExistedAccountNumber_whenDebitAccountApiCall_thenReturnApprovalCode() throws Exception {
        final Double transactionAmount = 10.0;
        final BankAccount bankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("111-2222"))
                .balance(Amount.of(20.0))
                .owner("Hasan")
                .build();

        final String approvalCodeFromService = UUID.randomUUID().toString();
        when(accountService.debit(bankAccount.getAccountNumber().value(), transactionAmount))
                .thenReturn(approvalCodeFromService);

        TransactionRequest transactionRequest = new TransactionRequest(transactionAmount);

        MockHttpServletResponse response = mockMvc.perform(
                post("/account/v1/debit/" + bankAccount.getAccountNumber().value())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(transactionRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response.getContentAsString());

        final TransactionResultResponse transactionResultResponse = fromJsonString(response.getContentAsString(), TransactionResultResponse.class);
        assertEquals(approvalCodeFromService, transactionResultResponse.approvalCode());
        assertEquals(HttpStatus.OK, transactionResultResponse.status());
    }

    @Test
    void givenNotExistAccountNumber_whenDebitAccountApiCall_thenReturn404() throws Exception {
        final Double transactionAmount = 10.0;
        String notExistAccountNumber = "111-2222";
        when(accountService.debit(notExistAccountNumber,transactionAmount )).thenThrow(new BankAccountNotFoundException(notExistAccountNumber));

        TransactionRequest transactionRequest = new TransactionRequest(transactionAmount);

        MockHttpServletResponse response = mockMvc.perform(
                post("/account/v1/debit/" + notExistAccountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(transactionRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenInsufficientBalanceAccountNumber_whenDebitAccountApiCall_thenReturn406() throws Exception {
        final Double transactionAmount = 100.0;
        final BankAccount bankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("111-2222"))
                .balance(Amount.of(20.0))
                .owner("Hasan")
                .build();
        when(accountService.debit(bankAccount.getAccountNumber().value(),transactionAmount)).thenThrow(new InsufficientBalanceException());

        TransactionRequest transactionRequest = new TransactionRequest(transactionAmount);

        MockHttpServletResponse response = mockMvc.perform(
                post("/account/v1/debit/" + bankAccount.getAccountNumber().value())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(transactionRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
    }
}
