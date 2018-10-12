/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.account;

import com.bank.exception.InvalidTransactionException;
import com.bank.model.Address;
import com.bank.model.Name;

import java.math.BigDecimal;

public interface BaseAccount {
    BigDecimal ZERO_BALANCE = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);

    void activateAccount();

    void deActivateAccount();

    long getAccountNumber();

    String getMaskedAccountNumber();

    String getBalanceAsString();

    BigDecimal getBalance();

    boolean isAccountActive();

    Name getName();

    Object getLock();

    Address getAddress();

    void withdraw(BigDecimal withdrawalAmount, long transactionId, String desc) throws InvalidTransactionException;

    void deposit(BigDecimal DepositAmount, long transactionId, String desc) throws InvalidTransactionException;

    void printLast10Transactions();
}
