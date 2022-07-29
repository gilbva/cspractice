package com.github.gilbva.systems.cache;

import java.time.LocalDateTime;
import java.util.*;

public class MemoryCache implements CacheService {

    private class Value {
        String value;

        LocalDateTime expTime;

        LocalDateTime expBlock;

        public Value(String value, LocalDateTime time) {
        }
    }

    public MemoryCache() {

    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void put(String key, String value, long expirationInSec) {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public Set<String> allKeys() {
        return null;
    }

    @Override
    public void close() throws Exception {

    }

    private void removeExpired() {

    }
}
