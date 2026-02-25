package hse.java.lectures.lecture3.practice.randomSet;
import java.util.Random;

public class RandomSet<T> {
    private T[] objects;
    private int size;
    private int capacity;


    private static class HashNode<T> {
        T key; // значение элемента
        int value; // индекс в массиве
        HashNode<T> next;

        HashNode(T key_, int val_) {
            value = val_;
            key = key_;
            next = null;
        }
    }

    private HashNode<T>[] buckets;
    private int cnt_bucket;

    public RandomSet() {
        capacity = 256;
        size = 0;
        objects = (T[]) new Object[capacity];

        cnt_bucket = 256;
        buckets = (HashNode<T>[]) new HashNode[cnt_bucket];
    }

    private void extend_array() {
        capacity *= 2;
        T[] new_array = (T[]) new Object[capacity];
        System.arraycopy(objects, 0, new_array, 0, objects.length);
        objects = new_array;
    }

    private int hash(T value) {
        return Math.abs(value.hashCode() % cnt_bucket);
    }

    private void extend_hash() {
        cnt_bucket *= 2;
        HashNode<T>[] new_hashNode = new HashNode[cnt_bucket];

        for (int i = 0; i < buckets.length; i++) {
            HashNode<T> curr = buckets[i];
            while (curr != null) {
                int new_ind = Math.abs(curr.key.hashCode() % cnt_bucket);
                HashNode<T> temp = curr.next;
                curr.next = new_hashNode[new_ind];
                new_hashNode[new_ind] = curr;
                curr = temp;
            }
        }

        buckets = new_hashNode;
    }

    private int find_index(T value) {
        int ind = hash(value);
        HashNode<T> curr = buckets[ind];

        while (curr != null) {
            if (curr.key.equals(value)) {
                return curr.value;
            }
            curr = curr.next;
        }
        return -1;
    }

    private void update(T value, int new_ind) {
        int ind = hash(value);
        HashNode<T> curr = buckets[ind];

        while (curr != null) {
            if (curr.key.equals(value)) {
                curr.value = new_ind;
                return;
            }
            curr = curr.next;
        }
    }

    private void remove_hashmap(T value) {
        int ind = hash(value);

        if (buckets[ind] == null) {
            return;
        }

        if (buckets[ind].key.equals(value)) {
            buckets[ind] = buckets[ind].next;
            return;
        }

        HashNode<T> curr = buckets[ind];
        while (curr.next != null) {
            if (curr.next.key.equals(value)) {
                curr.next = curr.next.next;
                return;
            }
            curr = curr.next;
        }
    }

    public boolean insert(T value) {
        boolean flag = contains(value);
        if (flag) {
            return false;
        }

        if (size >= capacity) {
            extend_array();
        }

        if (size > 0.75 * cnt_bucket) {
            extend_hash();
        }

        objects[size] = value;
        int ind = hash(value);
        HashNode<T> new_node = new HashNode<>(value, size);
        new_node.next = buckets[ind];
        buckets[ind] = new_node;

        size++;
        return true;
    }

    public boolean remove(T value) {
        if (size == 0) {
            return false;
        }

        int ind = find_index(value);
        if (ind == -1) return false;

        if (ind < size - 1) {
            objects[ind] = objects[size - 1];
            update(objects[size - 1], ind);
        }

        objects[size - 1] = null;

        size--;
        remove_hashmap(value);
        return true;
    }

    public boolean contains(T value) {
        return find_index(value) != -1;
    }

    public T getRandom() {
        if (size == 0) {
            throw new EmptySetException("empty");
        }
        Random rnd = new Random();
        int rand = rnd.nextInt(size);
        return objects[rand];
    }

}
