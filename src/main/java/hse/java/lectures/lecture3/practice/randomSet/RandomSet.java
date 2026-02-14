package hse.java.lectures.lecture3.practice.randomSet;

import static java.lang.Math.max;
import java.util.Random;

public class RandomSet<T extends Comparable<T>> {
    Node<T> root;

    RandomSet() {
        root = new Node<>();
    }

    private boolean isEmpty() {
        return root.value == null;
    }

    private Node<T> find(T value) {
        if (this.isEmpty()) {
            return null;
        }
        Node<T> current = root;
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
        if (node.left != null) {
            return next(node.left);
        }
        return node;
    }

    private void update(Node<T> v) {
        if (v.right != null && v.left != null) {
            v.height = max(v.right.height, v.left.height) + 1;
        }
        else if (v.right != null) {
            v.height = v.right.height + 1;
        }
        else if (v.left != null ){
            v.height = v.left.height + 1;
        }
    }

    private Node<T> rotateRight(Node<T> p) {
        Node<T> q = p.left;
        p.left = q.right;
        q.right = p;
        update(p);
        update(q);
        return q;
    }

    private Node<T> rotateLeft(Node<T> p) {
        Node<T> q = p.right;
        p.right = q.left;
        q.left = p;
        update(p);
        update(q);
        return q;
    }

    private int getHeight(Node<T> p) {
        if(p != null) {
            return p.height;
        }
        return 0;
    }

    private int getBalanceFactor(Node<T> p){
        return getHeight(p.right) - getHeight(p.left);
    }

    private Node<T> rebalance(Node<T> p) {
        if (p == null) {
            return p;
        }
        update(p);
        if (getBalanceFactor(p) == 2) {
            if (getBalanceFactor(p.right) < 0) {
                p.right = rotateRight(p.right);
            }
            return rotateLeft(p);
        }
        if (getBalanceFactor(p) == -2) {
            if(getBalanceFactor(p.left) > 0) {
                p.left = rotateLeft(p.left);
            }
            return rotateRight(p);
        }
        return p;
    }

    private Node<T> add(Node<T> v, T value) {
        if (v == null) {
            return new Node<>(value, 1);
        }
        int cmp = v.value.compareTo(value);
        if (cmp > 0) {
            v.left = add(v.left, value);
        }
        else {
            v.right = add(v.right, value);
        }
        return rebalance(v);
    }

    public boolean insert(T value) {
        if (this.isEmpty()) {
            root = new Node<>(value, 1);
            return true;
        }
        if (this.contains(value)) {
            return false;
        }
        add(root, value);
        return true;
    }

    private Node<T> del(Node<T> v, T value) {
        if (v == null) {
            return v;
        }
        int cmp = v.value.compareTo(value);
        if (cmp > 0) {
            v.left = del(v.left, value);
            update(v);
        }
        else if (cmp < 0) {
            v.right = del(v.right, value);
            update(v);
        }
        else {
            if (v.right == null) {
                v = v.left;

            }
            else if (v.left == null) {
                v = v.right;
            }
            else {
                Node<T> temp = next(v.right);
                v.value = temp.value;
                v.right = del(v.right, v.value);
            }
        }
        if (v == null) {
            return v;
        }
        else {
            return rebalance(v);
        }
    }

    public boolean remove(T value) {
        if (this.contains(value)) {
            root = del(root, value);
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
        Random randomNum = new Random();
        int depth = randomNum.nextInt(getHeight(root));
        Node<T> current = root;
        for(int i = 0; i < depth; i++) {
            int direction = randomNum.nextInt(2);
            if (direction == 0) {
                if (current.left != null) {
                    current = current.left;
                }
                else {
                    break;
                }
            }
            else {
                if (current.right != null) {
                    current = current.right;
                }
                else {
                    break;
                }
            }
        }
        return current.value;
    }

}
