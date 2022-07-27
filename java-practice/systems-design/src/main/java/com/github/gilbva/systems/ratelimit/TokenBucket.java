package com.github.gilbva.systems.ratelimit;

public class TokenBucket {

    public TokenBucket(int maxTokens, int refillTimeInSec) {
    }

    public int getRefillTokens() {
        return 0;
    }

    public int getRefillTime() {
        return 0;
    }

    public boolean isAllow(String key) {
        return false;
    }
}
