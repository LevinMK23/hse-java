package hse.java.lectures.lecture3.practice.randomSet;

public class HashMap<K, V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next_node;

        Node(K _key, V _value, Node<K, V> _next_node) {
            key = _key;
            value = _value;
            next_node = _next_node;
        }
    }

    int BASE_CAP = 4;
    private Node<K, V>[] array;
    int size = 0;
    int capacity = 1 << BASE_CAP;
    int threshold = capacity - (capacity >> 2);


    public HashMap() {
        array = (Node<K, V>[]) new Node[capacity];
    }

    public HashMap(Integer _capacity) {
        capacity = Integer.highestOneBit(_capacity) << 1;
        threshold = capacity - (capacity >> 2);
        array = (Node<K, V>[]) new Node[capacity];
    }

    private Integer hash_func(K key) {
        Integer hash = key.hashCode();
        hash = hash ^ (hash >>> 16);
        return hash & (capacity - 1);
    }

    private void resize_capacity() {
        threshold *= 2;
        capacity = capacity << 1;
        if (capacity < 0) { //int overflow
            throw new HashMapOverflow("Max size of hash map achieved due to the hashing option");
        }

        Node<K, V>[] old_array = array;

        Node<K, V>[] new_array = (Node<K, V>[]) new Node[capacity];

        for (int i = 0; i < old_array.length; i++) {
            Node<K, V> curr_node = old_array[i];
            while (curr_node != null) {
                Node<K, V> next = curr_node.next_node;
                int new_index = hash_func(curr_node.key);
                curr_node.next_node = new_array[new_index];
                new_array[new_index] = curr_node;
                curr_node = next;
            }
        }

        array = new_array;
    }

    public boolean remove(K key) {
        Integer index = hash_func(key);
        Node<K, V> curr_node = array[index];
        Node<K, V> prev_node = null;

        while (curr_node != null) {
            if (curr_node.key.equals(key)) {
                if (prev_node == null) {
                    array[index] = curr_node.next_node;
                } else {
                    prev_node.next_node = curr_node.next_node;
                }
                size--;
                return true;
            }
            prev_node = curr_node;
            curr_node = curr_node.next_node;
        }
        return false;
    }

    public boolean insert(K key, V value) { // return true if key already exists 👹👹👹
        Integer index = hash_func(key);
        try {
            if (array[index] == null) {
                array[index] = new Node(key, value, null);
                size++;
                if (size == threshold) {
                    resize_capacity();
                }
                return false;
            } else {
                Node<K, V> curr_node = array[index];

                boolean found = false;
                while (curr_node != null) {
                    if (curr_node.key.equals(key)) {
                        found = true;
                        break;
                    }
                    curr_node = curr_node.next_node;
                }
                if (found == true) {
                    curr_node.value = value;
                    return true;
                } else {
                    Node<K, V> tmp = array[index];
                    array[index] = new Node(key, value, tmp);
                    size++;
                    if (size == threshold) {
                        resize_capacity();
                    }
                    return false;
                }
            }
        } catch (HashMapOverflow e) {
            throw e;
        }
    }

    public V get(K key) {
        Integer index = hash_func(key);
        Node<K, V> curr_node = array[index];

        while (curr_node != null) {
            if (curr_node.key.equals(key)) {
                return curr_node.value;
            }
            curr_node = curr_node.next_node;
        }
        return null;
    }

    public boolean exists(K key) {
        Integer index = hash_func(key);
        Node<K, V> curr_node = array[index];

        while (curr_node != null) {
            if (curr_node.key.equals(key)) {
                return true;
            }
            curr_node = curr_node.next_node;
        }
        return false;
    }

}
