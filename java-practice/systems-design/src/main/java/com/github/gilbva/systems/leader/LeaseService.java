package com.github.gilbva.systems.leader;

public class LeaseService {
    public synchronized void lease(String id) {
        throw new UnsupportedOperationException();
    }

    public String getCurrent() {
        throw new UnsupportedOperationException();
    }

    private boolean hasExpired() {
        throw new UnsupportedOperationException();
    }
}
