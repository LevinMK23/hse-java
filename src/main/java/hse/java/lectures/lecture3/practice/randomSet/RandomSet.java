package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T> {

    private Object[] keyByIdx;
    private int[] keyToIdx;
    private Object[] keys;
    private byte[] status; // 0 - empty, 1 - occupied, 2 - deleted
    int capacity_;
    int size_;
    private final Random random = new Random();

    public RandomSet() {
        this.keyByIdx = new Object[32];
        this.keyToIdx = new int[32];
        this.keys = new Object[32];
        this.status = new byte[32];
        this.capacity_ = 32;
        this.size_ = 0;
    }

    private void rehash() {
        Object[] oldKeys = keys;
        byte[] oldStatus = status;

        capacity_ <<= 1;
        keys = new Object[capacity_];
        status = new byte[capacity_];
        keyToIdx = new int[capacity_];
        keyByIdx = new Object[capacity_];
        size_ = 0;

        for (int i = 0; i < oldKeys.length; i++) {
            if (oldStatus[i] == 1) {
                insert((T) oldKeys[i]);
            }
        }
    }

    private int hash(T value) {
        int h = value.hashCode();
        h ^= h >>> 16;
        h *= 0x21f0aaad;
        h ^= h >>> 15;
        h *= 0x735a2d97;
        h ^= h >>> 15;
        return h & (capacity_ - 1);
    }

    public boolean insert(T value) {
        if (contains(value)) return false;
        if (size_ >= capacity_ * 0.7) rehash();

        int h = hash(value);
        
        while (status[h] == 1) {
            h = (h + 1) & (capacity_ - 1);
        }

        keyToIdx[h] = size_;
        keyByIdx[size_] = value;
        keys[h] = value;
        status[h] = 1;
        size_++;
        return true;
    }

    private void change(T value, int newIdx) {
        int h = hash(value);
        while (status[h] != 0) {
            if (status[h] == 1 && keys[h].equals(value)) {
                keyToIdx[h] = newIdx;
                return;
            }
            h = (h + 1) & (capacity_ - 1);
        }
    }

    public boolean remove(T value) {
        if (!contains(value)) return false;

        int h = hash(value);

        while (status[h] != 0) {
            if (status[h] == 1 && keys[h].equals((value))) {
                int idx = keyToIdx[h];
                T lastValue = (T) keyByIdx[size_ - 1];
                keyByIdx[idx] = lastValue;
                change(lastValue, idx);
                status[h] = 2;
                size_--;
                return true;
            }
            h = (h + 1) & (capacity_ - 1);
        }
        return false;
    }

    public boolean contains(T value) {
        int h = hash(value);
        int start = h;
    
        while (status[h] != 0) {
            if (status[h] == 1 && keys[h].equals(value)) {
                return true;
            }
            h = (h + 1) & (capacity_ - 1);
            if (h == start) return false;
        }
        return false;
    }

    public T getRandom() {
        if (size_ == 0) {
            throw new EmptySetException("");
        }

        return (T) keyByIdx[random.nextInt(size_)];
    }

}
