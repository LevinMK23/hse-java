package hse.java.lectures.lecture3.practice.randomSet;

import java.util.*;

class MyHashTable<K, V> {
    class Entry {
        K key;
        V value;
        boolean isDeleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // this for simple commit

    private Entry[] data;
    private int size;
    private int countDeleted;
    private final double RESIZE_FAKTOR = 0.7;

    public MyHashTable() {
        data = new Entry[64];
        size = 0;
        countDeleted = 0;
    }

    private int hash(K key) {
        int h = key.hashCode();
        h ^= (h >>> 16);
        return (h & 0x7fffffff) % data.length;
    }

    private int getIndex(K key, boolean forInsert) {
        int i = hash(key);
        int firstDeletedIndex = -1;
        int count = 0;

        while (count < data.length) {
            if (data[i] == null) {
                return (forInsert && firstDeletedIndex != -1) ? firstDeletedIndex : i;
            } else if (data[i].isDeleted) {
                if (firstDeletedIndex == -1) {
                    firstDeletedIndex = i;
                }
            } else if (data[i].key.equals(key)) {
                return i;
            }

            i = (i + 1) % data.length;
            count++;
        }
        if (forInsert && firstDeletedIndex != -1) {
            return firstDeletedIndex;
        }
        return -1;
    }

    private void resize() {
        if (size + countDeleted < data.length * RESIZE_FAKTOR) {
            return;
        }
        Entry[] oldData = data;
        data = new Entry[oldData.length * 2];
        countDeleted = 0;
        for (int i = 0; i < oldData.length; ++i) {
            if (oldData[i] != null && oldData[i].isDeleted == false) {
                data[getIndex(oldData[i].key, true)] = oldData[i];
            }
        }
    }

    public void put(K key, V value) {
        resize();
        int i = getIndex(key, true);
        if (data[i] == null) {
            data[i] = new Entry(key, value);
            size++;
        } else if (data[i].isDeleted) {
            data[i].key = key;
            data[i].value = value;
            data[i].isDeleted = false;
            size++;
            countDeleted--;
        } else {
            data[i].value = value;
        }
    }

    public void remove(K key) {
        resize();
        int i = getIndex(key, false);
        if (i != -1 && data[i].isDeleted == false) {
            data[i].isDeleted = true;
            size--;
            countDeleted++;
        }
    }

    public boolean containsKey(K key) {
        int i = getIndex(key, false);
        if (i == -1) {
            return false;
        } else {
            return true;
        }
    }

    public V get(K key) {
        int i = getIndex(key, false);
        if (i == -1) {
            return null;
        } else {
            return data[i].value;
        }
    }

}

public class RandomSet<T> {

    private List<T> data = new ArrayList<>();
    private MyHashTable<T, Integer> positions = new MyHashTable<>();
    private Random rand = new Random();

    private void swap(int i, int j) {
        if (i == j) {
            return;
        }
        T temp = data.get(i);
        data.set(i, data.get(j));
        positions.put(data.get(j), i);
        data.set(j, temp);
        positions.put(temp, j);
    }

    private int getRandomnumber(int a, int b) {
        return a + rand.nextInt(b - a + 1);
    }

    public boolean insert(T value) {
        if (positions.containsKey(value)) {
            return false;
        } else {
            data.add(value);
            positions.put(value, data.size() - 1);
            return true;
        }
    }

    public boolean remove(T value) {
        if (!positions.containsKey(value)) {
            return false;
        } else {
            int index = positions.get(value);
            positions.remove(value);
            swap(index, data.size() - 1);
            data.remove(data.size() - 1);
            return true;
        }
    }

    public boolean contains(T value) {
        return positions.containsKey(value);
    }

    public T getRandom() {
        if (data.size() == 0) {
            throw new EmptySetException("Set is empty");
        }
        return data.get(getRandomnumber(0, data.size() - 1));
    }

}
