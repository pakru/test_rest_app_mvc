package com.example.bcs.server.demo.exception;

public class AccountNotFoundException extends Exception {

    private Long id;

    public AccountNotFoundException() {
        super("Account not Found");
    }

    public AccountNotFoundException(Long accountId) {
        this();
        this.id = accountId;
    }

    public Long getAccountId() {
        return id;
    }
}
