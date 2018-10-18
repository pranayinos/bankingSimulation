/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.model;

import com.bank.account.AccountOpeningDocument;
import com.bank.model.Address;
import com.bank.model.Name;

public class KYCDocuments implements AccountOpeningDocument {

    @Override
    public Name getName() {
        //TODO Hardcoded will actually extract details from Documents/Aadhar apis
        return new Name("Pranay", "Kumar");
    }

    @Override
    public Address getAddress() {
        //TODO Hardcoded will actually extract details from Documents/Aadhar apis
        return new Address("Flat #301, Lotus Imperia", "Noida", "UP", "201309");
    }
}
