package hse.java.lectures.lecture6.tasks.queue;

import java.util.LinkedList;
import java.util.Queue;

public class BoundedBlockingQueue<T> {
    Queue<T> queue;
    int capacity;

    public BoundedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        queue = new LinkedList<>();
    }

    public synchronized void put(T item) throws InterruptedException {
        if (item == null) {
            throw new NullPointerException();
        }

        while (queue.size() == capacity) {
            wait();
        }

        queue.add(item);
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T item = queue.poll();
        notifyAll();
        return item;
    }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized int capacity() {
        return capacity;
    }
}
