/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.service;

import com.bank.account.BaseAccount;
import com.bank.exception.InvalidTransactionException;

import java.math.BigDecimal;

interface FundsTransferService {
    void transferTo(BaseAccount fromAccount, BaseAccount toAccount, BigDecimal transferAmount, long transactionId) throws InvalidTransactionException;
}
