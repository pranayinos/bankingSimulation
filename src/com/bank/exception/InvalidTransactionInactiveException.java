/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.exception;

public final class InvalidTransactionInactiveException extends InvalidTransactionException {
    public InvalidTransactionInactiveException(String message) {
        super(message);
    }
}
