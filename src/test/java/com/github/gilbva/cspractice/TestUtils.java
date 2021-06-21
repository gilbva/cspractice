package com.github.gilbva.cspractice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import java.util.*;

@FunctionalInterface
interface IntArrayCallback {
    void modify(int[] arr);
}

@FunctionalInterface
interface KeySizeIntArrayCallback {
    void modify(int[] arr, int keySize);
}

public class TestUtils {
    public static int[] randomArray(int n) {
        int[] arr = new int[n];
        Random random = new Random();
        for(int i = 0; i < n; i++) {
            arr[i] = random.nextInt();
        }
        return arr;
    }

    public static int[] randomArrayKeySize(int n, int keySize) {
        int[] arr = new int[n];
        Random random = new Random();
        for(int i = 0; i < n; i++) {
            arr[i] = random.nextInt(keySize);
        }
        return arr;
    }

    public static int[] linealArray(int n) {
        int[] arr = new int[n];
        for(int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    public static int[] revertedLinealArray(int n) {
        int[] arr = new int[n];
        for(int i = 0; i < n; i++) {
            arr[i] = n - i;
        }
        return arr;
    }

    public static DynamicTest arraySortTest(int size, String title, IntArrayCallback callback) {
        int[] arr = randomArray(size);
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);
        return DynamicTest.dynamicTest(title, () -> {
            callback.modify(arr);
            Assertions.assertArrayEquals(expected, arr);
        });
    }

    public static DynamicTest arraySortKeySizeTest(int size, int keySize, String title, KeySizeIntArrayCallback callback) {
        int[] arr = randomArrayKeySize(size, keySize);
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);
        return DynamicTest.dynamicTest(title, () -> {
            callback.modify(arr, keySize);
            Assertions.assertArrayEquals(expected, arr);
        });
    }

    public static DynamicTest arrayRevertTest(int size, String title, IntArrayCallback callback) {
        int[] arr = linealArray(size);
        int[] expected = revertedLinealArray(size);
        return DynamicTest.dynamicTest(title, () -> {
            callback.modify(arr);
            Assertions.assertArrayEquals(expected, arr);
        });
    }

    public static DynamicTest genericArrayTest(int size, String title, IntArrayCallback callback) {
        int[] arr = linealArray(size);
        return DynamicTest.dynamicTest(title, () -> {
            callback.modify(arr);
        });
    }
}
