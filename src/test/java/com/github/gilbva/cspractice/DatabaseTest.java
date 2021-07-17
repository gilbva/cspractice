package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.database.BTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.*;

public class DatabaseTest {

    void testBTreeInsert(int[] arr, int maxDegree) {
        BTree<Integer, String> tree = new BTree<>(maxDegree, Integer::compareTo);
        HashMap<Integer, String> map = new HashMap<>();
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
                HashMap<Integer, String> map = new HashMap<>();
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
}