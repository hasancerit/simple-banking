package com.eteration.simplebanking.controller.dto.req;

import com.fasterxml.jackson.annotation.JsonCreator;

public record TransactionRequest(Double amount) {
    @JsonCreator
    public TransactionRequest {
    }
}
