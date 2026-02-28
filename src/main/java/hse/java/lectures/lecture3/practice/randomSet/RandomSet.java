package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Objects;
import java.util.Random;

public class RandomSet<T extends Number> {
    private static class Node<T> {
        T value;
        int index;          
        Node<T> next;

        Node(T value, int index, Node<T> next) {
            this.value = value;
            this.index = index;
            this.next = next;
        }
    }
    private static final int CAPACITY = 100;
    private static final int PRIME = 15_485_863;

    private Object[] elements;              
    private Node<T>[] heads;            
    private int len;                     
    private final Random random = new Random();

    @SuppressWarnings("unchecked")
    public RandomSet() {
        elements = new Object[CAPACITY];
        heads = new Node[CAPACITY];
        len = 0;
    }

    private int get_hash(T value) {
        return (value == null ) ? 0 : Math.abs((int) value.longValue() * PRIME) % heads.length;
    }

    private Node<T> findNode(T value) {
        int h = get_hash(value);
        Node<T> cur = heads[h];
        while (cur != null) {
            if (Objects.equals(value, cur.value)) {
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public boolean insert(T value) {
        if (findNode(value) != null) {
            return false;
        }

        if (len == elements.length) {
            Object[] newData = new Object[elements.length * 2];
            for (int i = 0; i < elements.length; i++) {
                newData[i] = elements[i];
            }
            elements = newData;
        }

        elements[len] = value;

        int h = get_hash(value);
        heads[h] = new Node<>(value, len, heads[h]);

        len++;

        if (len > heads.length * 0.75) {
            Node<T>[] oldHeads = heads;
            heads = new Node[oldHeads.length * 2];
            for (Node<T> head : oldHeads) {
                Node<T> cur = head;
                while (cur != null) {
                    Node<T> next = cur.next;
                    int newHash = get_hash(cur.value);
                    cur.next = heads[newHash];
                    heads[newHash] = cur;
                    cur = next;
                }
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean remove(T value) {
        Node<T> node = findNode(value);
        if (node == null) {
            return false;
        }

        int ind = node.index;

        int h = get_hash(value);
        Node<T> cur = heads[h];
        Node<T> prev = null;
        while (cur != null) {
            if (cur == node) {
                if (prev == null) {
                    heads[h] = cur.next;
                } else {
                    prev.next = cur.next;
                }
                break;
            }
            prev = cur;
            cur = cur.next;
        }
        
        if (ind != len - 1) {
            T lastValue = (T) elements[len - 1];
            elements[ind] = lastValue;

            Node<T> lastNode = findNode(lastValue);
            if (lastNode != null) {
                lastNode.index = ind;
            }
        }

        len--;

        return true;
    }

    public boolean contains(T value) {
        return findNode(value) != null;
    }
    
    @SuppressWarnings("unchecked")
    public T getRandom() {
        if (len == 0) {
            throw new EmptySetException("No elements in the random set");
        }
        int randomIndex = random.nextInt(len);
        return (T) elements[randomIndex];
    }
}