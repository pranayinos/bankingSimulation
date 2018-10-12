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

public interface AccountManagerFacade {
    BaseAccount createAccount(AccountCreationRequest request) throws InvalidTransactionException;

    void activateAccount(BaseAccount baseAccount);

    void deleteAccount(BaseAccount baseAccount) throws InvalidTransactionException;

    void deActivateAccount(BaseAccount baseAccount);

    void transfer(BaseAccount fromAccount, BaseAccount toAccount, BigDecimal transferAmount) throws InvalidTransactionException;

    void depositTo(BaseAccount toAccount, BigDecimal transferAmount) throws InvalidTransactionException;

    void withdrawFrom(BaseAccount fromAccount, BigDecimal transferAmount) throws InvalidTransactionException;

    void listAllAccounts();

}
