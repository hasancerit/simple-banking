package com.eteration.simplebanking.domain.model;

import com.eteration.simplebanking.util.RegexUtil;

public record AccountNumber(String value) {
    private static final String VALID_REGEX_FOR_VALUE = "^[0-9]{3}-[0-9]{4}$";

    public static AccountNumber of(String value) {
        if (!RegexUtil.matches(value, VALID_REGEX_FOR_VALUE)) {
            throw new RuntimeException("Account Number format is invalid.");
        }
        return new AccountNumber(value);
    }
}
