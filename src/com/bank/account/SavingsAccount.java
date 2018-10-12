/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.account;

import com.bank.exception.InvalidTransactionException;
import com.bank.exception.InvalidTransactionInactiveException;
import com.bank.model.Address;
import com.bank.model.Name;
import com.bank.service.AccountNumberMasker;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Saving account will have minimum balance/withdrawal limits and interest calculation strategies different from other account types
 */
public class SavingsAccount implements BaseAccount {
    private final Object lock;
    private final long accountNumber;
    private final Name accountHoldersName;
    private final Address accountHoldersAddress;
    private final AtomicBoolean status = new AtomicBoolean(false);
    private BigDecimal balance;
    private final AccountLedger accountLedger;

    public SavingsAccount(long accountNumber, Name accountHoldersName, Address accountHoldersAddress, BigDecimal balance, AccountLedger accountLedger) {
        this.accountNumber = accountNumber;
        this.accountHoldersName = accountHoldersName;
        this.accountHoldersAddress = accountHoldersAddress;
        this.balance = balance;
        this.accountLedger = accountLedger;
        this.lock = new Object();
    }

    @Override
    public void activateAccount() {
        synchronized (this.lock) {
            this.status.set(true);
        }
    }

    @Override
    public void deActivateAccount() {
        synchronized (this.lock) {
            this.status.set(false);
        }
    }

    @Override
    public long getAccountNumber() {
        return this.accountNumber;
    }

    @Override
    public String getMaskedAccountNumber() {
        return AccountNumberMasker.maskAccountNumber(getAccountNumber());
    }

    @Override
    public String getBalanceAsString() {
        return this.balance.setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString();
    }

    @Override
    public BigDecimal getBalance() {
        return this.balance;
    }

    @Override
    public Name getName() {
        return this.accountHoldersName;
    }

    @Override
    public Object getLock() {
        return this.lock;
    }

    @Override
    public Address getAddress() {
        return this.accountHoldersAddress;
    }

    @Override
    public void withdraw(BigDecimal withdrawalAmount, long transactionId, String desc) throws InvalidTransactionException {
        validateAmount(withdrawalAmount);
        loggerInfo(" Withdrawing :" + withdrawalAmount + " from account : " + this.toString());
        synchronized (this.lock) {
            this.checkAccountStatus();
            if (balance.compareTo(withdrawalAmount) >= 0) {
                this.balance = this.balance.subtract(withdrawalAmount);
                LedgerEntry entry = new LedgerEntry(EntryType.CREDIT, transactionId, desc, withdrawalAmount , BigDecimal.ZERO, this.balance);
                this.accountLedger.addTransaction(entry);
            } else {
                throw new InvalidTransactionException("Insufficient funds, cannot process txn : " + withdrawalAmount.toPlainString());
            }
        }
    }

    @Override
    public void deposit(BigDecimal depositAmount, long transactionId, String desc) throws InvalidTransactionException {
        validateAmount(depositAmount);
        loggerInfo(" Depositing :" + depositAmount + " to account : " + this.toString());
        synchronized (this.lock) {
            this.checkAccountStatus();
            this.balance = this.balance.add(depositAmount);
            LedgerEntry entry = new LedgerEntry(EntryType.DEBIT, transactionId, desc, BigDecimal.ZERO, depositAmount, this.balance);
            this.accountLedger.addTransaction(entry);
        }
    }

    @Override
    public void printLast10Transactions() {
        System.out.println("\nSAVINGS ACCOUNT--------------------------------------------------------------#"+this.getMaskedAccountNumber());
        accountLedger.printLast10Transactions();
    }

    @Override
    public boolean isAccountActive() {
        return status.get();
    }

    @Override
    public String toString() {
        return "SavingsAccount{" + "accountNumber=" + accountNumber + ", accountHoldersName=" + accountHoldersName +
                ", balance=" + getBalanceAsString() + ", status=" + status + '}';
    }

    private void loggerInfo(String message) {
        System.out.println("LOGGER : INFO | " + this.getClass().getName() + " | THREAD "
                + Thread.currentThread().getName() + " | " + System.currentTimeMillis() + " | " + message);
    }

    private void checkAccountStatus() throws InvalidTransactionInactiveException {
        if (!this.isAccountActive()) {
            throw new InvalidTransactionInactiveException("Account not active");
        }
    }

    private void validateAmount(BigDecimal amount) throws InvalidTransactionException {
        if (Objects.nonNull(amount) && amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidTransactionException("tranmsaction amount cannot be negative: " + amount.toPlainString());
        }
    }
}
