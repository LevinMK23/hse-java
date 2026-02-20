package hse.java.lectures.lecture3.practice.randomSet;

public class DynamicArray<T> {
    private Object[] data;
    private int size;

    public DynamicArray() {
        data = new Object[32];
        size = 0 ;
    }

    public int size() {
        return  size;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) return null; // Защита
        return (T) data[index];
    }

    public void add(T value) {
        if (size == data.length) {
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
        data[size++] = value;
    }

    public void set(int index, T value) {
        if (index >= 0 && index < size) {
            data[index] = value;
        }
    }

    @SuppressWarnings("unchecked")
    public T removeLast() {
        if (size == 0) return null;
        T value = (T) data[size - 1];
        data[size - 1] = null;
        size--;
        return value;
    }


}
