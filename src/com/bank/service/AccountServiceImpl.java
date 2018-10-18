package com.bank.service;

import com.bank.exception.UserException;
import com.bank.model.BaseAccount;
import com.bank.model.EntryType;
import com.bank.model.SavingsAccount;
import com.bank.model.TransactionDetail;
import com.bank.persistance.AccountRepository;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AccountServiceImpl implements AccountService {

    private final TellerService tellerService;
    private final AccountRepository accountRepository;
    private final AtomicLong lastGeneratedNumber = new AtomicLong(10000000000L);

    public AccountServiceImpl(AccountRepository accountRepository, TellerService tellerService) {
        this.accountRepository = accountRepository;
        this.tellerService = tellerService;
    }

    @Override
    public long createAccount(String name, String address, BigDecimal openingBalance) {
        BaseAccount newAccount = new SavingsAccount(lastGeneratedNumber.incrementAndGet(), name, address, openingBalance);
        newAccount.getTransactionList().add(new TransactionDetail(EntryType.DEBIT, UUID.randomUUID().toString(), "OPENING-INITIAL-DEPOSIT", new BigDecimal(0), openingBalance, openingBalance));
        accountRepository.save(newAccount);
        return newAccount.getAccountNumber();
    }

    @Override
    public void deleteAccount(long accountNumber) throws UserException {
        BaseAccount account = accountRepository.findByAccountNumber(accountNumber);
        tellerService.withdraw(accountNumber, account.getBalance().toPlainString());
        accountRepository.delete(accountNumber);
    }

    @Override
    public String getBalance(long accountNumber) throws UserException {
        BaseAccount account = accountRepository.findByAccountNumber(accountNumber);
        return formatNumberToCurrency(account.getBalance());
    }

    private String formatNumberToCurrency(BigDecimal number) {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.UK);
        double money = number.doubleValue();
        return n.format(money);
    }
}
