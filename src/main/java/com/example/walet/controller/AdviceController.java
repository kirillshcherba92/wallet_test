package com.example.walet.controller;

import com.example.walet.exception.WalletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {
    @ExceptionHandler
    public ResponseEntity<String> handleControllerException(WalletException walletException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(walletException.getMessage());
    }

}
