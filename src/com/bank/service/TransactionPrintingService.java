package com.bank.service;

import com.bank.exception.UserException;

public interface TransactionPrintingService {

    void printLast10Transactions(long accountNumber) throws UserException;

    void printAllTransactions(long accountNumber) throws UserException;

}
