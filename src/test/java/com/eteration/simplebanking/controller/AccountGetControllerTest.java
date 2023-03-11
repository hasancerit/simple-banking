package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.controller.dto.res.BankAccountResponse;
import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
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
    void givenExistedAccountNumber_whenGetAccountApiCall_thenReturnAccountResponse() throws Exception {
        DepositTransaction depositTransaction1 = DepositTransaction.of(Amount.of(10.0));

        final BankAccount bankAccount = BankAccount.builder()
                .accountNumber(AccountNumber.of("111-2222"))
                .transactions(List.of(depositTransaction1))
                .balance(Amount.of(10.0))
                .owner("Hasan")
                .build();

        //TODO: Dont these
        depositTransaction1.setBankAccount(bankAccount);

        when(accountService.get(bankAccount.getAccountNumber().value()))
                .thenReturn(bankAccount);

        MockHttpServletResponse response = mockMvc.perform(
                get("/account/v1/" + bankAccount.getAccountNumber().value()).accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response.getContentAsString());

        final BankAccountResponse bankAccountResponseFromApi = fromJsonString(response.getContentAsString(), BankAccountResponse.class);
        assertEquals(bankAccount.getAccountNumber().value(), bankAccountResponseFromApi.accountNumber());
        assertEquals(bankAccount.getBalance().amount(), bankAccountResponseFromApi.balance());
        assertEquals(bankAccount.getOwner(), bankAccountResponseFromApi.owner());
        assertEquals(bankAccount.getCreatedDate(), bankAccountResponseFromApi.createdDate());
        assertEquals(bankAccount.getTransactions().size(), bankAccountResponseFromApi.transactions().size());
        assertEquals(bankAccount.getTransactions().get(0).getAmount().amount(), bankAccountResponseFromApi.transactions().get(0).amount());
    }

    @Test
    void givenNotExistAccountNumber_whenGetAccountApiCall_thenReturn404() throws Exception {
        String notExistAccountNumber = "111-2222";
        when(accountService.get(notExistAccountNumber)).thenThrow(new BankAccountNotFoundException(notExistAccountNumber));

        MockHttpServletResponse response = mockMvc.perform(
                get("/account/v1/" + notExistAccountNumber).accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}