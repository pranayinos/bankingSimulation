/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.service;

import com.bank.account.BaseAccount;
import com.bank.exception.InvalidTransactionException;
import com.bank.model.AccountCreationRequest;

import java.math.BigDecimal;
import java.util.HashMap;

public class AccountManagerFacadeImpl implements AccountManagerFacade {

    private final FundsTransferService fundsTransferService = new FundsTransferServiceImpl();
    private final SimpleTransactionIdGenerator transactionIdGenerator = SimpleTransactionIdGenerator.INSTANCE;
    private final HashMap<Long, BaseAccount> accountList= new HashMap();

    @Override
    public BaseAccount createAccount(AccountCreationRequest request) throws InvalidTransactionException {
        switch (request.getSelectedAccountType()) {
            case SAVINGS:
                loggerInfo(" Creating Account for User : " + request.getAccountOpeningDocument().getName());
                BaseAccount createdAccount = SavingsAccountFactory.INSTANCE.createAccount(request.getAccountOpeningDocument(), request.getOpeningBalance(), transactionIdGenerator.generate());
                accountList.put(createdAccount.getAccountNumber(), createdAccount);
                return createdAccount;
            default:
                throw new InvalidTransactionException("Invalid account creation request" + request.toString());
        }
    }

    @Override
    public void activateAccount(BaseAccount baseAccount) {
        loggerInfo(" Activating Account : " + baseAccount.toString());
        baseAccount.activateAccount();
    }

    @Override
    public void deleteAccount(BaseAccount baseAccount) throws InvalidTransactionException {
        synchronized (baseAccount.getLock()){
           baseAccount.withdraw(baseAccount.getBalance(), transactionIdGenerator.generate(), "ACCOUNT_CLOSURE");
            deActivateAccount(baseAccount);
            loggerInfo(" Account deleted : " + baseAccount.toString());
        }
    }

    @Override
    public void deActivateAccount(BaseAccount baseAccount) {
        loggerInfo(" Deactivating Account : " + baseAccount.toString());
        baseAccount.deActivateAccount();
    }

    @Override
    public void transfer(BaseAccount fromAccount, BaseAccount toAccount, BigDecimal transferAmount) throws InvalidTransactionException {
        if(fromAccount.getAccountNumber() == toAccount.getAccountNumber()){
            throw new InvalidTransactionException("To and from accounts cannot be same");
        }
        fundsTransferService.transferTo(fromAccount, toAccount, transferAmount, transactionIdGenerator.generate());
    }

    @Override
    public void depositTo(BaseAccount toAccount, BigDecimal depositAmount) throws InvalidTransactionException {
        toAccount.deposit(depositAmount, transactionIdGenerator.generate(), "CASH_DEPOSIT");
    }

    @Override
    public void withdrawFrom(BaseAccount fromAccount, BigDecimal withdrawalAmount) throws InvalidTransactionException {
        fromAccount.withdraw(withdrawalAmount, transactionIdGenerator.generate(), "CASH_WITHDRAWAL");
    }

    @Override
    public void listAllAccounts() {
        System.out.println("\n=================================LISTING ALL ACCOUNTS====================================");
        accountList.forEach((aLong, baseAccount) -> {
            String accountStatus = baseAccount.isAccountActive()?"Active": "INACTIVE";
            System.out.println("Account #"+baseAccount.getMaskedAccountNumber()
                    + " Owners Name: "+baseAccount.getName().getFullName()
                    + " Current balance: "+baseAccount.getBalanceAsString()
                    + " Status: "+accountStatus);
        });
        System.out.println("=========================================================================================\n");
    }

    private void loggerInfo(String message) {
        System.out.println("LOGGER : INFO | " + this.getClass().getName() + " | THREAD " + Thread.currentThread().getName() + " | " + System.currentTimeMillis() + " | " + message);
    }

}
