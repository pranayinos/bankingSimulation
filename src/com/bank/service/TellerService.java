package com.bank.service;

import com.bank.exception.UserException;

public interface TellerService {

    void transfer(Long fromAccount, Long toAccount, String transferAmount) throws UserException;

    void deposit(long accountNumber, String transferAmount) throws UserException;

    void withdraw(long accountNumber, String transferAmount) throws UserException;

}
