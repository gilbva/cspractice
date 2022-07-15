package com.github.gilbva.cspractice.leader;

public class BatchProcess implements Runnable {
    public BatchProcess(String id, LeaseService lease) {
    }

    public String getId() {
        throw new UnsupportedOperationException();
    }

    public void shutdown() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    public void restart() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException();
    }
}
