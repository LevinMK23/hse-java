package hse.java.lectures.lecture3.practice.randomSet;

import java.util.HashMap;
import java.util.Random;

public class RandomSet<T> {

    private final HashMap<Integer, T> indexToValue;
    private final HashMap<T, Integer> valueToIndex;
    private int size;
    private final Random random;

    public RandomSet() {
        this.indexToValue = new HashMap<>();
        this.valueToIndex = new HashMap<>();
        this.size = 0;
        this.random = new Random();
    }

    public boolean insert(T value) {
        if (valueToIndex.containsKey(value)) {
            return false;
        }
        valueToIndex.put(value, size);
        indexToValue.put(size, value);
        size++;
        return true;
    }

    public boolean remove(T value) {
        Integer index = valueToIndex.get(value);
        if (index == null) {
            return false;
        }
        if (index != size - 1) {
            T endValue = indexToValue.get(size - 1);
            indexToValue.put(index, endValue);
            valueToIndex.put(endValue, index);
        }
        indexToValue.remove(size - 1);
        valueToIndex.remove(value);
        size--;
        return true;
    }

    public boolean contains(T value) {
        return valueToIndex.containsKey(value);
    }

    public T getRandom() {
        if (size == 0) {
            throw new EmptySetException("Error because set is empty");
        }
        int indexToGet = random.nextInt(size);
        return indexToValue.get(indexToGet);
    }
}
