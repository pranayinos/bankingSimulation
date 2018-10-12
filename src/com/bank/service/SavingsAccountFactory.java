/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.service;

import com.bank.account.*;
import com.bank.exception.InvalidTransactionException;

import java.math.BigDecimal;
import java.util.Objects;

enum SavingsAccountFactory implements AccountFactory {
    INSTANCE;
    private final AccountNumberGenerator accountNumberGenerator = new SimpleAccountNumberGenerator();

    @Override
    public BaseAccount createAccount(AccountOpeningDocument accountOpeningDocument, BigDecimal openingBalance, long transactionId) throws InvalidTransactionException {
        Validator.validateDocuments(accountOpeningDocument);
        AccountLedger accountLedger = new AccountLedger();
        accountLedger.addTransaction(new LedgerEntry(EntryType.DEBIT, transactionId, "OPENING-INITIAL-DEPOSIT", new BigDecimal(0), openingBalance, openingBalance));
        BaseAccount createdAccount = new SavingsAccount(accountNumberGenerator.generate(), accountOpeningDocument.getName(),
                accountOpeningDocument.getAddress(), openingBalance, accountLedger);
        loggerInfo(" Account Created Successfully : " + createdAccount.toString());
        return createdAccount;
    }

    private void loggerInfo(String message) {
        System.out.println("LOGGER : INFO | " + this.getClass().getName() + " | THREAD " + Thread.currentThread().getName() + " | " + System.currentTimeMillis() + " | " + message);
    }
}
