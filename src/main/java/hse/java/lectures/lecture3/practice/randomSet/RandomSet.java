package hse.java.lectures.lecture3.practice.randomSet;

public class RandomSet<T extends Comparable<T>> {
    Node<T> root;

    RandomSet() {
        root = new Node<>();
    }


    private boolean isEmpty() {
        return root.right == null;
    }

    private Node<T> find(T value) {
        if (this.isEmpty()) {
            return null;
        }
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
        // next вызывается тогда, когда 2 ребенка есть
        Node<T> current = node.right;
        while(current.left != null) {
            current = current.left;
        }
        return current;
    }

    public boolean insert(T value) {
        if (this.isEmpty()) {
            // первая инициализация
            root.right = new Node<>(value, root, 1);
            root.value = value;
            return true;
        }
        Node<T> current = root.right;
        if (this.contains(value)) {
            return false;
        }
        Node<T> parent = current;
        while (current != null) {
            parent = current;
            int cmp = current.value.compareTo(value);
            if (cmp > 0) {
                current = current.left;
            }
            else if (cmp < 0) {
                current = current.right;
            }
        }
        current = new Node<>(value, parent, parent.height + 1);
        int cmp = parent.value.compareTo(value);
        if (cmp > 0) {
            parent.left = current;
        }
        else if (cmp < 0) {
            parent.right = current;
        }
        return true;
    }

    private boolean isLeftChildForParent(Node<T> node) {
        return node.parent.left == node;
    }

    public boolean remove(T value) {
        if (this.isEmpty()) {
            return false;
        }
        Node<T> current = this.find(value);
        if (current == null) {
            return false;
        }
        else if (current.left == null && current.right == null) { // нет детей
            if (this.isLeftChildForParent(current)) {
                current.parent.left = null;
            }
            else {
                current.parent.right = null;
            }
        }
        else if (current.left != null && current.right != null) { // оба ребенка на месте
            Node<T> nextNode = next(current);
            current.value = nextNode.value;
            nextNode.parent.left = null; // удалили next(current)
        }
        else {
            if (current.left != null) {
                if (this.isLeftChildForParent(current)) {
                    current.parent.left = current.left;
                }
                else {
                    current.parent.right = current.left;
                }
            }
            else {
                if (this.isLeftChildForParent(current)) {
                    current.parent.left = current.right;
                }
                else {
                    current.parent.right = current.right;
                }
            }
        }
        return true;
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
