package com.github.gilbva.cspractice.cache;

import com.github.gilbva.cspractice.TestUtils;
import com.github.gilbva.systems.cache.CacheService;
import com.github.gilbva.systems.cache.MemoryCache;
import com.github.gilbva.systems.cache.RedisCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.*;

public class CacheServiceTest {

    private class CacheTestEntry {
        String key;

        String value;

        int seconds;

        boolean mustExpire;

        public CacheTestEntry(String key, String value, int seconds, boolean mustExpire) {
            this.key = key;
            this.value = value;
            this.seconds = seconds;
            this.mustExpire = mustExpire;
        }
    }

    @TestFactory
    public Collection<DynamicTest> testCaches() {
        List<DynamicTest> result = new ArrayList<>();
        List<CacheTestEntry> testEntries = createTestEntries();
        result.add(DynamicTest.dynamicTest("test memory cache ", () -> testCache(new MemoryCache(), testEntries,10_000)));
        result.add(DynamicTest.dynamicTest("test redis cache ", () -> {
            var redis = new RedisCache("redis", 6379);
            redis.removeAll();
            testCache(redis, testEntries,10_000);
        }));
        return result;
    }

    public void testCache(CacheService cache, List<CacheTestEntry> testEntries, int waitTime) {
        testEntries.parallelStream()
                .forEach(x -> {
                    System.out.println("adding key " + x.key);
                    cache.put(x.key, x.value, x.seconds);
                    cache.put(x.key, x.value, x.seconds);
                    cache.put(x.key, x.value, x.seconds);
                });

        TestUtils.sleep(waitTime);

        String[] expectedAllKeys = testEntries.stream()
                .filter(x -> !x.mustExpire)
                .map(x -> x.key)
                .distinct()
                .sorted()
                .toArray(String[]::new);
        String[] allKeys = cache.allKeys().toArray(new String[0]);
        Arrays.sort(allKeys);

        Assertions.assertArrayEquals(expectedAllKeys, allKeys);

        for (var entry : testEntries) {
            if (entry.mustExpire) {
                System.out.println("testing expired key " + entry.key);
                Assertions.assertNull(cache.get(entry.key));
            } else {
                System.out.println("testing not-expired key " + entry.key);
                Assertions.assertEquals(entry.value, cache.get(entry.key));
            }
        }
    }

    private List<CacheTestEntry> createTestEntries() {
        List<CacheTestEntry> lst = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 1000; i++) {
            var key = UUID.randomUUID().toString();
            var entry = new CacheTestEntry(key, "value" + i, 1 + random.nextInt(8), true);
            lst.add(entry);
            lst.add(entry);
            lst.add(entry);
        }

        for(int i = 0; i < 1000; i++) {
            var key = UUID.randomUUID().toString();
            var entry = new CacheTestEntry(key, "value" + i, 12 + random.nextInt(8), false);
            lst.add(entry);
            lst.add(entry);
            lst.add(entry);
        }
        return lst;
    }
}
