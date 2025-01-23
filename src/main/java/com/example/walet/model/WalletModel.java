package com.example.walet.model;

import lombok.*;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletModel {
    private String walletId;
    private int amount;
}
