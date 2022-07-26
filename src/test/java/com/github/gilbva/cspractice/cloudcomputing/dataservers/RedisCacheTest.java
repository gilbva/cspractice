package com.github.gilbva.cspractice.cloudcomputing.dataservers;

import com.github.gilbva.cspractice.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class RedisCacheTest {
    @Test
    public void testCache() {
        var redisCache = new RedisCache("localhost", 6379);

        for (var key : redisCache.allKeys()) {
            redisCache.remove(key);
        }

        Assertions.assertTrue(redisCache.allKeys().isEmpty());

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put("test-key-" + UUID.randomUUID().toString().substring(0, 6), "value" + i);
        }

        for (var kp : map.entrySet()) {
            redisCache.put(kp.getKey(), kp.getValue(), 1);
        }

        TestUtils.sleep(1000);
        for (var kp : map.entrySet()) {
            System.out.println("get: " + kp.getKey() + " -> " + redisCache.get(kp.getKey()));
            Assertions.assertNull(redisCache.get(kp.getKey()), kp.getKey() + " must have expired");
        }

        for (var kp : map.entrySet()) {
            redisCache.put(kp.getKey(), kp.getValue(), 10);
        }

        for (var kp : map.entrySet()) {
            System.out.println("get: " + kp.getKey() + " -> " + redisCache.get(kp.getKey()));
            Assertions.assertEquals(redisCache.get(kp.getKey()), kp.getValue(), kp.getKey() + " must be present");
        }

        for (var kp : map.entrySet()) {
            System.out.println("deleting: " + kp.getKey() + " -> " + redisCache.get(kp.getKey()));
            redisCache.remove(kp.getKey());
        }

        for (var kp : map.entrySet()) {
            System.out.println("get: " + kp.getKey() + " -> " + redisCache.get(kp.getKey()));
            Assertions.assertNull(redisCache.get(kp.getKey()), kp.getKey() + " must have expired");
        }

        for (var kp : map.entrySet()) {
            redisCache.put(kp.getKey(), kp.getValue(), 100);
        }

        String[] expected = map.keySet().toArray(new String[0]);
        String[] allKeys = redisCache.allKeys().toArray(new String[0]);

        Arrays.sort(expected);
        Arrays.sort(allKeys);

        Assertions.assertArrayEquals(expected, allKeys);
    }
}
