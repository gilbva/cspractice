package com.github.gilbva.cspractice.databases.replication;

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
