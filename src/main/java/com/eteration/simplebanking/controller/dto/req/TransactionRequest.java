package com.eteration.simplebanking.controller.dto.req;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(@NotNull Double amount) {
    @JsonCreator
    public TransactionRequest {
    }
}
