package com.eteration.simplebanking.util;

import java.util.regex.Pattern;

public class RegexUtil {
    public static boolean matches(String value, String regex) {
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }
}
