/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.model;

public class Address {
    private final String address;
    private final String city;
    private final String state;
    private final String pin;

    public Address(String address, String city, String state, String pin) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.pin = pin;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPin() {
        return pin;
    }
}
