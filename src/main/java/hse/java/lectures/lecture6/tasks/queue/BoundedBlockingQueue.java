package hse.java.lectures.lecture6.tasks.queue;

import java.util.ArrayList;
import java.util.List;

public class BoundedBlockingQueue<T> {

    private List<T> buf;
    private int head = 0;
    private int tail = 0;
    private int size;
    private final int capacity;
    private Object monitor;

    public BoundedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.buf = new ArrayList<>(capacity);
        this.size = 0;
        this.capacity = capacity;
        this.monitor = new Object();

        for (int i = 0; i < capacity; ++i) {
            buf.add(null);
        }
    }

    public void put(T item) throws InterruptedException {
        if (item == null) {
            throw new NullPointerException();
        }
        synchronized (monitor) {
            while (size == capacity) {
                monitor.wait();
            }

            buf.set(tail, item);
            size++;
            tail = (tail + 1) % capacity;

            monitor.notify();
        }

    }

    public T take() throws InterruptedException {
        synchronized (monitor) {
            while (size == 0) {
                monitor.wait();
            }
            T res = buf.get(head);
            head = (head + 1) % capacity;
            size--;
            monitor.notify();
            return res;
        }

    }

    public int size() {
        synchronized (monitor) {
            return size;
        }
    }

    public int capacity() {
        return capacity;
    }
}
