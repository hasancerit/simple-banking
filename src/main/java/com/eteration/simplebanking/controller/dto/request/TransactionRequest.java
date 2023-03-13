package com.eteration.simplebanking.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(@Min(1) @NotNull Double amount) {
    @JsonCreator
    public TransactionRequest {
    }
}
