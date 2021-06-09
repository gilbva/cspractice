package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.datastructures2.*;
import com.github.gilbva.cspractice.datastructures2.HashMap;
import com.github.gilbva.cspractice.datastructures2.PriorityQueue;
import com.github.gilbva.cspractice.datastructures2.TreeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.*;

public class DataStructures2Test {
    @TestFactory
    Collection<DynamicTest> testHashMap() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            String[] keys = new String[arr.length];
            HashMap<String, Integer> map = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(i, map.size());
                keys[i] = UUID.randomUUID().toString();
                map.put(keys[i], arr[i]);
                Assertions.assertEquals(map.get(keys[i]), arr[i]);
                map.put(keys[i], arr[i]);
                Assertions.assertEquals(map.get(keys[i]), arr[i]);
            }
            Assertions.assertEquals(arr.length, map.size());
            for (int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(arr.length - i, map.size());
                Assertions.assertTrue(map.exists(keys[i]));
                Assertions.assertEquals(arr[i], map.get(keys[i]));
                map.remove(keys[i]);
                Assertions.assertFalse(map.exists(keys[i]));
                Assertions.assertNull(map.get(keys[i]));

                map.put(keys[i], 0);
                Assertions.assertTrue(map.exists(keys[i]));
                Assertions.assertEquals(0, map.get(keys[i]));
                map.remove(keys[i]);
                Assertions.assertFalse(map.exists(keys[i]));
                Assertions.assertNull(map.get(keys[i]));
            }
            Assertions.assertEquals(0, map.size());
        };
        for (int i = 0; i < 20; i++) {
            result.add(TestUtils.genericArrayTest(i, "test hash map " + i, callback));
        }
        for (int i = 100; i < 10_000; i += 500) {
            result.add(TestUtils.genericArrayTest(i, "test hash map " + i, callback));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testPriorityQueue() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a));
            for (int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(i, queue.size());
                queue.offer(arr[i]);
            }
            Assertions.assertEquals(arr.length, queue.size());
            for (int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(arr.length - i, queue.size());
                int value = queue.peek();
                arr[i] = queue.poll();
                Assertions.assertEquals(value, arr[i]);
            }
            Assertions.assertEquals(0, queue.size());
        };
        for (int i = 0; i < 100; i++) {
            result.add(TestUtils.arraySortTest(i, "test priority queue sort " + i, callback));
        }
        for (int i = 1_000; i < 100_000; i += 1_000) {
            result.add(TestUtils.arraySortTest(i, "test priority queue sort " + i, callback));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testTreeMap() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            String[] keys = new String[arr.length];
            TreeMap<String, Integer> map = new TreeMap<>(String::compareTo);
            for (int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(i, map.size());
                keys[i] = UUID.randomUUID().toString();
                map.put(keys[i], arr[i]);
                Assertions.assertTrue(map.exists(keys[i]));
                Assertions.assertEquals(map.get(keys[i]), arr[i]);
                map.put(keys[i], arr[i]);
                Assertions.assertTrue(map.exists(keys[i]));
                Assertions.assertEquals(map.get(keys[i]), arr[i]);
                map.remove(keys[i]);
                Assertions.assertFalse(map.exists(keys[i]));
                Assertions.assertNull(map.get(keys[i]));
                map.put(keys[i], arr[i]);
                Assertions.assertTrue(map.exists(keys[i]));
                Assertions.assertEquals(map.get(keys[i]), arr[i]);

                for (String key : map) {
                    Assertions.assertNotNull(key);
                }
            }
            Assertions.assertEquals(arr.length, map.size());
            for (int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(arr.length - i, map.size());
                Assertions.assertTrue(map.exists(keys[i]));
                Assertions.assertEquals(arr[i], map.get(keys[i]));
                map.remove(keys[i]);
                Assertions.assertFalse(map.exists(keys[i]));
                Assertions.assertNull(map.get(keys[i]));

                map.put(keys[i], 0);
                Assertions.assertTrue(map.exists(keys[i]));
                Assertions.assertEquals(0, map.get(keys[i]));
                map.remove(keys[i]);
                Assertions.assertFalse(map.exists(keys[i]));
                Assertions.assertNull(map.get(keys[i]));

                for (String key : map) {
                    Assertions.assertNotNull(key);
                }
            }
            Assertions.assertEquals(0, map.size());
        };
        for (int i = 0; i < 20; i++) {
            result.add(TestUtils.genericArrayTest(i, "test tree map " + i, callback));
        }
        for (int i = 100; i < 10_000; i += 500) {
            result.add(TestUtils.genericArrayTest(i, "test tree map " + i, callback));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testLRUCache() {
        List<DynamicTest> result = new ArrayList<>();
        result.add(DynamicTest.dynamicTest("test lru cache 1", () -> {
            LRUCache<String, Integer> cache = new LRUCache<>(5);
            cache.refer("one", 1);
            Assertions.assertNotNull(cache.get("one"));
            cache.refer("two", 2);
            Assertions.assertNotNull(cache.get("two"));
            cache.refer("three", 3);
            Assertions.assertNotNull(cache.get("three"));
            cache.refer("four", 4);
            Assertions.assertNotNull(cache.get("four"));
            cache.refer("five", 5);
            Assertions.assertNotNull(cache.get("five"));
            cache.refer("six", 6);
            Assertions.assertNotNull(cache.get("six"));
            Assertions.assertNull(cache.get("one"));
            Assertions.assertNotNull(cache.get("two"));
            Assertions.assertNotNull(cache.get("three"));
            Assertions.assertNotNull(cache.get("four"));
            Assertions.assertNotNull(cache.get("five"));
            Assertions.assertNotNull(cache.get("six"));
            cache.refer("seven", 7);
            Assertions.assertNotNull(cache.get("seven"));
            Assertions.assertNull(cache.get("two"));
            Assertions.assertNotNull(cache.get("three"));
            cache.refer("eight", 8);
            Assertions.assertNull(cache.get("four"));
            Assertions.assertNotNull(cache.get("three"));

            cache.clear();
            Assertions.assertNull(cache.get("one"));
            Assertions.assertNull(cache.get("two"));
            Assertions.assertNull(cache.get("three"));
            Assertions.assertNull(cache.get("four"));
            Assertions.assertNull(cache.get("five"));
            Assertions.assertNull(cache.get("six"));
            Assertions.assertNull(cache.get("seven"));
            Assertions.assertNull(cache.get("eight"));

            cache.refer("one", 1);
            Assertions.assertNotNull(cache.get("one"));
            cache.refer("two", 2);
            Assertions.assertNotNull(cache.get("two"));
            cache.refer("three", 3);
            Assertions.assertNotNull(cache.get("three"));
            cache.refer("four", 4);
            Assertions.assertNotNull(cache.get("four"));
            cache.refer("five", 5);
            Assertions.assertNotNull(cache.get("five"));
            cache.refer("six", 6);
            Assertions.assertNotNull(cache.get("six"));
            Assertions.assertNull(cache.get("one"));
            Assertions.assertNotNull(cache.get("two"));
            Assertions.assertNotNull(cache.get("three"));
            Assertions.assertNotNull(cache.get("four"));
            Assertions.assertNotNull(cache.get("five"));
            Assertions.assertNotNull(cache.get("six"));
            cache.refer("seven", 7);
            Assertions.assertNotNull(cache.get("seven"));
            Assertions.assertNull(cache.get("two"));
            Assertions.assertNotNull(cache.get("three"));
            cache.refer("eight", 8);
            Assertions.assertNull(cache.get("four"));
            Assertions.assertNotNull(cache.get("three"));
        }));
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testBTree() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            BTree tree = new BTree();
            for(int i = 0; i < arr.length; i++) {
                tree.put(arr[i], UUID.randomUUID().toString());
            }
        };
        for (int i = 0; i < 20; i++) {
            result.add(TestUtils.genericArrayTest(i, "test btree " + i, callback));
        }
        for (int i = 100; i < 10_000; i += 500) {
            result.add(TestUtils.genericArrayTest(i, "test btree " + i, callback));
        }
        return result;
    }
}