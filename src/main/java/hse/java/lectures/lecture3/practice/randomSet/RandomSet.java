package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T> {

    private static final Object DELETED_OBJECT = new Object();
    private static final int INIT_CAP = 128;

    private Object[] elements = new Object[INIT_CAP];
    private Object[] mapKeys = new Object[INIT_CAP];
    private int[] mapVals = new int[INIT_CAP];

	private int size = 0;
    private int mapCapacity = INIT_CAP;
    private int mapLiveElementsCount = 0;
    private int mapUsed = 0;

    private final Random rng = new Random();

    private int getIndex(Object key) {
        int h = key.hashCode();
        return ((h ^ (h >>> 16)) & 0x7fffffff) % mapCapacity;
    }

    private int mapFind(Object key) {
        int i = getIndex(key);
        while (mapKeys[i] != null) {
            if (mapKeys[i] != DELETED_OBJECT && mapKeys[i].equals(key)) return i;
            i = (i + 1) % mapCapacity;
        }
        return -1;
    }

    private void mapInsert(T key, int val) {
        if (mapUsed * 2 > mapCapacity) mapRehash();
        int i = getIndex(key);
        int ind = -1;
        while (true) {
            if (mapKeys[i] == null) {
                if (ind < 0) ind = i;
                break;
            }
            if (mapKeys[i] == DELETED_OBJECT && ind < 0) ind = i;
            i = (i + 1) % mapCapacity;
        }
        if (mapKeys[ind] == null) mapUsed++;
        mapKeys[ind] = key;
        mapVals[ind] = val;
        mapLiveElementsCount++;
    }

    private void mapRehash() {
        int oldCap = mapCapacity;
        Object[] oldKeys = mapKeys;
        int[] oldVals = mapVals;
        mapCapacity *= 2;
        mapKeys = new Object[mapCapacity];
        mapVals = new int[mapCapacity];
        mapLiveElementsCount = 0;
        mapUsed = 0;
        for (int i = 0; i < oldCap; i++) {
            if (oldKeys[i] != null && oldKeys[i] != DELETED_OBJECT) {
                mapInsert((T) oldKeys[i], oldVals[i]);
            }
        }
    }

    public boolean insert(T value) {
        if (value == null || mapFind(value) >= 0) return false;
        if (size == elements.length) {
            Object[] tmp = new Object[size * 2];
            System.arraycopy(elements, 0, tmp, 0, size);
            elements = tmp;
        }
        elements[size] = value;
        mapInsert(value, size);
        size++;
        return true;
    }

    public boolean remove(T value) {
		if (value == null) return false;
		
        final int mapInd = mapFind(value);
        if (mapInd < 0) return false;
        int deletedInd = mapVals[mapInd];

        mapKeys[mapInd] = DELETED_OBJECT;
        mapLiveElementsCount--;

        int last = size - 1;
        if (deletedInd != last) {
            T moved = (T) elements[last];
            elements[deletedInd] = moved;
			final int i = mapFind(moved);
        	mapVals[i] = deletedInd;
        }
        elements[last] = null;
        size--;
        return true;
    }

    public boolean contains(T value) {
        return mapFind(value) >= 0;
    }

    public T getRandom() {
        if (size == 0) throw new EmptySetException("Set is empty, you're lose");
        return (T) elements[rng.nextInt(size)];
    }
}
