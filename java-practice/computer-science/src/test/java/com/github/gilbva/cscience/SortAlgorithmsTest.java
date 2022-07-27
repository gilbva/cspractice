package com.github.gilbva.cscience;

import com.github.gilbva.cscience.algorithms.*;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.*;

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
    Collection<DynamicTest> testQuickSelect() {
        List<DynamicTest> result = new ArrayList<>();
        for(int i = 1; i < 100; i++) {
            for(int j = 1; j < i; j++) {
                result.add(TestUtils.selectKthSmallestTest(i, j,"test quick select " + i + " with " + j + "th", QuickSelect::quickSelect));
            }
        }
        Random random = new Random();
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            int j = random.nextInt(i);
            result.add(TestUtils.selectKthSmallestTest(i, j,"test quick select " + i + " with " + j + "th", QuickSelect::quickSelect));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testHeapSort() {
        List<DynamicTest> result = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arraySortTest(i, "test heap sort " + i, HeapSort::heapSort));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arraySortTest(i,"test heap sort " + i, HeapSort::heapSort));
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
