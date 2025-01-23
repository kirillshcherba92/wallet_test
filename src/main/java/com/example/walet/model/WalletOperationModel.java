package com.example.walet.model;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletOperationModel {
    private Integer id;
    private OperationType operationType;
    private Integer amount;
    private String walletId;
}
