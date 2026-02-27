package hse.java.lectures.lecture3.practice.randomSet;

public class RandomSet<T> {

    private HashMap<T, Integer> value_to_index;
    private T[] index_to_value;
    private int size;
    private int capacity;
    private java.util.Random random;

    public RandomSet() {
        value_to_index = new HashMap<>();
        capacity = 16;
        index_to_value = (T[]) new Object[capacity];
        size = 0;
        random = new java.util.Random(); //да
    }

    public boolean insert(T _value) {
        if (value_to_index.exists(_value)) {
            return false;
        }

        if (size == capacity) {
            capacity = capacity << 1;
            T[] new_array = (T[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                new_array[i] = index_to_value[i];
            }
            index_to_value = new_array;
        }

        index_to_value[size] = _value;
        value_to_index.insert(_value, size);
        size++;
        return true;
    }

    public boolean remove(T _value) {
        Integer index = value_to_index.get(_value);
        if (index == null) {
            return false;
        }

        int last_index = size - 1;
        T last_value = index_to_value[last_index];

        index_to_value[index] = last_value;
        value_to_index.insert(last_value, index);

        index_to_value[last_index] = null;
        value_to_index.remove(_value);
        size--;
        return true;
    }

    public boolean contains(T _value) {
        return value_to_index.exists(_value);
    }

    public T getRandom() {
        if (size == 0) {
            throw new EmptySetException("Set is empty");
        }
        int rand_index = random.nextInt(size);
        return index_to_value[rand_index];
    }

}