/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.service;

import com.bank.account.BaseAccount;
import com.bank.account.Validator;
import com.bank.exception.InvalidTransactionException;

import java.math.BigDecimal;
import java.util.Objects;

public class FundsTransferServiceImpl implements FundsTransferService {

    @Override
    public void transferTo(BaseAccount fromAccount, BaseAccount toAccount, BigDecimal transferAmount, long transactionId) throws InvalidTransactionException {
        Validator.verifyAmount(transferAmount);
        Validator.validateAccountObject(fromAccount);
        Validator.validateAccountObject(toAccount);
        loggerInfo("Transferring : " + transferAmount.toPlainString() + ", to Account : " + toAccount.toString());
        Object firstLock = fromAccount.getAccountNumber() < toAccount.getAccountNumber() ? fromAccount.getLock() : toAccount.getLock();
        Object lastLock = fromAccount.getAccountNumber() < toAccount.getAccountNumber() ? toAccount.getLock() : fromAccount.getLock();
        String depositNarration = "INTERNAL_TRANSFER-" + transactionId + "-" + fromAccount.getName().getFullName() + "-" + fromAccount.getMaskedAccountNumber();
        String withdrawNarration = "INTERNAL_TRANSFER-" + transactionId + "-" + toAccount.getName().getFullName() + "-" + toAccount.getMaskedAccountNumber();
        ;
        synchronized (firstLock) {
            synchronized (lastLock) {
                fromAccount.withdraw(transferAmount, transactionId, withdrawNarration);
                toAccount.deposit(transferAmount, transactionId, depositNarration);
            }
        }
    }

    private void loggerInfo(String message) {
        System.out.println("LOGGER : INFO | " + this.getClass().getName() + " | THREAD " + Thread.currentThread().getName() + " | " + System.currentTimeMillis() + " | " + message);
    }
}
