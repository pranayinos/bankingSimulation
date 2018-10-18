/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.common;


public interface BankFacade {

    long createAccount(String name, String address, String initialDeposit);

    void deleteAccount(long accountNumber);

    void transfer(long fromAccountNumber, long toAccountNumber, String transferAmount);

    void deposit(long accountNumber, String transferAmount);

    void withdraw(long accountNumber, String transferAmount);

    void getBalance(long accountNumber);

    void printLast10Transactions(long accountNumber);

    void printAllTransactions(long accountNumber);

}