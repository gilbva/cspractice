package com.github.gilbva.cspractice.datastructures2;

import java.util.Comparator;
import java.util.Iterator;

public class TreeMap<K, V> implements Iterable<K> {
    public static class Entry<K, V> {
        public K getKey() {
            throw new UnsupportedOperationException();
        }

        public V getValue() {
            throw new UnsupportedOperationException();
        }
    }

    public TreeMap(Comparator<K> comparator) {
    }

    public void put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public V get(K key) {
        throw new UnsupportedOperationException();
    }

    public void remove(K key) {
        throw new UnsupportedOperationException();
    }

    public boolean exists(K key) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
