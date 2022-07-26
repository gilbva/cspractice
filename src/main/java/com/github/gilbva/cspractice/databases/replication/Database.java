package com.github.gilbva.cspractice.databases.replication;

import java.util.List;

public class Database {
    public Database() {
    }

    public DatabaseSnapshot takeSnapshot() {
        throw new UnsupportedOperationException();
    }

    public String get(String key) {
        throw new UnsupportedOperationException();
    }

    public void set(String key, String value) {
        throw new UnsupportedOperationException();
    }

    public void delete(String key) {
        throw new UnsupportedOperationException();
    }

    public List<WalEntry> getWalEntries(int checkpoint) {
        return null;
    }

    public void addFollower(Database database) {

    }
}
