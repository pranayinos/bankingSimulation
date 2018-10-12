/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.exception;

public class BankingException extends Exception {
    BankingException() {
    }

    BankingException(String message) {
        super(message);
    }

    BankingException(String message, Throwable cause) {
        super(message, cause);
    }

    BankingException(Throwable cause) {
        super(cause);
    }

    BankingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
