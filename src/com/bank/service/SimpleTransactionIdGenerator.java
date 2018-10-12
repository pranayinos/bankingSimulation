/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.service;

import java.util.concurrent.atomic.AtomicLong;

enum SimpleTransactionIdGenerator implements TransactionIdGenerator {
    INSTANCE;
    private final AtomicLong lastGeneratedNumber = new AtomicLong(0);

    @Override
    public long generate() {
        return lastGeneratedNumber.incrementAndGet();
    }
}
