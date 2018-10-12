/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.service;

import java.util.concurrent.atomic.AtomicLong;

class SimpleAccountNumberGenerator implements AccountNumberGenerator {

    private final AtomicLong lastGeneratedNumber = new AtomicLong(10000000000L);

    @Override
    public long generate() {
        return lastGeneratedNumber.incrementAndGet();
    }
}
