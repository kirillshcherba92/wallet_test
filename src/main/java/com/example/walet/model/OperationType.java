package com.example.walet.model;

public enum OperationType {
    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW");

    OperationType(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }
}
