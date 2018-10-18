package com.bank.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Objects;

public class SavingsAccount implements BaseAccount {

    private final long accountNumber;
    private final String name;
    private final LinkedList<TransactionDetail> transactionList = new LinkedList<>();
    private final String address;
    private BigDecimal balance;

    public SavingsAccount(long accountNumber, String name, String address, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.balance = balance;
    }

    @Override
    public Long getAccountNumber() {
        return this.accountNumber;
    }

    @Override
    public BigDecimal getBalance() {
        return this.balance;
    }

    @Override
    public void addToBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    @Override
    public void subtractFromBalance(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public LinkedList<TransactionDetail> getTransactionList() {
        return transactionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavingsAccount that = (SavingsAccount) o;
        return accountNumber == that.accountNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }


    @Override
    public int compareTo(BaseAccount o) {
        return this.getAccountNumber().compareTo(o.getAccountNumber());
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "accountNumber=" + accountNumber +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", balance=" + balance +
                '}';
    }
}
