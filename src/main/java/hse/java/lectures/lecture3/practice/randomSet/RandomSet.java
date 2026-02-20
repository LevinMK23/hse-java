package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T> {
    private T[] array = (T[]) new Object[64];
    private int size = 0;

    private Node<T>[] table;
    private int capacity = 256;
    private int tableSize = 0;

    Random random = new Random();

    private static class Node<T> {
        T key;
        int index;
        Node<T> next;
        Node(T key, int index, Node<T> next) {
            this.key = key;
            this.index = index;
            this.next = next;
        }
    }

    public RandomSet() {
        table = new Node[capacity];
    }

    private void add(T value) {
        if (array.length == size) {
            T[] newArray = (T[]) new Object[size*2];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
        array[size] = value;
        size += 1;
    }

    private int hash(T key) {
        int h = key.hashCode();
        if (h < 0) {
            h = -h;
        }
        return h % capacity;
    }

    private void resize() {
        capacity *= 2;
        Node<T>[] oldTable = table;
        table = new Node[capacity];
        tableSize = 0;

        for (Node<T> head : oldTable) {
            Node<T> Node = head;
            while (Node != null) {
                int pos = hash(Node.key);
                table[pos] = new Node<>(Node.key, Node.index, table[pos]);
                tableSize++;
                Node = Node.next;
            }
        }
    }

    public boolean insert(T value) {
        if (contains(value)) {
            return false;
        }
        else {
            add(value);
            int pos = hash(value);
            int indexInArray = size - 1;
            table[pos] = new Node<>(value, indexInArray, table[pos]);
            tableSize++;

            if (tableSize ==  capacity) {
                resize();
            }

            return true;
        }
    }

    public boolean remove(T value) {
        int pos = hash(value);
        Node<T> Node = table[pos];
        Node<T> prev = null;
        
        while (Node != null) {
            if (Node.key.equals(value)) {
                int indexToRemove = Node.index;
                int lastIndex = size - 1;

                if (lastIndex != indexToRemove) {
                    T lastElement = array[lastIndex];
                    array[indexToRemove] = lastElement;

                    int lastPos = hash(lastElement);
                    Node<T> elem = table[lastPos];

                    while (elem != null) {
                        if (elem.key.equals((lastElement))) {
                            elem.index = indexToRemove;
                            break;
                        }
                        elem = elem.next;
                    }
                }

                size--;

                if (prev == null) {
                    table[pos] = Node.next;
                }
                else {
                    prev.next = Node.next;
                }

                tableSize--;
                return true;
            }

            prev = Node;
            Node = Node.next;
        }

        return false;
    }

    public boolean contains(T value) {
        int pos = hash(value);
        Node<T> Node = table[pos];

        while (Node != null) {
            if (Node.key.equals(value)) {
                return true;
            }
            Node = Node.next;
        }

        return false;
    }

    public T getRandom() {
        if (size == 0) {
            throw new EmptySetException("You can't request random element from empty set!");
        }
        int randomPos = random.nextInt(size);
        return array[randomPos];
    }
}