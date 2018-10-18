/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank;

import com.bank.common.BankFacade;
import com.bank.common.BankFacadeImpl;

class BankSimulator {
    private final BankFacade bankFacade = new BankFacadeImpl();

    public void runSimulation() {
        loggerInfo(" Starting Simulation ");
        long accountOne = bankFacade.createAccount("Tester One", "Sector 56, Delhi", "2000.00");
        long accountTwo = bankFacade.createAccount("Tester Two", "Sector 63, Delhi", "4000.00");
        bankFacade.deposit(accountOne, "1200.00");
        bankFacade.deposit(accountTwo, "200.00");
        bankFacade.withdraw(accountOne, "100.00");
        bankFacade.withdraw(accountTwo, "3000.00");
        bankFacade.transfer(accountOne, accountTwo, "111");
        bankFacade.transfer(accountTwo, accountOne, "1000");
        bankFacade.getBalance(accountTwo);
        bankFacade.transfer(accountOne, accountTwo, "1010");
        bankFacade.transfer(accountTwo, accountOne, "2300");
        bankFacade.printLast10Transactions(accountOne);
        bankFacade.printLast10Transactions(accountTwo);
        bankFacade.printAllTransactions(accountTwo);
        bankFacade.deleteAccount(accountTwo);
        loggerInfo(" Simulation Completed ");
    }

    private void loggerInfo(String message) {
        System.out.println("LOGGER : INFO | " + this.getClass().getName() + " | THREAD " + Thread.currentThread().getName() + " | " + System.currentTimeMillis() + " | " + message);
    }
}
