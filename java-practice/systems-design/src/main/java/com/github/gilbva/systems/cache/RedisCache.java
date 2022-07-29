package com.github.gilbva.systems.cache;

import java.util.Set;

/**
 * https://github.com/redis/jedis
 */
public class RedisCache implements CacheService, AutoCloseable {

    public RedisCache(String host, int port) {
    }

    public Set<String> allKeys() {
        return null;
    }

    public String get(String key) {
        return null;
    }

    public long getLength(String key) {
        return 0L;
    }

    public void put(String key, String value, long expirationInSec) {

    }

    public boolean putIfAbsent(String key, String value) {
        return false;
    }

    public void remove(String key) {

    }

    public long expirationTime(String key) {
        return 0L;
    }

    public long increment(String key) {
        return 0L;
    }

    public long decrement(String key) {
        return 0L;
    }

    public long increment(String key, long amount) {
        return 0L;
    }

    public long decrement(String key, long amount) {
        return 0L;
    }

    public void removeAll() {

    }

    @Override
    public void close() throws Exception {

    }
}
