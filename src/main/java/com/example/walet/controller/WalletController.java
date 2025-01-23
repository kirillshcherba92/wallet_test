package com.example.walet.controller;

import com.example.walet.model.WalletDto;
import com.example.walet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("wallet/")
    public ResponseEntity<String> changeBalanceOfWallet(@RequestBody WalletDto walletDto) {
        walletService.changeAmount(walletDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(String.format("Операция \"%s\" была выполнена успешно", walletDto.getOperationType().getName()));
    }

    @GetMapping("wallets/{WALLET_UUID}")
    public ResponseEntity<Integer> getCurrentBalanceOfWallet(@PathVariable("WALLET_UUID") String walletId) {
        int outCurrentBalance = walletService.findOutCurrentBalance(UUID.fromString(walletId));
        return ResponseEntity.status(HttpStatus.OK).body(outCurrentBalance);
    }
}
