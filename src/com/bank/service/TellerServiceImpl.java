package com.bank.service;

import com.bank.exception.UserException;
import com.bank.model.BaseAccount;
import com.bank.model.EntryType;
import com.bank.model.TransactionDetail;
import com.bank.persistance.AccountRepository;
import com.bank.utilities.Validator;

import java.math.BigDecimal;
import java.util.UUID;

public class TellerServiceImpl implements TellerService {

    private final AccountRepository accountRepository;

    public TellerServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public void transfer(Long fromAccountNumber, Long toAccountNumber, String transferAmountString) throws UserException {
        if(fromAccountNumber.equals(toAccountNumber)){
            throw new UserException("Entered account numbers cannot be same");
        }
        BaseAccount fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
        BaseAccount toAccount = accountRepository.findByAccountNumber(toAccountNumber);
        BigDecimal transferAmount = Validator.verifyAmount(transferAmountString);
        loggerInfo("Transferring : " + transferAmount.toPlainString() + ", to Account : " + toAccount.toString());

        Object firstLock = fromAccount.compareTo(toAccount) < 0 ? fromAccount : toAccount;
        Object lastLock = fromAccount.compareTo(toAccount) < 0 ? toAccount : fromAccount;
        synchronized (firstLock) {
            synchronized (lastLock) {
                accountHasSufficientBalance(fromAccount, transferAmount);
                fromAccount.subtractFromBalance(transferAmount);
                toAccount.addToBalance(transferAmount);
                TransactionDetail creditEntry = new TransactionDetail(EntryType.CREDIT, UUID.randomUUID().toString(),
                        "Cash Withdrawal", transferAmount, BigDecimal.ZERO, fromAccount.getBalance());
                fromAccount.getTransactionList().add(creditEntry);
                TransactionDetail debitEntry = new TransactionDetail(EntryType.DEBIT, UUID.randomUUID().toString(),
                        "Cash Deposit", BigDecimal.ZERO, transferAmount, toAccount.getBalance());
                toAccount.getTransactionList().add(debitEntry);
            }
        }
    }

    @Override
    public void deposit(long accountNumber, String depositAmountString) throws UserException {
        BaseAccount account = accountRepository.findByAccountNumber(accountNumber);
        BigDecimal depositAmount = Validator.verifyAmount(depositAmountString);
        loggerInfo(" Depositing :" + depositAmount + " to account : " + this.toString());
        synchronized (account) {
            account.addToBalance(depositAmount);
            TransactionDetail entry = new TransactionDetail(EntryType.DEBIT, UUID.randomUUID().toString(),
                    "Cash Deposit", BigDecimal.ZERO, depositAmount, account.getBalance());
            account.getTransactionList().add(entry);
        }
    }

    @Override
    public void withdraw(long accountNumber, String withdrawalAmountString) throws UserException {
        BaseAccount account = accountRepository.findByAccountNumber(accountNumber);
        BigDecimal withdrawalAmount = Validator.verifyAmount(withdrawalAmountString);
        accountHasSufficientBalance(account, withdrawalAmount);
        loggerInfo(" Withdrawing :" + withdrawalAmount + " from account : " + this.toString());
        synchronized (account) {
            account.subtractFromBalance(withdrawalAmount);
            TransactionDetail entry = new TransactionDetail(EntryType.CREDIT, UUID.randomUUID().toString(),
                    "Cash Withdrawal", withdrawalAmount, BigDecimal.ZERO, account.getBalance());
            account.getTransactionList().add(entry);
        }
    }

    private void loggerInfo(String message) {
        System.out.println("LOGGER : INFO | " + this.getClass().getName() + " | THREAD "
                + Thread.currentThread().getName() + " | " + System.currentTimeMillis() + " | " + message);
    }

    private void accountHasSufficientBalance(BaseAccount account, BigDecimal amount) throws UserException {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new UserException("Insufficient funds, cannot process txn of : " + amount.toPlainString());
        }
    }
}

