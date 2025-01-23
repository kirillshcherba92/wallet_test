package com.example.walet.exception;

public class WalletException extends RuntimeException{

    public WalletException() {
        super();
    }

    public WalletException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
