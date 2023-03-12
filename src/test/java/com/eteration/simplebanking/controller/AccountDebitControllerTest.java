package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.controller.dto.request.TransactionRequest;
import com.eteration.simplebanking.controller.dto.response.TransactionResultResponse;
import com.eteration.simplebanking.domain.exception.InsufficientBalanceException;
import com.eteration.simplebanking.service.AccountService;
import com.eteration.simplebanking.service.exception.BankAccountNotFoundException;
import com.eteration.simplebanking.util.BankAccountTestDataBuilder;
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

    private final String accountNumber = BankAccountTestDataBuilder.generateValidAccountNumber();
    private final Double transactionAmount = 10.0;

    @Test
    void givenServiceReturnApprovalCode_whenDebitApiCall_thenReturnApprovalCode() throws Exception {
        final String approvalCodeFromService = UUID.randomUUID().toString();
        when(accountService.debit(accountNumber, transactionAmount))
                .thenReturn(approvalCodeFromService);

        final MockHttpServletResponse response = sendRequestDebit();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response.getContentAsString());

        final TransactionResultResponse transactionResultResponse =
                fromJsonString(response.getContentAsString(), TransactionResultResponse.class);
        assertEquals(approvalCodeFromService, transactionResultResponse.approvalCode());
        assertEquals(HttpStatus.OK, transactionResultResponse.status());
    }

    @Test
    void givenServiceThrowBankAccountNotFoundException_whenDebitApiCall_thenReturn404() throws Exception {
        when(accountService.debit(accountNumber, transactionAmount))
                .thenThrow(new BankAccountNotFoundException(accountNumber));

        final MockHttpServletResponse response = sendRequestDebit();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenServiceThrowInsufficientBalanceAccountNumber_whenDebitApiCall_thenReturn406() throws Exception {
        when(accountService.debit(accountNumber, transactionAmount)).thenThrow(new InsufficientBalanceException());

        final MockHttpServletResponse response = sendRequestDebit();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
    }

    private MockHttpServletResponse sendRequestDebit() throws Exception {
        final TransactionRequest transactionRequest = new TransactionRequest(transactionAmount);

        return mockMvc.perform(
                post("/account/v1/%s/debit".formatted(accountNumber))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(transactionRequest))
        ).andReturn().getResponse();
    }
}
