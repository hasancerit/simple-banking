package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.controller.dto.res.BankAccountResponse;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.service.AccountService;
import com.eteration.simplebanking.service.exception.BankAccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountResponse> get(@PathVariable String accountNumber) {
        final BankAccount bankAccount = accountService.get(accountNumber);
        final BankAccountResponse bankAccountResponse = BankAccountResponse.from(bankAccount);
        return ResponseEntity.ok(bankAccountResponse);
    }

    @ExceptionHandler(BankAccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleBankAccountNotFoundException() {
    }
}