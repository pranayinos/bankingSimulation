/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.model;

import java.math.BigDecimal;
import java.util.LinkedList;

public interface BaseAccount extends Comparable<BaseAccount> {

    Long getAccountNumber();

    BigDecimal getBalance();

    void addToBalance(BigDecimal amount);

    void subtractFromBalance(BigDecimal amount);

    String getName();

    String getAddress();

    LinkedList<TransactionDetail> getTransactionList();
}
