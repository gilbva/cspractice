package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.datastructures.BloomFilter;
import com.github.gilbva.cspractice.datastructures.BTree;
import com.github.gilbva.cspractice.datastructures.*;
import com.github.gilbva.cspractice.datastructures.HashMap;
import com.github.gilbva.cspractice.datastructures.PriorityQueue;
import com.github.gilbva.cspractice.datastructures.TreeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class DataStructuresTest {

    @TestFactory
    Collection<DynamicTest> testArrayStack() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            ArrayStack<Integer> stack = new ArrayStack<>();
            for(int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(i, stack.size());
                stack.push(arr[i]);
            }
            Assertions.assertEquals(arr.length, stack.size());
            for(int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(arr.length - i, stack.size());
                int value = stack.get();
                arr[i] = stack.pop();
                Assertions.assertEquals(value, arr[i]);
            }
            Assertions.assertEquals(0, stack.size());
            Assertions.assertThrows(Exception.class, stack::get);
            Assertions.assertThrows(Exception.class, stack::pop);
        };
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with stack " + i, callback));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with stack " + i, callback));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testArrayQueue() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            ArrayQueue<Integer> queue = new ArrayQueue<>();
            for(int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(i, queue.size());
                queue.offer(arr[i]);
            }
            Assertions.assertEquals(arr.length, queue.size());
            for(int i = arr.length - 1; i >= 0; i--) {
                int value = queue.peek();
                arr[i] = queue.poll();
                Assertions.assertEquals(i, queue.size());
                Assertions.assertEquals(value, arr[i]);
            }
            Assertions.assertEquals(0, queue.size());
            Assertions.assertThrows(Exception.class, queue::peek);
            Assertions.assertThrows(Exception.class, queue::poll);

        };
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with queue " + i, callback));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with queue " + i, callback));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testDLinkedListFront() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            DLinkedList<Integer> dll = new DLinkedList<>();
            for(int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(i, dll.size());
                dll.addFirst(arr[i]);
                dll.removeFirst();
                dll.addFirst(arr[i]);
            }
            Assertions.assertEquals(arr.length, dll.size());
            int j = arr.length - 1;
            for(var current : dll) {
                Assertions.assertEquals(arr[j], current);
                j--;
            }
            for(int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(arr.length - i, dll.size());
                int value = dll.getFirst();
                arr[i] = dll.removeFirst();
                Assertions.assertEquals(value, arr[i]);
            }
            Assertions.assertEquals(0, dll.size());
            Assertions.assertThrows(Exception.class, dll::getFirst);
            Assertions.assertThrows(Exception.class, dll::getLast);
            Assertions.assertThrows(Exception.class, dll::removeFirst);
            Assertions.assertThrows(Exception.class, dll::removeLast);
        };
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with dll front " + i, callback));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with dll front " + i, callback));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testDLinkedListBack() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            DLinkedList<Integer> dll = new DLinkedList<>();
            for(int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(i, dll.size());
                dll.addLast(arr[i]);
            }
            Assertions.assertEquals(arr.length, dll.size());
            int j = 0;
            for(var current : dll) {
                Assertions.assertEquals(arr[j], current);
                j++;
            }
            for(int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(arr.length - i, dll.size());
                int value = dll.getLast();
                arr[i] = dll.removeLast();
                Assertions.assertEquals(value, arr[i]);
            }
            Assertions.assertEquals(0, dll.size());
            Assertions.assertThrows(Exception.class, dll::getFirst);
            Assertions.assertThrows(Exception.class, dll::getLast);
            Assertions.assertThrows(Exception.class, dll::removeFirst);
            Assertions.assertThrows(Exception.class, dll::removeLast);
        };
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with dll back " + i, callback));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with dll back " + i, callback));
        }
        return result;
    }

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

    void testBTreeInsert(int[] arr, int maxDegree) {
        BTree<Integer, String> tree = new BTree<>(maxDegree, Integer::compareTo);
        java.util.HashMap<Integer, String> map = new java.util.HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            var value = UUID.randomUUID().toString();
            map.put(arr[i], value);
            tree.put(arr[i], value);
            Assertions.assertEquals(value, tree.get(arr[i]));
            tree.put(arr[i], "other value");
            Assertions.assertEquals("other value", tree.get(arr[i]));
            tree.put(arr[i], value);
            Assertions.assertEquals(value, tree.get(arr[i]));
        }

        for (var entry : map.entrySet()) {
            Assertions.assertEquals(entry.getValue(), tree.get(entry.getKey()));
        }

        BTree<String, Integer> treeStr = new BTree<>(maxDegree, String::compareTo);
        java.util.HashMap<String, Integer> mapStr = new java.util.HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            var value = UUID.randomUUID().toString();
            map.put(arr[i], value);
            treeStr.put(value, arr[i]);
            Assertions.assertEquals(arr[i], treeStr.get(value));
            treeStr.put(value, -1);
            Assertions.assertEquals(-1, treeStr.get(value));
            treeStr.put(value, arr[i]);
            Assertions.assertEquals(arr[i], treeStr.get(value));
        }

        for (var entry : mapStr.entrySet()) {
            Assertions.assertEquals(entry.getValue(), treeStr.get(entry.getKey()));
        }
    }

    @TestFactory
    Collection<DynamicTest> testBTreeInsert() {
        List<DynamicTest> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for(int j = 3; j < 100; j*=2) {
                final int maxDegree = j;
                result.add(TestUtils.genericArrayTest(i, "test btree nodes: " + i + ", maxDegree: " + j, (arr) -> testBTreeInsert(arr, maxDegree)));
            }
        }
        for (int i = 1000; i < 10000; i += 1000) {
            for(int j = 3; j < 100; j*=2) {
                final int maxDegree = j;
                result.add(TestUtils.genericArrayTest(i, "test btree nodes: " + i + ", maxDegree: " + j, (arr) -> testBTreeInsert(arr, maxDegree)));
            }
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testBTreeRemove() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            for(int j = 3; j < 100; j*=2) {
                BTree<Integer, String> tree = new BTree<>(j, Integer::compareTo);
                java.util.HashMap<Integer, String> map = new java.util.HashMap<>();
                for (int i = 0; i < arr.length; i++) {
                    var value = UUID.randomUUID().toString();
                    map.put(arr[i], value);
                    tree.put(arr[i], value);
                    Assertions.assertEquals(value, tree.get(arr[i]));
                    tree.put(arr[i], "other value");
                    Assertions.assertEquals("other value", tree.get(arr[i]));
                    tree.remove(arr[i]);
                    Assertions.assertNull(tree.get(arr[i]));
                    tree.put(arr[i], value);
                    Assertions.assertEquals(value, tree.get(arr[i]));
                }

                for (var entry : map.entrySet()) {
                    Assertions.assertEquals(entry.getValue(), tree.get(entry.getKey()));
                    tree.remove(entry.getKey());
                    Assertions.assertNull(tree.get(entry.getKey()));
                    tree.put(entry.getKey(), entry.getValue());
                    Assertions.assertEquals(entry.getValue(), tree.get(entry.getKey()));
                    tree.remove(entry.getKey());
                    Assertions.assertNull(tree.get(entry.getKey()));
                }

                for (var entry : map.entrySet()) {
                    Assertions.assertNull(tree.get(entry.getKey()));
                    tree.put(entry.getKey(), entry.getValue());
                    Assertions.assertEquals(entry.getValue(), tree.get(entry.getKey()));
                }
            }
        };
        for (int i = 0; i < 20; i++) {
            result.add(TestUtils.genericArrayTest(i, "test btree " + i, callback));
        }
        for (int i = 1000; i < 10000; i += 1000) {
            result.add(TestUtils.genericArrayTest(i, "test btree " + i, callback));
        }
        return result;
    }

    @Test
    public void testBloomFilter() throws NoSuchAlgorithmException {
        BloomFilter bloomFilter = null;
        try {
            bloomFilter = new BloomFilter(10, 1024 * 1024 * 1);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        HashSet<String> strings = new HashSet<>();
        for(int i = 0; i < 100000; i++) {
            String str = UUID.randomUUID().toString();
            Assertions.assertFalse(bloomFilter.mightExists(str));
            strings.add(str);
            bloomFilter.add(str);
            Assertions.assertTrue(bloomFilter.mightExists(str));
        }

        for(int i = 0; i < 100000; i++) {
            String str = UUID.randomUUID().toString();
            Assertions.assertFalse(bloomFilter.mightExists(str));
        }

        for(var str : strings) {
            Assertions.assertTrue(bloomFilter.mightExists(str));
        }
    }
}
