/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.service;

import com.bank.account.AccountOpeningDocument;
import com.bank.account.BaseAccount;
import com.bank.exception.InvalidTransactionException;

import java.math.BigDecimal;

interface AccountFactory {
    BaseAccount createAccount(AccountOpeningDocument accountOpeningDocument, BigDecimal openingBalance, long transactionId) throws InvalidTransactionException;
}
