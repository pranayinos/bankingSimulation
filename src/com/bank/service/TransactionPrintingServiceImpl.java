package com.bank.service;

import com.bank.exception.UserException;
import com.bank.model.BaseAccount;
import com.bank.model.TransactionDetail;
import com.bank.persistance.AccountRepository;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class TransactionPrintingServiceImpl implements TransactionPrintingService {

    private final AccountRepository accountRepository;
    private final String LAST_10_TXN = "LAST-10-TRANSACTIONS";
    private final String ALL_TXN = "ALL-TRANSACTIONS-FOR-ACCOUNT";

    public TransactionPrintingServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void printLast10Transactions(long accountNumber) throws UserException {
        BaseAccount baseAccount = accountRepository.findByAccountNumber(accountNumber);
        printStatementHeader(LAST_10_TXN, baseAccount);
        List<TransactionDetail> transactionList = baseAccount.getTransactionList();
        int txnToDisplay = transactionList.size() <= 10 ? transactionList.size() - 1 : 10;
        for (int i = txnToDisplay; i >= 0; i--) {
            transactionList.get(i).printEntry();
        }
        printStatementFooter();
    }

    @Override
    public void printAllTransactions(long accountNumber) throws UserException {
        BaseAccount baseAccount = accountRepository.findByAccountNumber(accountNumber);
        List<TransactionDetail> transactionList = baseAccount.getTransactionList();
        printStatementHeader(ALL_TXN, baseAccount);
        for (int i = transactionList.size()-1; i >= 0; i--) {
            transactionList.get(i).printEntry();
        }
        printStatementFooter();
    }

    private void printStatementHeader(String statementType, BaseAccount account) {
        System.out.println("\n==============================" + statementType + "==============================");
        System.out.println(account.getAccountNumber()+"--------------------------------------------------------------------"+ account.getName());
        System.out.println("Address=================================================================="+account.getAddress());
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("||\t\t\tDate\t\t\t|\tNarration\t|\tWithdrawal Amount\t|\tDeposit Amount\t| \tClosing Balance\t||");
    }

    private void printStatementFooter() {
        System.out.println("==========================================================================================\n");
    }
}
