package com.bank.persistance;

import com.bank.exception.UserException;
import com.bank.model.BaseAccount;

public interface AccountRepository {

    BaseAccount findByAccountNumber(long accountNumber) throws UserException;

    void save(BaseAccount baseAccount);

    void delete(long accountNumber) throws UserException;

}
