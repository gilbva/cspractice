package com.github.gilbva.cspractice.cloud.replication;

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
