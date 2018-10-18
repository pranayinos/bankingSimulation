/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.common;

import com.bank.exception.Action;
import com.bank.exception.UserException;
import com.bank.persistance.AccountRepository;
import com.bank.persistance.SimpleAccountRepository;
import com.bank.service.*;

import java.math.BigDecimal;

public class BankFacadeImpl implements BankFacade {

    private final TellerService tellerService;
    private final AccountService accountService;
    private final TransactionPrintingService transactionPrintingService;

    public BankFacadeImpl() {
        AccountRepository accountRepository = new SimpleAccountRepository();
        this.tellerService = new TellerServiceImpl(accountRepository);
        this.accountService = new AccountServiceImpl(accountRepository, this.tellerService);
        this.transactionPrintingService = new TransactionPrintingServiceImpl(accountRepository);
    }

    @Override
    public long createAccount(String name, String address, String initialDeposit) {
        return accountService.createAccount(name, address, new BigDecimal(initialDeposit));
    }

    @Override
    public void deleteAccount(long accountNumber) {
        try {
            accountService.deleteAccount(accountNumber);
            printUserMessage("Account Closed");
        } catch (UserException e) {
            printUserMessage(e.getMessage());
        }
    }

    @Override
    public void transfer(long fromAccountNumber, long toAccountNumber, String transferAmount) {
        handleExceptions(() -> tellerService.transfer(fromAccountNumber, toAccountNumber, transferAmount), "Transfer Successful!");
    }

    @Override
    public void deposit(long accountNumber, String transferAmount) {
        handleExceptions(() -> tellerService.deposit(accountNumber, transferAmount), "Deposit Successful!");
    }

    @Override
    public void withdraw(long accountNumber, String transferAmount) {
        handleExceptions(() -> tellerService.withdraw(accountNumber, transferAmount), "Withdrawal Successful!");
    }

    @Override
    public void getBalance(long accountNumber) {
        try {
            String balance = accountService.getBalance(accountNumber);
            printUserMessage("Current balance for account number : " + accountNumber + " is : " + balance);
        } catch (UserException e) {
            printUserMessage(e.getMessage());
        }
    }

    @Override
    public void printLast10Transactions(long accountNumber) {
        handleExceptions(() -> transactionPrintingService.printLast10Transactions(accountNumber), null);
    }

    @Override
    public void printAllTransactions(long accountNumber) {
        handleExceptions(() -> transactionPrintingService.printAllTransactions(accountNumber), null);
    }

    private void handleExceptions(Action action, String message) {
        try {
            action.execute();
            printUserMessage(message);
        } catch (UserException e) {
            printUserMessage(e.getMessage());
        } catch (Exception e) {
            loggerError(e.getMessage());
        }
    }

    private void printUserMessage(String message) {
        if(message!=null){
            System.out.println("Message from Bank | " + message);
        }
    }

    private void loggerError(String message) {
        System.err.println("LOGGER : ERROR | " + this.getClass().getName() + " | THREAD " + Thread.currentThread().getName() + " | " + System.currentTimeMillis() + " | " + message);
    }
}
