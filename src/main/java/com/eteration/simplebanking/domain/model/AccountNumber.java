package com.eteration.simplebanking.domain.model;

import com.eteration.simplebanking.domain.exception.InvalidAccountNumberException;
import com.eteration.simplebanking.util.RegexUtil;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record AccountNumber(String value) implements Serializable {
    private static final String VALID_REGEX_FOR_VALUE = "^[0-9]{3}-[0-9]{4}$";

    public static AccountNumber of(String value) {
        if (!RegexUtil.matches(value, VALID_REGEX_FOR_VALUE)) {
            throw new InvalidAccountNumberException(value);
        }
        return new AccountNumber(value);
    }
}
