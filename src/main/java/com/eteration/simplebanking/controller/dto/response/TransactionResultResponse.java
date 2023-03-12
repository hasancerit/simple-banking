package com.eteration.simplebanking.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.http.HttpStatus;

public record TransactionResultResponse(String approvalCode, HttpStatus status) {
    @JsonCreator
    public TransactionResultResponse {
    }
}
