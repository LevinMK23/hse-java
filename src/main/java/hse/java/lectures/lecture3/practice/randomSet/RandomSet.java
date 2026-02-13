package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T> {
    private static class Node<T> {
        T key;
        int index;

        Node(T key, int index) {
            this.key = key;
            this.index = index;
        }
    }

    private final Node<T> DELETED = new Node<>(null, -1);

    private final Random random = new Random();

    private Node<T>[] table;
    private Object[] values;
    private int size = 0;
    private int capacity = 4;
    private double loadFactor = 0.5;

    public RandomSet() {
        table = new Node[capacity];
        values = new Object[2];
    }

    private int hash(T key) {
        return key.hashCode() % capacity();
    }

    private void rehash() {
        Node<T>[] oldTable = table;
        int oldCapacity = capacity;

        capacity *= 2;
        table = new Node[capacity];
        
        for (int i = 0; i < oldCapacity; i++) {
            Node<T> node = oldTable[i];
            if (node != null && node != DELETED) {
                T key = node.key;
                int idx = key.hashCode() % capacity;
                while (table[idx] != null) {
                    idx = (idx + 1) % capacity;
                }
                table[idx] = new Node<>(key, node.index);
            }
        }
    }

    private int index(T key) {
        int index = hash(key);
        for (int t = 0; t < capacity(); t++) {
            Node<T> entry = table[index];
            if (entry == null) {
                return -1;
            }
            if (entry != DELETED && entry.key.equals(key)) {
                return index;
            }
            index = (index + 1) % capacity();
        }
        return -1;
    }

    private void updateElements() {
        if (size < values.length) {
            return;
        }
        Object[] newElements = new Object[values.length * 2];
        System.arraycopy(values, 0, newElements, 0, values.length);
        values = newElements;
    }

    public boolean insert(T value) {
        if (size >= capacity() * loadFactor) {
            rehash();
        }

        if (index(value) != -1) {
            return false;
        }

        int i = hash(value);
        while (table[i] != null && table[i] != DELETED) {
            i = (i + 1) % capacity();
        }

        updateElements();
        int newIndex = size;
        values[size++] = value;

        table[i] = new Node<>(value, newIndex);
        return true;
    }

    public boolean remove(T value) {
        int pos = index(value);
        if (pos == -1) {
            return false;
        }

        int oldIndex = table[pos].index;
        table[pos] = (Node<T>) DELETED;

        if (oldIndex != size - 1) {
            T last = (T) values[size - 1];
            values[oldIndex] = last;
            int lastPos = index(last);
            table[lastPos].index = oldIndex;
        }

        size--;
        values[size] = null;
        return true;
    }

    public boolean contains(T value) {
        return index(value) != -1;
    }

    public T getRandom() {
        if (size == 0) {
            throw new EmptySetException("set is empty");
        }
        int index = random.nextInt(size);
        return (T) values[index];
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }
}
