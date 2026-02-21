package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T> {
    private final Random rnd = new Random();

    private static class Node{
        int value;
        int height;
        int size;
        Node left, right;

        Node (int v) {
            value = v;
            height = 1;
            size = 1;
        }
    }

    private Node root;

    private int getH(Node n) {
        return n == null ? 0 : n.height;
    }
    private int getS(Node n){
        return n == null ? 0 : n.size;
    }

    private void recount (Node n){
        if (n == null){
            return;
        }
        n.height = 1 + Math.max(getH(n.left), getH(n.right));
        n.size = 1 + getS(n.left) + getS(n.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        recount(y);
        recount(x);
        return x;
    }
    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        recount(x);
        recount(y);
        return y;
    }

    private Node rebalance(Node v) {
        if (v == null) {
            return null;
        }
        recount(v);

        int d = getH(v.left) - getH(v.right);
        if (d > 1) {
            if (getH(v.left.right) > getH(v.left.left)) {
                v.left = rotateLeft(v.left);
            }
            v = rotateRight(v);
        } else if (d < -1) {
            if (getH(v.right.left) > getH(v.right.right)) {
                v.right = rotateRight(v.right);
            }
            v = rotateLeft(v);
        }
        return v;
    }

    private Node insertNode(Node cur, int v) {
        if (cur == null) {
            return new Node(v);
        }

        if (v < cur.value) {
            cur.left = insertNode(cur.left, v);
        } else if (v > cur.value) {
            cur.right = insertNode(cur.right, v);
        }

        return rebalance(cur);
    }

    public boolean insert(T value) {
        if (contains(value)) {
            return false;
        }
        root = insertNode(root, (int)value);
        return true;
    }

    private Node removeNode(Node cur, int v) {
        if (cur == null) {
            return null;
        }

        if (v < cur.value) {
            cur.left = removeNode(cur.left, v);
        } else if (v > cur.value) {
            cur.right = removeNode(cur.right, v);
        } else {
            if (cur.left == null) {
                return cur.right;
            }
            if (cur.right == null) {
                return cur.left;
            }

            Node mn = cur.right;
            while (mn.left != null) {
                mn = mn.left;
            }

            cur.value = mn.value;
            cur.right = removeNode(cur.right, mn.value);
        }

        return rebalance(cur);
    }

    public boolean remove(T value) {
        if (!contains(value)) {
            return false;
        }
        root = removeNode(root, (int)value);
        return true;
    }

    public boolean contains(T value) {
        Node cur = root;
        while (cur != null) {
            if ((int)value < cur.value) {
                cur = cur.left;
            } else if ((int)value > cur.value) {
                cur = cur.right;
            } else{
                return true;
            }
        }
        return false;
    }

    public int getRandom() {
        if (root == null) {
            throw new EmptySetException("Set is empty");
        }

        int k = rnd.nextInt(root.size) + 1;
        Node cur = root;
        while (cur != null) {
            int leftSize = getS(cur.left);
            if (k <= leftSize) {
                cur = cur.left;
            } else if (k == leftSize + 1) {
                return cur.value;
            } else {
                k -= (leftSize + 1); cur = cur.right;
            }
        }

        throw new RuntimeException("Unexpected");
    }

}