/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.model;

import com.bank.account.AccountOpeningDocument;

import java.math.BigDecimal;

public class AccountCreationRequest {
    private final AccountType selectedAccountType;
    private final AccountOpeningDocument accountOpeningDocument;
    private final BigDecimal openingBalance;

    public AccountCreationRequest(AccountType selectedAccountType, AccountOpeningDocument accountOpeningDocument, BigDecimal openingBalance) {
        this.selectedAccountType = selectedAccountType;
        this.accountOpeningDocument = accountOpeningDocument;
        this.openingBalance = openingBalance;
    }

    public AccountType getSelectedAccountType() {
        return selectedAccountType;
    }

    public AccountOpeningDocument getAccountOpeningDocument() {
        return accountOpeningDocument;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }
}
