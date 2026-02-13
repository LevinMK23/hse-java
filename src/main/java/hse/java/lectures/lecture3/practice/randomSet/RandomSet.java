package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T extends Comparable<T>> {
    private Node<T> root;
    private final Random random = new Random();

    private boolean find(Node<T> node, T value) {
        if (node == null) {
            return false;
        }
        int compareResult = node.getValue().compareTo(value);
        if (compareResult == 0) {
            return true;
        }
        if (compareResult > 0) {
            return find(node.getLeft(), value);
        }
        return find(node.getRight(), value);
    }

    private Pair<Node<T>> split(Node<T> currentNode, T value) {
        if (currentNode == null) {
            return new Pair<>(null, null);
        }
        int compareResult = currentNode.getValue().compareTo(value);
        if (compareResult == 0) {
            return new Pair<>(currentNode.getLeft(), currentNode.getRight());
        } else if (compareResult < 0) {
            var splitResult = split(currentNode.getRight(), value);
            currentNode.setRight(splitResult.left());
            currentNode.recalculateSize();
            return new Pair<>(currentNode, splitResult.right());
        } else {
            var splitResult = split(currentNode.getLeft(), value);
            currentNode.setLeft(splitResult.right());
            currentNode.recalculateSize();
            return new Pair<>(splitResult.left(), currentNode);
        }
    }

    private Node<T> merge(Node<T> leftNode, Node<T> rightNode) {
        if (leftNode == null) {
            return rightNode;
        }
        if (rightNode == null) {
            return leftNode;
        }
        if (random.nextInt(leftNode.getSize() + rightNode.getSize()) < leftNode.getSize()) {
            leftNode.setRight(merge(leftNode.getRight(), rightNode));
            leftNode.recalculateSize();
            return leftNode;
        } else {
            rightNode.setLeft(merge(leftNode, rightNode.getLeft()));
            rightNode.recalculateSize();
            return rightNode;
        }
    }

    private Node<T> getRandomNode(Node<T> currentNode) {
        int leftSize = currentNode.getLeft() != null ? currentNode.getLeft().getSize() : 0;
        int rightSize = currentNode.getRight() != null ? currentNode.getRight().getSize() : 0;
        int totalSize = leftSize + rightSize + 1;
        int randomIndex = random.nextInt(totalSize);
        if (randomIndex < leftSize) {
            return getRandomNode(currentNode.getLeft());
        } else if (randomIndex == leftSize) {
            return currentNode;
        } else {
            return getRandomNode(currentNode.getRight());
        }
    }

    public RandomSet() {
        root = null;
    }

    public boolean insert(T value) {
        if (contains(value)) {
            return false;
        }
        var splitResult = split(root, value);
        root = merge(merge(splitResult.left(), new Node<>(value)), splitResult.right());
        return true;
    }

    public boolean remove(T value) {
        if (!contains(value)) {
            return false;
        }
        var splitResult = split(root, value);
        root = merge(splitResult.left(), splitResult.right());
        return true;
    }

    public boolean contains(T value) {
        return find(root, value);
    }

    public T getRandom() {
        if (root == null) {
            throw new EmptySetException("Set is empty");
        }
        return getRandomNode(root).getValue();
    }
}
