package com.bank.persistance;

import com.bank.exception.UserException;
import com.bank.model.BaseAccount;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleAccountRepository implements AccountRepository {

    private final Map<Long, BaseAccount> accountMap;

    public SimpleAccountRepository() {
        this.accountMap = new ConcurrentHashMap<>();
    }


    @Override
    public BaseAccount findByAccountNumber(long accountNumber) throws UserException {
        return Optional.ofNullable(accountMap.get(accountNumber)).orElseThrow(() -> new UserException("Invalid Account number : " + accountNumber));
    }

    @Override
    public void save(BaseAccount baseAccount) {
        accountMap.put(baseAccount.getAccountNumber(), baseAccount);
    }

    @Override
    public void delete(long accountNumber) throws UserException {
        accountMap.remove(accountNumber);
    }

}
