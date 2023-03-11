package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.controller.dto.req.TransactionRequest;
import com.eteration.simplebanking.controller.dto.res.BankAccountResponse;
import com.eteration.simplebanking.controller.dto.res.TransactionResultResponse;
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

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionResultResponse> credit(@PathVariable String accountNumber, @RequestBody TransactionRequest transactionRequest) {
        final String approvalCode = accountService.credit(accountNumber, transactionRequest.amount());
        final TransactionResultResponse transactionResultResponse = new TransactionResultResponse(approvalCode, HttpStatus.OK);
        return ResponseEntity.ok(transactionResultResponse);
    }

    @ExceptionHandler(BankAccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleBankAccountNotFoundException() {
    }
}