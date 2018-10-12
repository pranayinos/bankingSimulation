/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank;

import com.bank.account.BaseAccount;
import com.bank.exception.BankingException;
import com.bank.service.AccountManagerFacade;
import com.bank.service.AccountManagerFacadeImpl;

import java.math.BigDecimal;

class BankSimulator {
    private final AccountManagerFacade accountManagerFacade = new AccountManagerFacadeImpl();

    public void runSimulation() {
        loggerError(" Starting Simulation ");
        try {
            accountManagerFacade.listAllAccounts();
            BaseAccount accountOne = accountManagerFacade.createAccount(TestingUtility.getDefaultSavingAccountCreationRequest());
            BaseAccount accountTwo = accountManagerFacade.createAccount(TestingUtility.getDefaultSavingAccountCreationRequest());
            accountManagerFacade.activateAccount(accountOne);
            accountManagerFacade.listAllAccounts();
            accountManagerFacade.activateAccount(accountTwo);
            accountManagerFacade.listAllAccounts();
            accountManagerFacade.depositTo(accountOne, new BigDecimal(1200.00));
            loggerInfo(" Printing account state : " + accountOne.toString());
            accountManagerFacade.depositTo(accountTwo, new BigDecimal(200.00));
            loggerInfo(" Printing account state : " + accountTwo.toString());
            accountManagerFacade.withdrawFrom(accountOne, new BigDecimal(100.00));
            accountManagerFacade.withdrawFrom(accountTwo, new BigDecimal(3000.00));
            loggerInfo(" Printing account state : " + accountTwo.toString());
            loggerInfo(" Printing account state : " + accountOne.toString());
            accountManagerFacade.transfer(accountOne, accountTwo, new BigDecimal(111));
            loggerInfo(" Printing account state : " + accountTwo.toString());
            loggerInfo(" Printing account state : " + accountOne.toString());
            accountManagerFacade.transfer(accountTwo, accountOne, new BigDecimal(1000));
            accountManagerFacade.transfer(accountOne, accountTwo, new BigDecimal(1010));
            accountManagerFacade.transfer(accountTwo, accountOne, new BigDecimal(2300));
            accountManagerFacade.deActivateAccount(accountOne);
            accountManagerFacade.deleteAccount(accountTwo);
            accountOne.printLast10Transactions();
            accountTwo.printLast10Transactions();
            accountManagerFacade.listAllAccounts();
        } catch (BankingException e) {
            loggerError(" Exception details : " + e.getMessage());
        }
    }

    private void loggerInfo(String message) {
        System.out.println("LOGGER : INFO | " + this.getClass().getName() + " | THREAD " + Thread.currentThread().getName() + " | " + System.currentTimeMillis() + " | " + message);
    }

    private void loggerError(String message) {
        System.err.println("LOGGER : ERROR | " + this.getClass().getName() + " | THREAD " + Thread.currentThread().getName() + " | " + System.currentTimeMillis() + " | " + message);
    }
}
