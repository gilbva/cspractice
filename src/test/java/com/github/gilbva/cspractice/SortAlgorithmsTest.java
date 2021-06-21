package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.sorting.*;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SortAlgorithmsTest {

    @TestFactory
    Collection<DynamicTest> testBubbleSort() {
        List<DynamicTest> result = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arraySortTest(i, "test bubble sort " + i, BubbleSort::bubbleSort));
        }
        for(int i = 10_000; i <= 20_000; i+=10_000) {
            result.add(TestUtils.arraySortTest(i, "test bubble sort " + i, BubbleSort::bubbleSort));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testSelectionSort() {
        List<DynamicTest> result = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arraySortTest(i, "test selection sort " + i, SelectionSort::selectionSort));
        }
        for(int i = 10_000; i <= 20_000; i+=10_000) {
            result.add(TestUtils.arraySortTest(i, "test selection sort " + i, SelectionSort::selectionSort));
        }
        return result;
    }

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

    @TestFactory
    Collection<DynamicTest> testCountingSort() {
        List<DynamicTest> result = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arraySortKeySizeTest(i, 100, "test counting sort " + i, CountingSort::countingSort));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arraySortKeySizeTest(i, 1000, "test counting sort " + i, CountingSort::countingSort));
        }
        return result;
    }
}
