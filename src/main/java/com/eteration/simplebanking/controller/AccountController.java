package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.controller.dto.request.BillPaymentTransactionRequest;
import com.eteration.simplebanking.controller.dto.request.TransactionRequest;
import com.eteration.simplebanking.controller.dto.response.BankAccountResponse;
import com.eteration.simplebanking.controller.dto.response.TransactionResultResponse;
import com.eteration.simplebanking.domain.exception.InsufficientBalanceException;
import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.service.AccountService;
import com.eteration.simplebanking.service.exception.BankAccountNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
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
    public ResponseEntity<BankAccountResponse> get(
            @PathVariable @Pattern(regexp = AccountNumber.VALID_REGEX) String accountNumber
    ) {
        final BankAccount bankAccount = accountService.get(accountNumber);
        final BankAccountResponse bankAccountResponse = BankAccountResponse.from(bankAccount);
        return ResponseEntity.ok(bankAccountResponse);
    }

    @PostMapping("/{accountNumber}/credit")
    public ResponseEntity<TransactionResultResponse> credit(
            @PathVariable @Pattern(regexp = AccountNumber.VALID_REGEX) String accountNumber,
            @Valid @RequestBody TransactionRequest transactionRequest
    ) {
        final String approvalCode = accountService.credit(accountNumber, transactionRequest.amount());
        final TransactionResultResponse transactionResultResponse = new TransactionResultResponse(approvalCode, HttpStatus.OK);
        return ResponseEntity.ok(transactionResultResponse);
    }

    @PostMapping("/{accountNumber}/debit")
    public ResponseEntity<TransactionResultResponse> debit(
            @PathVariable @Pattern(regexp = AccountNumber.VALID_REGEX) String accountNumber,
            @Valid @RequestBody TransactionRequest transactionRequest
    ) {
        final String approvalCode = accountService.debit(accountNumber, transactionRequest.amount());
        final TransactionResultResponse transactionResultResponse = new TransactionResultResponse(approvalCode, HttpStatus.OK);
        return ResponseEntity.ok(transactionResultResponse);
    }

    @PostMapping("/{accountNumber}/bill-payment")
    public ResponseEntity<TransactionResultResponse> billPayment(
            @PathVariable @Pattern(regexp = AccountNumber.VALID_REGEX) String accountNumber,
            @Valid @RequestBody BillPaymentTransactionRequest billPaymentTransactionRequest
    ) {
        final String approvalCode = accountService.billPayment(
                accountNumber,
                billPaymentTransactionRequest.amount(),
                billPaymentTransactionRequest.billNumber(),
                billPaymentTransactionRequest.payee()

        );
        final TransactionResultResponse transactionResultResponse = new TransactionResultResponse(approvalCode, HttpStatus.OK);
        return ResponseEntity.ok(transactionResultResponse);
    }

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<String> handleBankAccountNotFoundException() {
        return new ResponseEntity<>("Not Found Account With Account Number", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceExceptionException() {
        return new ResponseEntity<>("Insufficient Balance", HttpStatus.BAD_REQUEST);
    }
}