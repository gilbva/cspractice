package com.github.gilbva.systems.replication;

public class DatabaseSnapshot {
    public int getCheckpoint() {
        return 0;
    }

    public String get(String key) {
        throw new UnsupportedOperationException();
    }
}
