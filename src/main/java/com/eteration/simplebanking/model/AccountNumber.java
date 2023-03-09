package com.eteration.simplebanking.model;

import java.util.regex.Pattern;

public record AccountNumber(String value) {
    private static final Pattern VALID_REGEX_FOR_VALUE = Pattern.compile("^[0-9]{3}-[0-9]{4}$");

    public static AccountNumber of(String value) {
        if(!VALID_REGEX_FOR_VALUE.matcher(value).matches()) {
            throw new RuntimeException("Account Number format is invalid.");
        }
        return new AccountNumber(value);
    }
}
