package hse.java.lectures.lecture3.practice.randomSet;

public class DynamicArray<T> {
    private Object[] data;
    private int size;

    public DynamicArray() {
        data = new Object[16];
        size = 0 ;
    }

    public int size() {
        return  size;
    }

    public void add(T value) {
        if ( size == data.length) {
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data , 0 , newData ,0 ,size);
            data = newData;
        }
        data[size] = value;
        size++;
    }
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if ( 0 < index && index < data.length){
            return (T)data[index] ;
        }
        return null;
    }
    public void set(int index , T value) {
        data[index] =value ;
    }

    @SuppressWarnings("unchecked")
    public T removeLast() {
        T value = (T) data[size -1] ;
        data[size - 1] = null;
        size--;
        return value;
    }


}
