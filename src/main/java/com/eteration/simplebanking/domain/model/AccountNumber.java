package com.eteration.simplebanking.domain.model;

import com.eteration.simplebanking.domain.exception.InvalidAccountNumberException;
import com.eteration.simplebanking.util.RegexUtil;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record AccountNumber(String value) implements Serializable {
    public static final String VALID_REGEX = "^[0-9]{3}-[0-9]{4}$";

    public AccountNumber {
        if (!RegexUtil.matches(value, VALID_REGEX)) {
            throw new InvalidAccountNumberException(value);
        }
    }

    public static AccountNumber of(String value) {
        return new AccountNumber(value);
    }
}
