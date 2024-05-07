package com.pda.practice.utils;

import java.util.regex.Pattern;

public class ValidationUtil {

    /**
     * Verify if number is positive integer or zero
     * @param number target number
     * @return true(>=0), false(negative)
     */
    public static boolean isValidNumber(int number) {
        return number <= 0;
    }

    /**
     * Verify if given string is made by alphabet
     * @param str target string
     * @return true / false
     */
    public static boolean isAlpha(String str) {
        return !Pattern.matches("[a-zA-Z]*$", str);
    }

    /**
     * Check given string is empty or not
     * @param str target string
     * @return true(empty or null), false
     */
    public static boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Verify given string does not exceed max length
     * @param str target string
     * @param maxLen max length
     * @return given string does not exceed max length or not
     */
    public static boolean isStringInLength(String str, int maxLen) {
        return str.length() <= maxLen;
    }
}
