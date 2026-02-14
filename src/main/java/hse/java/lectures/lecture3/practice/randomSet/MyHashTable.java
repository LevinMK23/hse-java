package hse.java.lectures.lecture3.practice.randomSet;

public class MyHashTable<K, V> {
    class Entry {
        K key;
        V value;
        boolean isDeleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Object[] data;
    private int size;
    private int countDeleted;
    private final double RESIZE_FAKTOR = 0.7;

    public MyHashTable() {
        data = new Object[64];
        size = 0;
        countDeleted = 0;
    }

    private int hash(K key) {
        int h = key.hashCode();
        h ^= (h >>> 16);
        return (h & 0x7fffffff) % data.length;
    }

    @SuppressWarnings("unchecked")
    private int getIndex(K key, boolean forInsert) {
        int i = hash(key);
        int firstDeletedIndex = -1;
        int count = 0;

        while (count < data.length) {
            Entry cur = (Entry) data[i];
            if (cur == null) {
                return (forInsert && firstDeletedIndex != -1) ? firstDeletedIndex : i;
            } else if (cur.isDeleted) {
                if (firstDeletedIndex == -1) {
                    firstDeletedIndex = i;
                }
            } else if (cur.key.equals(key)) {
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

    @SuppressWarnings("unchecked")
    private void resize() {
        if (size + countDeleted < data.length * RESIZE_FAKTOR) {
            return;
        }
        Object[] oldData = data;
        data = new Object[oldData.length * 2];
        countDeleted = 0;
        for (int i = 0; i < oldData.length; ++i) {
            Entry cur = (Entry) oldData[i];
            if (cur != null && !cur.isDeleted) {
                data[getIndex(cur.key, true)] = cur;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void put(K key, V value) {
        resize();
        int i = getIndex(key, true);
        Entry cur = (Entry) data[i];

        if (cur == null) {
            cur = new Entry(key, value);
            data[i] = cur;
            size++;
        } else if (cur.isDeleted) {
            cur.key = key;
            cur.value = value;
            cur.isDeleted = false;
            size++;
            countDeleted--;
        } else {
            cur.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    public void remove(K key) {
        int i = getIndex(key, false);
        if (i != -1) {
            Entry cur = (Entry) data[i];
            if (cur != null && !cur.isDeleted) {
                cur.isDeleted = true;
                size--;
                countDeleted++;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public boolean containsKey(K key) {
        int i = getIndex(key, false);
        Entry cur = (Entry) (i != -1 ? data[i] : null);
        return cur != null && !cur.isDeleted;
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        int i = getIndex(key, false);
        Entry cur = (Entry) (i != -1 ? data[i] : null);
        if (cur == null || cur.isDeleted) {
            return null;
        }
        return cur.value;
    }
}
