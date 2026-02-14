package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T> {

    private MyArrayList<T> data = new MyArrayList<>();
    private MyHashTable<T, Integer> positions = new MyHashTable<>();
    private Random rand = new Random();

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
            int indexValue = positions.get(value);
            T lastNumber = data.get(data.size() - 1);
            data.set(indexValue, lastNumber);
            positions.put(lastNumber, indexValue);
            data.remove(data.size() - 1);
            positions.remove(value);
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
