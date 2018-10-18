package com.bank.service;

import com.bank.exception.UserException;

import java.math.BigDecimal;

public interface AccountService {

    long createAccount(String name, String address, BigDecimal openingBalance);

    void deleteAccount(long accountNumber) throws UserException;

    String getBalance(long accountNumber) throws UserException;

}
