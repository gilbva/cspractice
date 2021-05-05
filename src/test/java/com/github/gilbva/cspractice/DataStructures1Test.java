package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.datastructures1.ArrayQueue;
import com.github.gilbva.cspractice.datastructures1.ArrayStack;
import com.github.gilbva.cspractice.datastructures1.DLinkedList;
import org.junit.jupiter.api.Assertions;
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
                Assertions.assertEquals(i, queue.size());
                queue.offer(arr[i]);
            }
            Assertions.assertEquals(arr.length, queue.size());
            for(int i = arr.length - 1; i >= 0; i++) {
                Assertions.assertEquals(arr.length - i, queue.size());
                int value = queue.peek();
                arr[i] = queue.poll();
                Assertions.assertEquals(value, arr[i]);
            }
            Assertions.assertEquals(0, queue.size());
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
            DLinkedList<Integer> dll = new DLinkedList<>();
            for(int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(i, dll.size());
                dll.addFirst(arr[i]);
            }
            Assertions.assertEquals(arr.length, dll.size());
            for(int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(arr.length - i, dll.size());
                int value = dll.getFirst();
                arr[i] = dll.removeFirst();
                Assertions.assertEquals(value, arr[i]);
            }
            Assertions.assertEquals(0, dll.size());
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
            DLinkedList<Integer> dll = new DLinkedList<>();
            for(int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(i, dll.size());
                dll.addLast(arr[i]);
            }
            Assertions.assertEquals(arr.length, dll.size());
            for(int i = 0; i < arr.length; i++) {
                Assertions.assertEquals(arr.length - i, dll.size());
                int value = dll.getLast();
                arr[i] = dll.removeLast();
                Assertions.assertEquals(value, arr[i]);
            }
            Assertions.assertEquals(0, dll.size());
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
