package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.datastructures1.ArrayQueue;
import com.github.gilbva.cspractice.datastructures1.ArrayStack;
import com.github.gilbva.cspractice.datastructures1.DLinkedList;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStructures1Test {

    @TestFactory
    Collection<DynamicTest> testArrayQueue() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            ArrayStack<Integer> stack = new ArrayStack<>();
            for(int i = 0; i < arr.length; i++) {
                stack.push(arr[i]);
            }
            for(int i = 0; i < arr.length; i++) {
                arr[i] = stack.pop();
            }
        };
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with stack " + i, callback));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arraySortTest(i, "test revert array with stack " + i, callback));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testArrayStack() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            ArrayQueue<Integer> queue = new ArrayQueue<>();
            for(int i = 0; i < arr.length; i++) {
                queue.offer(arr[i]);
            }
            for(int i = arr.length - 1; i >= 0; i++) {
                arr[i] = queue.poll();
            }
        };
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with queue " + i, callback));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arraySortTest(i, "test revert array with queue " + i, callback));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testDLinkedListFront() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            DLinkedList<Integer> stack = new DLinkedList<>();
            for(int i = 0; i < arr.length; i++) {
                stack.addFirst(arr[i]);
            }
            for(int i = 0; i < arr.length; i++) {
                arr[i] = stack.removeFirst();
            }
        };
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with dll front " + i, callback));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arraySortTest(i, "test revert array with dll front " + i, callback));
        }
        return result;
    }

    @TestFactory
    Collection<DynamicTest> testDLinkedListBack() {
        List<DynamicTest> result = new ArrayList<>();
        IntArrayCallback callback = (arr) -> {
            DLinkedList<Integer> stack = new DLinkedList<>();
            for(int i = 0; i < arr.length; i++) {
                stack.addLast(arr[i]);
            }
            for(int i = 0; i < arr.length; i++) {
                arr[i] = stack.removeLast();
            }
        };
        for(int i = 0; i < 100; i++) {
            result.add(TestUtils.arrayRevertTest(i, "test revert array with dll back " + i, callback));
        }
        for(int i = 10_000; i < 1_000_000; i+=10_000) {
            result.add(TestUtils.arraySortTest(i, "test revert array with dll back " + i, callback));
        }
        return result;
    }
}
