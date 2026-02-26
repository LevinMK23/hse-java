package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T extends  Comparable<T>> {

    private int ElemNum = 0;
    private int ArraySize = 10000;
    private boolean isNewInserted = false;
    private boolean isFound = false;

    Node nodeFound;
    Node root = null;


    Object[] RandomSetArray = new Object[ArraySize];

    void copyArray(T elem) {
        Object[] CopyRandomSetArray = new Object[ArraySize * 2];
        for (int i = 0; i < ArraySize; i++) {
            CopyRandomSetArray[i] = RandomSetArray[i];
        }
        CopyRandomSetArray[ArraySize] = elem;
        RandomSetArray = CopyRandomSetArray;
        ArraySize *= 2;
    }



    public boolean insert(T value) {

        root = insertAVL(value, root);

        if (!isNewInserted) {
            return isNewInserted;
        }

        if (ArraySize <= ElemNum) {
            copyArray(value);
        } else {
            RandomSetArray[ElemNum] = value;
        }

        ElemNum++;

        return isNewInserted;
    }

    public Node insertAVL(T value, Node node) {
        if (node == null) {
            node = new Node();
            node.key = value;
            node.index = ElemNum;

            isNewInserted = true;

            return node;
        }

        if (node.key.equals(value)) {
            isNewInserted =  false;
            return node;

        } else if (node.key.compareTo(value) > 0) {
            node.left = insertAVL(value, node.left);
            node = node.Rebalance(node);
            return node;
        } else {
            node.right = insertAVL(value, node.right);
            node = node.Rebalance(node);
            return node;
        }
    }

    public boolean remove(T value) {
        if (!contains(value)) {
            return false;
        } else {
            int index = nodeFound.index;

            root = removeAVL(root, value);

            if (index != ElemNum - 1) {
                RandomSetArray[index] = RandomSetArray[ElemNum - 1];
                contains((T) RandomSetArray[index]);
                nodeFound.index = index;
            }

            ElemNum--;
            return true;
        }
    }

    public Node removeAVL(Node node, T value) {
        if (node == null) {
            return null;
        }

        if (node.key.compareTo(value) > 0) {
            node.left = removeAVL(node.left, value);
        } else if (node.key.compareTo(value) < 0) {
            node.right =removeAVL(node.right, value);
        } else  {
            if (node.right == null && node.left == null) {
                return null;
            } else if (node.right != null && node.left == null) {
                return node.right;
            } else if (node.right == null && node.left != null) {
                return node.left;
            } else {
                Node minLeft  = node.findMin(node.right);
                node.key = minLeft.key;
                node.index = minLeft.index;

                node.right = removeAVL(node.right, minLeft.key);
            }
        }
        node = node.Rebalance(node);
        return node;
    }

    public boolean contains(T value) {
        nodeFound = containsAVL(root, value);
        return isFound;
    }

    public Node containsAVL(Node node, T value) {
        if (node == null) {
            isFound = false;
            return node;
        } else if (node.key.equals(value)) {
            isFound = true;
            return node;
        } else if (node.key.compareTo(value) < 0) {
            return containsAVL(node.right, value);
        } else {
            return containsAVL(node.left, value);
        }
    }

    public T getRandom() {
        if (ElemNum == 0) {
            throw new EmptySetException("Empty Set");
        }

        Random rand = new Random();
        int index = rand.nextInt(ElemNum);
        return (T) RandomSetArray[index];
    }

    // AVL
    class Node {
        T key;
        int index;
        Node left, right = null;
        int height = 1;

        private int getHeight(Node node) {
            if (node == null) {
                return 0;
            } else {
                return node.height;
            }
        }

        private void updateHeight(Node node) {
                if (node == null) return;
                node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        }

        private int getBalance(Node node) {
            return getHeight(node.right) - getHeight(node.left);
        }

        private Node RotateLeft(Node node) {
            Node newRoot = node.right;
            node.right = node.right.left;
            newRoot.left = node;

            updateHeight(node);
            updateHeight(newRoot);

            return newRoot;
        }

        private Node RotateRight(Node node) {
            Node newRoot = node.left;
            node.left = node.left.right;
            newRoot.right = node;

            updateHeight(node);
            updateHeight(newRoot);

            return newRoot;
        }

        private Node Rebalance(Node node) {
            updateHeight(node);
            int balance = getBalance(node);

            if (balance >= 2) { // перевес справа
                int rightBalance = getBalance(node.right);
                if (rightBalance < 0) {
                    node.right = RotateRight(node.right);
                    node = RotateLeft(node);
                } else {
                    node = RotateLeft(node);
                }

            } else if (balance <= -2) { // перевес слева
                int rightBalance = getBalance(node.left);
                if (rightBalance > 0) {
                    node.left = RotateLeft(node.left);
                    node = RotateRight(node);
                } else {
                    node = RotateRight(node);
                }
            }

            return node;
        }

        private Node findMin(Node node) {
            if (node == null) {
                return null;
            }

            if (node.left == null) {
                return node;
            } else {
                return findMin(node.left);
            }
        }
    }
}
