package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T> {

    private int size = 0;
    private T[] elements;
    private Node[] nodes;
    private final Random random = new Random();

    static private class Node {
        int index;
        Node next;

        public Node(int index) {
            this.index = index;
        }
    }

    @SuppressWarnings("unchecked")
    public RandomSet() {
        this.elements = (T[]) new Object[16];
        this.nodes = new Node[16];
    }

    public boolean insert(T value) {
        if (value == null) {
            return false;
        }
        if (contains(value)) {
            return false;
        }
        if (size >= elements.length * 0.7) {
            resize();
        }
        int nodeIndex = getNodeIndex(value);
        elements[size] = value;
        Node newNode = new Node(size++);
        nodes[nodeIndex] = putNode(nodes[nodeIndex], newNode);

        return true;
    }

    public boolean remove(T value) {
        if (value == null) return false;

        int bucketIndex = getNodeIndex(value);
        Node prev = null;
        Node current = nodes[bucketIndex];

        while (current != null) {
            if (elements[current.index].equals(value)) {

                int indexToRemove = current.index;
                int lastIndex = size - 1;

                if (indexToRemove != lastIndex) {
                    T lastElement = elements[lastIndex];

                    elements[indexToRemove] = lastElement;

                    updateNodeIndex(lastElement, lastIndex, indexToRemove);
                }

                elements[lastIndex] = null;

                if (prev == null) {
                    nodes[bucketIndex] = current.next;
                } else {
                    prev.next = current.next;
                }

                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    private void updateNodeIndex(T value, int oldIndex, int newIndex) {
        int bucketIndex = getNodeIndex(value);
        Node node = nodes[bucketIndex];
        while (node != null) {
            if (node.index == oldIndex) {
                node.index = newIndex;
                return;
            }
            node = node.next;
        }
    }

    public boolean contains(T value) {
        if (value == null) {
            return false;
        }
        int nodeIndex = getNodeIndex(value);
        Node node = nodes[nodeIndex];
        while (node != null) {
            if (elements[node.index].equals(value)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public T getRandom() {
        if (size == 0) {
            throw new EmptySetException("");
        }
        int randomIndex = random.nextInt(size);
        return elements[randomIndex];
    }

    private int getNodeIndex(T value) {
        return Math.abs(value.hashCode()) % nodes.length;
    }


    private Node putNode(Node currentHead, Node nodeToInsert) {
        nodeToInsert.next = currentHead;
        return nodeToInsert;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = elements.length * 2;

        T[] newElements = (T[]) new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;

        Node[] newNodes = new Node[newCapacity];

        for (Node node : nodes) {
            while (node != null) {
                Node next = node.next;

                T val = elements[node.index];

                int bucketIndex = Math.abs(val.hashCode()) % newCapacity;

                newNodes[bucketIndex] = putNode(newNodes[bucketIndex], node);

                node = next;
            }
        }
        nodes = newNodes;
    }
}
