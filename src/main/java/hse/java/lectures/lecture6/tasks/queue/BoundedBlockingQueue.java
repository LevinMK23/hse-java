package hse.java.lectures.lecture6.tasks.queue;

import java.util.LinkedList;
import java.util.Queue;

public class BoundedBlockingQueue<T> {
    private final Queue<T> q = new LinkedList<T>();
    private int size = 0;
    private final int capacity;
    private final Object monitor;

    public BoundedBlockingQueue(int capacity) throws InterruptedException {
        if (capacity > 0) {
            this.capacity = capacity;
            monitor = new Object();
        } else {
            throw new InterruptedException("Capacity is zero");
        }
    }

    public void put(T item) throws InterruptedException {
        if (item == null) {
            throw new NullPointerException("Item is null");
        }
        synchronized (monitor) {
            while (size == capacity) {
                monitor.wait();
            }
            q.add(item);
            size++;
            monitor.notifyAll();
        }
    }

    public T take() throws InterruptedException {
        synchronized (monitor) {
            while (size == 0) {
                monitor.wait();
            }
            T value = q.remove();
            size--;
            monitor.notifyAll();
            return value;
        }
    }

    public int size() {
        synchronized (monitor){
            monitor.notify();
            return size;
        }
    }

    public int capacity() {
        return capacity;
    }
}
