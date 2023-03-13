package com.eteration.simplebanking.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BillPaymentTransactionRequest(@Min(1) @NotNull Double amount,
                                            @NotBlank String billNumber,
                                            @NotBlank String payee) {
    @JsonCreator
    public BillPaymentTransactionRequest {
    }
}
