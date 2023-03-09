package com.eteration.simplebanking.model.transaction;

import com.eteration.simplebanking.model.Amount;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Getter
public abstract class Transaction {
    private final Amount amount;
    private final Date date;
}
