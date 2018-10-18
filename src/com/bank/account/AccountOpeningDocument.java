/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.account;

import com.bank.model.Address;
import com.bank.model.Name;

public interface AccountOpeningDocument {
    Name getName();

    Address getAddress();
}
