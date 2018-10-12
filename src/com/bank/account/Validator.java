/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.account;

import com.bank.exception.InvalidTransactionException;

import java.math.BigDecimal;
import java.util.Objects;

public class Validator {
    public static void verifyAmount(BigDecimal transferAmount) throws InvalidTransactionException {
        if (Objects.nonNull(transferAmount) && transferAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidTransactionException("Transfer amount cannot be null or negative : " + transferAmount.toPlainString());
        }
    }

    public static void validateAccountObject(BaseAccount account) throws InvalidTransactionException {
        if (Objects.isNull(account)){
            throw new InvalidTransactionException("Invalid account object");
        }
    }

    public static void validateDocuments(AccountOpeningDocument accountOpeningDocument) throws InvalidTransactionException {
        if (Objects.isNull(accountOpeningDocument)){
            //TODO More Document validation - verification Code here
            throw new InvalidTransactionException("Invalid account Opening Document");
        }
    }
}
