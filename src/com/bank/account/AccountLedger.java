/*
 * *
 *   * Copyright (c) ION Trading UK Limited 2015
 *   * All Rights reserved.
 *
 */

package com.bank.account;

import java.util.LinkedList;

public class AccountLedger {
    private final LinkedList<LedgerEntry> ledgerEntries = new LinkedList<>();

    public void addTransaction(LedgerEntry entry) {
        ledgerEntries.add(entry);
    }

    public void printLast10Transactions() {
        System.out.println("=================================LAST-10-TRANSACTIONS====================================");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("||\tDate\t|\tNarration\t|\tWithdrawal Amount\t|\tDeposit Amount\t| \tClosing Balance\t||");
        int txnToDisplay = ledgerEntries.size() <= 10 ? ledgerEntries.size()-1 : 10;
        for(int i = txnToDisplay; i>=0; i--){
            ledgerEntries.get(i).printEntry();
        }
        System.out.println("==========================================================================================\n");
    }
}
