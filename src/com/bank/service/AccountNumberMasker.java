/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.service;

/**
 * This class is a utilituy class to mask Account number in Format 12XXXXX4321
 * leaving only first 2 digits and last 4 digits visible
 */
public class AccountNumberMasker {
    public static String maskAccountNumber(long accNumber) {

        String number = String.valueOf(accNumber);
        StringBuilder maskedNumber = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            if (i < 2 || i > number.length() - 5) {
                maskedNumber.append(c);
            } else {
                maskedNumber.append("X");
            }
        }
        return maskedNumber.toString();
    }
}
