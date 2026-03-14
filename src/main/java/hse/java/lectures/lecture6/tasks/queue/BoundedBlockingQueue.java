package hse.java.lectures.lecture6.tasks.queue;

import java.util.LinkedList;

public class BoundedBlockingQueue<T> {
    private final LinkedList<T> list = new LinkedList<>();
    private int capacity;
    private final Object monitor = new Object();

    public BoundedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw  new IllegalArgumentException("capacity must be positive");
        }
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        if (item == null) {
            throw new NullPointerException("item must be non-null");
        }

        synchronized (monitor) {
            while (list.size() == capacity) {
                monitor.wait();
            }
            list.push(item);
            notifyAll();
        }
    }

    public T take() throws InterruptedException {
        synchronized (monitor) {
            while (list.isEmpty()) {
                wait();
            }
            T item = list.pop();
            notifyAll();
            return item;
        }
    }

    public int size() {
        synchronized (monitor) {
            return list.size();
        }
    }

    public int capacity() {
        return this.capacity;
    }
}
