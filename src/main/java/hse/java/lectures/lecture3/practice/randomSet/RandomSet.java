package hse.java.lectures.lecture3.practice.randomSet;

import static java.lang.Math.max;

public class RandomSet<T extends Comparable<T>> {
    Node<T> root;

    RandomSet() {
        root = new Node<>();
    }

    private boolean isEmpty() {
        return root.right == null;
    }

    private Node<T> find(T value) {
        Node<T> current = root.right;
        while(current != null) {
            int cmp = current.value.compareTo(value);
            if (cmp == 0) {
                return current;
            }
            else if (cmp < 0) {
                current = current.right;
            }
            else {
                current = current.left;
            }
        }
        return null;
    }

    private Node<T> next(Node<T> node) {
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        else {
            while (node.parent != null && node.parent.right == node) {
                node = node.parent;
            }
            return node.parent;
        }
    }

    private void update(Node<T> v) {
        if (v.right != null && v.left != null) {
            v.height = max(v.right.height, v.left.height) + 1;
        }
        else if (v.right != null) {
            v.height = v.right.height + 1;
        }
        else {
            v.height = v.left.height + 1;
        }
    }

    private Node<T> add(Node<T> v, T value, Node<T> parent) {
        if (v == null) {
            return new Node<>(value, parent, 1);
        }
        int cmp = v.value.compareTo(value);
        if (cmp > 0) {
            v.left = add(v.left, value, v);
        }
        else {
            v.right = add(v.right, value, v);
        }
        update(v);
        return v;
    }

    private Node<T> del(Node<T> v, T value) {
        if (v == null) {
            return v;
        }
        int cmp = v.value.compareTo(value);
        if(cmp > 0) {
            v.left = del(v.left, value);
        }
        else if (cmp < 0) {
            v.right = del(v.right, value);
        }
        else if (v.left != null && v.right != null) {
            Node<T> nextNode = next(v);
            v.value = nextNode.value;
            v.right = del(v.right, v.value);
        }
        else {
            if (v.left != null) {
                v = v.left;
            }
            else if (v.right != null) {
                v = v.right;
            }
            else {
                v = null;
            }
        }
        return v;
    }

    public boolean insert(T value) {
        if (this.isEmpty()) {
            // первая инициализация
            root.right = new Node<>(value, root, 1);
            return true;
        }
        if (this.contains(value)) {
            return false;
        }
        add(root.right, value, root);
        return true;
    }

    private boolean isLeftChildForParent(Node<T> node) {
        return node.parent.left == node;
    }

    public boolean remove(T value) {
        if (this.contains(value)) {
            del(root.right, value);
            return true;
        }
        return false;
    }

    public boolean contains(T value) {
        return this.find(value) != null;
    }

    public T getRandom() {
        if (this.isEmpty()) {
            throw new EmptySetException("Empty set");
        }
        return root.right.value;
    }

}
