package hse.java.lectures.lecture6.tasks.queue;

import java.util.LinkedList;
import java.util.Queue;

public class BoundedBlockingQueue<T> {

    private int capacity = 0;
    private final Object monitor = new Object();
    private final Queue<T> blockingQueue = new LinkedList<>();

    public BoundedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.capacity = capacity;
        }
    }

    public void put(T item) throws InterruptedException {
        if (item == null) {
            throw new NullPointerException();
        } else {
            synchronized (monitor) {
                while (blockingQueue.size() == capacity) {
                    monitor.wait();
                }
                blockingQueue.add(item);
                monitor.notifyAll();
            }
        }
    }

    public T take() throws InterruptedException {
        synchronized (monitor) {
            while (blockingQueue.isEmpty()) {
                monitor.wait();
            }
            T item = blockingQueue.remove();
            monitor.notifyAll();
            return item;
        }
    }

    public int size() {
        synchronized (monitor) {
            return blockingQueue.size();
        }
    }

    public int capacity() {
        return capacity;
    }
}
