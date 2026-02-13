package hse.java.lectures.lecture3.practice.randomSet;

public class Node<T extends Comparable<T>> {
    public T value;
    public Node<T> parent;
    public Node<T> left;
    public Node<T> right;
    public int height;

    Node() {
        value = null;
        parent = null;
        left = null;
        right = null;
        height = 0;
    }
    Node(T value_, Node<T> parent_, int height_) {
        this.value = value_;
        this.parent = parent_;
        left = null;
        right = null;
        this.height = height_;
    }
}