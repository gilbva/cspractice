package com.github.gilbva.cspractice.replication;

public interface WalEntry {
    enum Operation
    {
        SET,
        DELETE;
    }

    Operation getOperation();

    String getKey();

    String getValue();
}
