package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.sorting.InsertionSort;
import com.github.gilbva.cspractice.sorting.MergeSort;
import com.github.gilbva.cspractice.sorting.QuickSort;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SortAlgorithmsTest {

    @TestFactory
    Collection<DynamicTest> testInsertionSort() {
        List<DynamicTest> result = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arraySortTest(i, "test insertion sort " + i, InsertionSort::insertionSort));
        }
        for(int i = 10_000; i <= 20_000; i+=10_000) {
            result.add(TestUtils.arraySortTest(i, "test insertion sort " + i, InsertionSort::insertionSort));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testMergeSort() {
        List<DynamicTest> result = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arraySortTest(i, "test merge sort " + i, MergeSort::mergeSort));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arraySortTest(i, "test merge sort " + i, MergeSort::mergeSort));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testQuickSort() {
        List<DynamicTest> result = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arraySortTest(i, "test quick sort " + i, QuickSort::quickSort));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arraySortTest(i, "test quick sort " + i, QuickSort::quickSort));
        }
        return result;
    }
}
