/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank;

import com.bank.model.KYCDocuments;
import com.bank.model.AccountCreationRequest;
import com.bank.model.AccountType;

import java.math.BigDecimal;

class TestingUtility {
    public static AccountCreationRequest getDefaultSavingAccountCreationRequest() {
        return new AccountCreationRequest(AccountType.SAVINGS, new KYCDocuments(), new BigDecimal(20000));
    }
}
