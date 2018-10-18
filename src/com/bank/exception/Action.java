package com.bank.exception;

@FunctionalInterface
public interface Action {
    void execute() throws UserException;
}
