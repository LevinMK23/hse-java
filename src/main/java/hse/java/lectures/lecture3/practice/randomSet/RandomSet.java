package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

public class RandomSet<T> {
  private DynamicArray<T> elements;
  private SimpleHashMap<T> indexMap;
  private Random random;
  public RandomSet() {
    elements = new DynamicArray<>();
    indexMap = new SimpleHashMap<>();
    random = new Random();
  }

  public boolean insert(T value) {
    if (value == null) {
      throw new NullPointerException("Value cannot be null");
    }
    if (indexMap.containsKey(value)) {
      return false;
    }
    int idx = elements.size();
    elements.add(value);
    indexMap.put(value, idx);
    return true;
  }

  public boolean remove(T value) {
    if (value == null) {
      throw new NullPointerException("Value cannot be null");
    }
    Integer idx = indexMap.get(value);
    if (idx == null) {
      return false;
    }

    int lastIdx = elements.size() - 1;

    if (idx != lastIdx) {
      T lastElement = elements.get(lastIdx);
      elements.set(idx, lastElement);
      indexMap.put(lastElement, idx);
    }

    elements.removeLast();
    indexMap.remove(value);

    return true;
  }

  public boolean contains(T value) {
    if (value == null) {
      throw new NullPointerException("Value cannot be null");
    }
    return indexMap.containsKey(value);
  }

  public T getRandom() {
    if (elements.size() == 0) {
      throw new EmptySetException("Cannot get random element from empty set");
    }
    int idx = random.nextInt(elements.size());
    return elements.get(idx);
  }

  private static class DynamicArray<T> {
    private T[] data;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    DynamicArray() {
      capacity = DEFAULT_CAPACITY;
      data = (T[]) new Object[capacity];
      size = 0;
    }

    int size() { return size; }

    void add(T value) {
      if (size == capacity) {
        resize();
      }
      data[size++] = value;
    }

    T get(int index) {
      if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("Index: " + index +
                                            ", Size: " + size);
      }
      return data[index];
    }

    void set(int index, T value) {
      if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("Index: " + index +
                                            ", Size: " + size);
      }
      data[index] = value;
    }

    T removeLast() {
      if (size == 0) {
        throw new IndexOutOfBoundsException("Cannot remove from empty array");
      }
      T removed = data[--size];
      data[size] = null;
      return removed;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
      capacity *= 2;
      T[] newData = (T[]) new Object[capacity];
      System.arraycopy(data, 0, newData, 0, size);
      data = newData;
    }
  }

  private static class SimpleHashMap<T> {
    private static class Entry<T> {
      T key;
      int value;
      Entry<T> next;

      Entry(T key, int value, Entry<T> next) {
        this.key = key;
        this.value = value;
        this.next = next;
      }
    }

    private Entry<T>[] table;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    SimpleHashMap() {
      capacity = DEFAULT_CAPACITY;
      table = (Entry<T>[]) new Entry[capacity];
      size = 0;
    }

    private int hash(T key) {
      if (key == null)
        return 0;
      int h = key.hashCode();
      h ^= (h >>> 16);
      return (h & 0x7fffffff) % capacity;
    }

    boolean containsKey(T key) {
      int idx = hash(key);
      Entry<T> entry = table[idx];
      while (entry != null) {
        if (entry.key.equals(key)) {
          return true;
        }
        entry = entry.next;
      }
      return false;
    }

    Integer get(T key) {
      int idx = hash(key);
      Entry<T> entry = table[idx];
      while (entry != null) {
        if (entry.key.equals(key)) {
          return entry.value;
        }
        entry = entry.next;
      }
      return null;
    }

    void put(T key, int value) {
      int idx = hash(key);
      Entry<T> entry = table[idx];
      while (entry != null) {
        if (entry.key.equals(key)) {
          entry.value = value;
          return;
        }
        entry = entry.next;
      }
      table[idx] = new Entry<>(key, value, table[idx]);
      size++;
      if (size > capacity * LOAD_FACTOR) {
        resize();
      }
    }

    void remove(T key) {
      int idx = hash(key);
      Entry<T> entry = table[idx];
      Entry<T> prev = null;
      while (entry != null) {
        if (entry.key.equals(key)) {
          if (prev == null) {
            table[idx] = entry.next;
          } else {
            prev.next = entry.next;
          }
          size--;
          return;
        }
        prev = entry;
        entry = entry.next;
      }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
      Entry<T>[] oldTable = table;
      capacity *= 2;
      table = (Entry<T>[]) new Entry[capacity];
      size = 0;
      for (Entry<T> head : oldTable) {
        Entry<T> entry = head;
        while (entry != null) {
          put(entry.key, entry.value);
          entry = entry.next;
        }
      }
    }
  }
}