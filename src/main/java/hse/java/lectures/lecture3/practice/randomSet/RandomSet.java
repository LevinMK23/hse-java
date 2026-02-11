package hse.java.lectures.lecture3.practice.randomSet;
import java.util.Random;

public class RandomSet<T> {

    public boolean insert(T value) {
        if (value == null) {
            return false;
        }
        if (find_idx(value) >= 0) {
            return false;
        }
        if (size_ == elements_.length) {
            resize();
        }
        elements_[size_] = value;
        unsafe_insert(value, size_);
        size_++;
        return true;
    }

    public boolean remove(T value) {
        if (value == null) {
            return false;
        }
        int idx = find_idx(value);
        if (idx < 0) {
            return false;
        }
        int elem_idx = values_[idx];
        keys_[idx] = DELETED;
        existing_keys_--;
        int last = size_ - 1;
        if (elem_idx != last) {
            update_idx(elements_[last], elem_idx);
        }
        elements_[last] = null;
        size_--;
        return true;
    }

    public boolean contains(T value) {
        return (value == null ? false : find_idx(value) >= 0);
    }

    public T getRandom() {
        if (size_ == 0) {
            throw new EmptySetException("getRandom error: set is empty");
        }
        return elements_[random_.nextInt(size_)];
    }

    private static final int DEFAULT_CAPACITY = 128;
    private static final Object DELETED = new Object();

    private T[] elements_ = (T[]) new Object[DEFAULT_CAPACITY];
    private Object[] keys_ = new Object[DEFAULT_CAPACITY];
    private int[] values_ = new int[DEFAULT_CAPACITY];
    private int size_ = 0;
    private int capacity_ = DEFAULT_CAPACITY;
    private int used_ = 0;
    private int existing_keys_ = 0;

    private final Random random_ = new Random();

    private int hash(Object _key) {
        int h = _key.hashCode();
        h ^= (h >>> 16);
        return h & (capacity_ - 1);
    }

    private int find_idx(Object _key) {
        for (int i = hash(_key); keys_[i] != null; i = next(i)) {
            if (keys_[i] != DELETED && keys_[i].equals(_key)) {
                return i;
            }
        }
        return -1;
    }

    private void unsafe_insert(T _key, int _value_idx) {
        if (used_ * 2 > capacity_) {
            reset();
        }
        int fst = -1;
        for (int i = hash(_key);; i = next(i)) {
            if (keys_[i] == null) {
                fst = fst == -1 ? i : fst;
                break;
            }
            if (keys_[i] == DELETED && fst == -1) {
                fst = i;
            }
        }
        if (keys_[fst] == null) {
            used_++;
        }
        keys_[fst] = _key;
        values_[fst] = _value_idx;
        existing_keys_++;
    }

    private void reset() {
        Object[] keys_cpy = keys_;
        int[] values_cpy = values_;
        int capacity_cpy = capacity_;
        capacity_ *= 2;
        keys_ = new Object[capacity_];
        values_ = new int[capacity_];
        used_ = 0;
        existing_keys_ = 0;
        for (int i = 0; i < capacity_cpy; i++) {
            if (keys_cpy[i] == null || keys_cpy[i] == DELETED) continue;
            unsafe_insert((T)keys_cpy[i], values_cpy[i]);
        }
    }

    private void resize() {
        T[] new_arr = (T[]) new Object[elements_.length * 2];
        System.arraycopy(elements_, 0, new_arr, 0, size_);
        elements_ = new_arr;
    }

    private void update_idx(T value, int new_idx) {
        elements_[new_idx] = value;
        values_[find_idx(value)] = new_idx;
    }

    private int next(int index) {
        return (index + 1) & (capacity_ - 1);
    }

}
