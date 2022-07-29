package com.github.gilbva.systems.cache;

import java.util.Set;

public interface CacheService extends AutoCloseable {
    String get(String key);

    void put(String key, String value, long expirationInSec);

    void remove(String key);

    Set<String> allKeys();
}
