/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.utilities;

import com.bank.exception.UserException;

import java.math.BigDecimal;

public class Validator {
    public static BigDecimal verifyAmount(String transferAmount) throws UserException {
        BigDecimal transferAmountConverted;
        try {
            transferAmountConverted = new BigDecimal(transferAmount);
        } catch (NumberFormatException e) {
            throw new UserException("Invalid amount String, please provide a valid number");
        }
        if (transferAmountConverted.compareTo(BigDecimal.ZERO) < 0) {
            throw new UserException("Transfer amount cannot be null or negative : " + transferAmount);
        }
        return transferAmountConverted;
    }
}
