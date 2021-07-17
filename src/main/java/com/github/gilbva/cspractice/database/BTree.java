package com.github.gilbva.cspractice.database;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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

public class BTree<K, V> {
    private Page<K, V> root;

    private Comparator<K> comparator;

    private int maxDegree;

    public BTree(int maxDegree, Comparator<K> comparator) {
        this.maxDegree = maxDegree;
        this.comparator = comparator;
        this.root = new Page<>(true, this.maxDegree);
    }

    public V get(K key) {
        var result = find(key, null);
        if(result.found) {
            return result.page.cells[result.index].value;
        }
        return null;
    }

    public void put(K key, V value) {
        var ancestors = new ArrayList<Node<K, V>>();
        var result = find(key, ancestors);
        if(result.found) {
            result.page.cells[result.index].value = value;
        }
        else {
            insert(result, new Cell<>(key, value));
            if(isFull(result.page)) {
                split(result.page, ancestors);
            }
        }
    }

    public void remove(K key) {
        var ancestors = new ArrayList<Node<K, V>>();
        var result = find(key, ancestors);
        if(result.found) {
            delete(result);
            if(ancestors.size() > 0 && isHalfEmpty(result.page)) {
                fill(result.page, ancestors);
            }
        }
    }

    private Result<K, V> find(K key, List<Node<K, V>> ancestors) {
        var current = root;
        while (!current.leaf) {
            var result = search(current, key);
            if(result.found) {
                return result;
            }
            if(ancestors != null) {
                ancestors.add(result);
            }
            current = current.children[result.index];
            if(current == null) {
                throw new IllegalStateException("Invalid child");
            }
        }

        return search(current, key);
    }

    private void insert(Node<K,V> node, Cell<K,V> cell) {
        var page = node.page;
        var index = node.index;
        insertPlace(page, index);
        page.cells[index] = cell;
    }

    private void delete(Node<K,V> node) {
        var page = node.page;
        var index = node.index;
        removePlace(page, index);
    }

    private Result<K, V> search(Page<K,V> current, K key) {
        int p = 0;
        int r = current.size - 1;

        while (p <= r) {
            int q = p + (r - p) / 2;
            if(Objects.equals(key, current.cells[q].key)) {
                return new Result<>(current, q, true);
            }

            if(comparator.compare(key, current.cells[q].key) <= 0) {
                r = q - 1;
            }
            else {
                p = q + 1;
            }
        }
        return new Result<>(current, p, false);
    }

    private void split(Page<K,V> source, List<Node<K, V>> ancestors) {
        Node<K, V> parentNode;
        if(ancestors.isEmpty()) {
            if(source != root) {
                throw new IllegalArgumentException("Ancestors were not provided.");
            }
            parentNode = grow();
        }
        else {
            parentNode = ancestors.remove(ancestors.size()-1);
        }

        int mid = source.size / 2;
        var target = new Page<K, V>(source.leaf, this.maxDegree);
        move(source, mid + 1, target);
        promoteLast(parentNode.page, parentNode.index, source);
        parentNode.page.children[parentNode.index] = source;
        parentNode.page.children[parentNode.index+1] = target;

        if(isFull(parentNode.page)) {
            split(parentNode.page, ancestors);
        }
    }

    private void fill(Page<K,V> page, List<Node<K, V>> ancestors) {
        if(!borrowLeft(page, ancestors) && !borrowRight(page, ancestors)) {
            if(!mergeLeft(page, ancestors)) {
                mergeRight(page, ancestors);
            }
        }
    }

    private Node<K,V> grow() {
        var newRoot = new Page<K, V>(false, this.maxDegree);
        newRoot.children[0] = root;
        root = newRoot;
        return new Node<>(root, 0);
    }

    private boolean borrowLeft(Page<K,V> target, List<Node<K,V>> ancestors) {
        var parentNode = ancestors.get(ancestors.size()-1);
        if(parentNode.index <= 0) {
            return false;
        }

        var source = parentNode.page.children[parentNode.index-1];
        if(!canBorrow(source)) {
            return false;
        }

        Page<K, V> preLastChild = null;
        Page<K, V> lastChild = null;
        var cell = source.cells[source.size-1];
        if(!source.leaf) {
            preLastChild = source.children[source.size - 1];
            lastChild = source.children[source.size];
        }
        delete(new Node<>(source, source.size-1));
        if(!source.leaf) {
            source.children[source.size] = preLastChild;
        }

        var parentCell = parentNode.page.cells[parentNode.index];
        parentNode.page.cells[parentNode.index] = cell;
        insert(new Node<>(target, 0), parentCell);
        if(!target.leaf) {
            target.children[0] = lastChild;
        }

        return true;
    }

    private boolean borrowRight(Page<K,V> target, List<Node<K,V>> ancestors) {
        var parentNode = ancestors.get(ancestors.size()-1);
        if(parentNode.index >= parentNode.page.size) {
            return false;
        }

        var source = parentNode.page.children[parentNode.index+1];
        if(!canBorrow(source)) {
            return false;
        }

        Page<K, V> firstChild = null;
        var cell = source.cells[0];
        if(!source.leaf) {
            firstChild = source.children[0];
        }
        delete(new Node<>(source, 0));

        var parentCell = parentNode.page.cells[parentNode.index];
        parentNode.page.cells[parentNode.index] = cell;

        insert(new Node<>(target, target.size), parentCell);
        if(!target.leaf) {
            target.children[target.size - 1] = target.children[target.size];
            target.children[target.size] = firstChild;
        }

        return true;
    }

    private boolean mergeLeft(Page<K,V> source, List<Node<K,V>> ancestors) {
        var parentNode = ancestors.get(ancestors.size()-1);
        if(parentNode.index <= 0) {
            return false;
        }

        var target = parentNode.page.children[parentNode.index-1];
        insert(new Node<>(target, target.size), parentNode.page.cells[parentNode.index-1]);
        if(!target.leaf) {
            target.children[target.size-1] = target.children[target.size];
        }

        delete(new Node<>(parentNode.page, parentNode.index-1));
        parentNode.page.children[parentNode.index-1] = target;

        for(int i = 0; i < source.size; i++) {
            insert(new Node<>(target, target.size), source.cells[i]);
            if(!target.leaf) {
                target.children[target.size - 1] = source.children[i];
            }
        }
        if(!target.leaf) {
            target.children[target.size] = source.children[source.size];
        }
        return true;
    }

    private boolean mergeRight(Page<K,V> source, List<Node<K,V>> ancestors) {
        var parentNode = ancestors.get(ancestors.size()-1);
        if(parentNode.index >= parentNode.page.size) {
            return false;
        }

        parentNode.index++;
        return mergeLeft(parentNode.page.children[parentNode.index], ancestors);
    }

    private boolean isFull(Page<K,V> page) {
        return page.size >= maxDegree;
    }

    private boolean canBorrow(Page<K,V> page) {
        return page.size > (maxDegree / 2);
    }

    private boolean isHalfEmpty(Page<K,V> page) {
        return page.size < (maxDegree / 2);
    }

    private void insertPlace(Page<K,V> page, int index) {
        page.size++;
        for(int i = page.size; i > index; i--) {
            if(!page.leaf) {
                page.children[i] = page.children[i-1];
            }
            if(i < page.size) {
                page.cells[i] = page.cells[i-1];
            }
        }
        if(!page.leaf) {
            page.children[index] = null;
        }
    }

    private void removePlace(Page<K,V> page, int index) {
        for(int i = index; i < page.size; i++) {
            if(!page.leaf) {
                page.children[i] = page.children[i+1];
            }
            if(i < page.size - 1) {
                page.cells[i] = page.cells[i+1];
            }
        }
        page.cells[page.size-1] = null;
        if(!page.leaf) {
            page.children[page.size] = null;
        }
        page.size--;
    }

    private void move(Page<K,V> source, int index, Page<K,V> target) {
        target.size = source.size - index;
        for(int i = 0; i <= target.size; i++) {
            if(i < target.size) {
                target.cells[i] = source.cells[index + i];
                source.cells[index + i] = null;
            }
            if(!target.leaf) {
                target.children[i] = source.children[index + i];
                source.children[index + i] = null;
            }
        }
        source.size -= target.size;
    }

    private void promoteLast(Page<K,V> parent, int i, Page<K,V> child) {
        insertPlace(parent, i);
        parent.cells[i] = child.cells[child.size-1];
        child.cells[child.size-1] = null;
        child.size--;
    }
}
