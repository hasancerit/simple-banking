package com.eteration.simplebanking.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;

public record BillPaymentTransactionRequest(@NotNull Double amount,
                                            @NotNull String billNumber,
                                            @NotNull String payee) {
    @JsonCreator
    public BillPaymentTransactionRequest {
    }
}
