package com.example.walet.model;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletDto {
    private UUID walletId;
    private OperationType operationType;
    private int amount;
}
