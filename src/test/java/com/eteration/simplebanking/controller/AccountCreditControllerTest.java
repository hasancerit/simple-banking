package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.controller.dto.req.TransactionRequest;
import com.eteration.simplebanking.controller.dto.res.TransactionResultResponse;
import com.eteration.simplebanking.domain.model.account.BankAccount;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(AccountController.class)
class AccountCreditControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void givenExistedAccountNumber_whenCreditAccountApiCall_thenReturnApprovalCode() throws Exception {
        final Double transactionAmount = 10.0;
        BankAccount bankAccount = BankAccountTestDataBuilder.emptyTransactionBankAccount();

        final String approvalCodeFromService = UUID.randomUUID().toString();
        when(accountService.credit(bankAccount.getAccountNumber().value(), transactionAmount))
                .thenReturn(approvalCodeFromService);

        TransactionRequest transactionRequest = new TransactionRequest(transactionAmount);

        MockHttpServletResponse response = mockMvc.perform(
                post("/account/v1/"+ bankAccount.getAccountNumber().value() + "/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(transactionRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response.getContentAsString());
        System.out.println(response.getContentAsString());

        final TransactionResultResponse transactionResultResponse = fromJsonString(response.getContentAsString(), TransactionResultResponse.class);
        assertEquals(approvalCodeFromService, transactionResultResponse.approvalCode());
        assertEquals(HttpStatus.OK, transactionResultResponse.status());
    }

    @Test
    void givenNotExistAccountNumber_whenCreditAccountApiCall_thenReturn404() throws Exception {
        final Double transactionAmount = 10.0;
        String notExistAccountNumber = "111-2222";
        when(accountService.credit(notExistAccountNumber,transactionAmount )).thenThrow(new BankAccountNotFoundException(notExistAccountNumber));

        TransactionRequest transactionRequest = new TransactionRequest(transactionAmount);

        MockHttpServletResponse response = mockMvc.perform(
                post("/account/v1/"+ notExistAccountNumber + "/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(transactionRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
