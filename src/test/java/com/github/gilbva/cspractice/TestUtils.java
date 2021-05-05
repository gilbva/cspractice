package com.github.gilbva.cspractice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import java.util.*;

@FunctionalInterface
interface SortArrayCallback {
    void sortArray(int[] arr);
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

    public static int[] linealArray(int n) {
        int[] arr = new int[n];
        for(int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    public static int[] invertedLinealArray(int n) {
        int[] arr = new int[n];
        for(int i = 0; i < n; i++) {
            arr[i] = n - i;
        }
        return arr;
    }

    public static DynamicTest arraySortTest(int size, String title, SortArrayCallback callback) {
        int[] arr = TestUtils.randomArray(size);
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);
        return DynamicTest.dynamicTest(title, () -> {
            callback.sortArray(arr);
            Assertions.assertArrayEquals(expected, arr);
        });
    }
}
