package com.github.gilbva.cspractice.cloudcomputing.replication;

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
