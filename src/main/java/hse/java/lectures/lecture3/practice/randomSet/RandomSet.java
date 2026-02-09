package hse.java.lectures.lecture3.practice.randomSet;
import java.util.Random;


public class RandomSet<T extends Number> {
    private final Integer N = 600011;
    private Object[] map = new Object[N];
    private short[] states = new short[N];
    private Integer[] map_indexes = new Integer[N];
    private Integer[] indexes = new Integer[N];
    private int size = 0;
    private Random random = new Random();
    private void updateMapIndexes(int index_in_map, int index_in_indexes) {
        indexes[index_in_map] = index_in_indexes;
    }

    private Integer getIndex(T elem) {
        int start =  ((elem.hashCode() & 0x7FFFFFFF) % N);
        while (states[start] != 0 && !elem.equals(map[start])) {
            if (N.equals(++start)) {
                start = 0;
            }
        }
        return start;
    }

    public boolean insert(T value) {
        var index = getIndex(value);
        if (states[index]  == 1) {
            return false;
        } else {
            states[index] = 1;
            map[index] = value;
            indexes[size] = index;
            map_indexes[index] = size;
            size ++;
            return true;
        }
    }

    public boolean remove(T value) {
        int index = getIndex(value);

        if (states[index] <= 0) {
            return false;
        } else {
            states[index] = -1;

            size--;
            Integer index_in_indexes = map_indexes[index];
            indexes[index_in_indexes] = indexes[size];
            map_indexes[indexes[size]] = index_in_indexes;

            return true;
        }
    }

    public boolean contains(T value) {
        var index = getIndex(value);
        if (states[index] == 1) {
            return true;
        } else {
            return false;
        }
    }

    public T getRandom() {
        if (size == 0) {
            throw new EmptySetException("");
        }
        var ind = random.nextInt(size);
        return (T) map[indexes[ind]];
    }

}
