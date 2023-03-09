package com.eteration.simplebanking.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexUtil {
    public static boolean matches(String value, String regex) {
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }
}
