package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T> {

    private Object[] keys = new Object[1024];
    private int[] values = new int[1024];
    private Object[] arr = new Object[1024];

    private int capacity = 1024;
	private int size = 0;
    private int cnt = 0;
    private int used = 0;

    private Random rand = new Random();
    private static Object delMark = new Object();

    public boolean insert(T value) {
        if (findKey(value) >= 0 || value == null) {
            return false;
        }
        if (arr.length == size) {
            Object[] arr2 = new Object[size * 2];
            for (int i = 0; i < size; ++ i) {
                arr2[i] = arr[i];
            }
            arr = arr2;
        }
        arr[size] = value;
        insertKey(value, size);
        ++size;
        return true;
    }

    public boolean remove(T value) {
		if (value == null) {
            return false;
        }
        int index = findKey(value);
        if (index < 0) {
            return false;
        }
        keys[index] = delMark;
        --cnt;
        --size;
        if (values[index] != size) {
            arr[values[index]] = arr[size];
        	values[findKey(arr[size])] = values[index];
        }
        arr[size] = null;
        return true;
    }

    public boolean contains(T value) {
        return findKey(value) >= 0;
    }

    public T getRandom() {
        return (T) arr[rand.nextInt(size)];
    }

    private int getInd(Object key) {
        int h = key.hashCode();
        return ((h ^ (h >>> 16)) & 0x7fffffff) % capacity;
    }

    private int findKey(Object key) {
        int i = getInd(key);
        while (keys[i] != null) {
            if (keys[i] != delMark && keys[i] == key) {
                return i;
            }
            i += 1;
            i %= capacity;
        }
        return -1;
    }

    private void insertKey(T key, int val) {
        if (used * 2 > capacity) rebuild();
        int i = getInd(key);
        int ind = -1;
        while (true) {
            if (keys[i] == null) {
                if (ind < 0) ind = i;
                break;
            }
            if (keys[i] == delMark && ind < 0) ind = i;
            i = (i + 1) % capacity;
        }
        if (keys[ind] == null) used++;
        keys[ind] = key;
        values[ind] = val;
        cnt++;
    }

    private void rebuild() {
        int[] oldVals = values;
        Object[] oldKeys = keys;
        int oldCap = capacity;
        capacity = capacity * 2;
        keys = new Object[capacity];
        values = new int[capacity];
        cnt = 0;
        used = 0;
        for (int i = 0; i < oldCap; i++) {
            if (oldKeys[i] != null && oldKeys[i] != delMark) {
                insertKey((T) oldKeys[i], oldVals[i]);
            }
        }
    }

}
