package com.github.gilbva.systems.replication;

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
