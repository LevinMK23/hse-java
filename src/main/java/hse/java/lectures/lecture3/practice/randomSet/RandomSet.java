package hse.java.lectures.lecture3.practice.randomSet;
import java.lang.reflect.Array;
import java.util.Random;

public class RandomSet<T extends Comparable<T>> {
    class Node {
        T key;
        int priority, index;
        Node l, r;

        Node(T key, int priority, int index) {
            this.key = key;
            this.priority = priority;
            this.index = index;
        }
    }

    class Pair {
        Node first, second;
        Pair(Node first, Node second) {
            this.first = first;
            this.second = second;
        }
    }

    class Element {
        T elem;
        Node node;
    }

    private final Random random = new Random();
    private Node root = null;
    private Element[] elements = (Element[]) Array.newInstance(Element.class, 16);
    private int size = 0;
    public RandomSet() {}

    public boolean insert(T value) {
        if (size == elements.length) {
            Element[] temp = (Element[]) Array.newInstance(Element.class, elements.length * 2);
            for (int i = 0; i < size; i++) temp[i] = elements[i];
            elements = temp;
        }

        Pair p1 = split(root, value, false);
        Pair p2 = split(p1.second, value, true);

        if (p2.first != null) {
            root = merge(p1.first, merge(p2.first, p2.second));
            return false;
        }

        Node node = new Node(value, random.nextInt(), size);
        elements[size] = new Element();
        elements[size].elem = value;
        elements[size].node = node;
        size++;

        root = merge(p1.first, merge(node, p2.second));
        return true;
    }

    public boolean remove(T value) {
        Pair p1 = split(root, value, false);
        Pair p2 = split(p1.second, value, true);

        if (p2.first == null) {
            root = merge(p1.first, p2.second);
            return false;
        }

        int removed_index = p2.first.index;
        root = merge(p1.first, p2.second);

        int last_index = size - 1;
        if (removed_index != last_index) {
            elements[removed_index] = elements[last_index];
            elements[removed_index].node.index = removed_index;
        }

        elements[last_index] = null;
        size--;
        return true;
    }

    public boolean contains(T value) {
        Node cur = root;
        while (cur != null) {
            int cmp = value.compareTo(cur.key);
            if (cmp < 0) cur = cur.l;
            else if (cmp > 0) cur = cur.r;
            else return true;
        }
        return false;
    }

    public T getRandom() {
        if (size == 0) throw new EmptySetException("Множество пусто");
        return elements[random.nextInt(size)].elem;
    }

    private Pair split(Node t, T key, boolean leq) {
        if (t == null) return new Pair(null, null);

        int cmp = t.key.compareTo(key);
        if (cmp < 0 || (leq && cmp == 0)) {
            Pair p = split(t.r, key, leq);
            t.r = p.first;
            return new Pair(t, p.second);
        }

        Pair p = split(t.l, key, leq);
        t.l = p.second;
        return new Pair(p.first, t);
    }

    private Node merge(Node l, Node r) {
        if (l == null || r == null) return l != null ? l : r;
        if (l.priority <= r.priority) {
            r.l = merge(l, r.l);
            return r;
        }

        l.r = merge(l.r, r);
        return l;
    }
}