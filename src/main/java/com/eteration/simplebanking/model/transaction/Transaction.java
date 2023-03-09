package com.eteration.simplebanking.model.transaction;

import com.eteration.simplebanking.model.Amount;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public abstract class Transaction {
    private final Amount amount;
    private final Date date;
}
