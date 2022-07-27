package com.github.gilbva.cscience.datastructures;

import java.util.*;

class Result<K, V> extends Node<K, V> {
    boolean found;

    public Result(Page<K, V> page, int index, boolean found) {
        super(page, index);
        this.found = found;
    }
}

class Node<K, V> {
    Page<K, V> page;
    int index;

    public Node(Page<K, V> page, int index) {
        this.page = page;
        this.index = index;
    }
}

class Cell<K, V> {
    K key;
    V value;

    public Cell(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

class Page<K, V> {
    int size;
    boolean leaf;
    Cell<K, V>[] cells;
    Page<K, V>[] children;

    public Page(boolean leaf, int maxDegree) {
        this.leaf = leaf;
        this.cells = new Cell[maxDegree + 1];
        if(!this.leaf) {
            this.children = new Page[maxDegree + 2];
        }
    }
}

public class BTree<K, V> implements Iterable<K> {
    private Page<K, V> root;

    private Comparator<K> comparator;

    private int maxDegree;

    public BTree(int maxDegree, Comparator<K> comparator) {
        this.maxDegree = maxDegree;
        this.comparator = comparator;
        this.root = new Page<>(true, this.maxDegree);
    }

    public V get(K key) {
        throw new UnsupportedOperationException();
    }

    public void put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public void remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
