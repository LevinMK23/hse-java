package hse.java.lectures.lecture6.tasks.queue;

import java.util.ArrayDeque;
import java.util.Queue;

public class BoundedBlockingQueue<T> {

    ArrayDeque<T> queue = new ArrayDeque<>();
    private int capacity;
    private int size = 0;
    Object monitor = new Object();

    public BoundedBlockingQueue(int capacity) throws IllegalArgumentException {
        if (capacity <= 0) throw new IllegalArgumentException();
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException, IllegalArgumentException {
        if (item == null) throw new IllegalArgumentException();
        synchronized (monitor) {
            if (size == capacity) {

                monitor.wait();

            }

            queue.add(item);
            size++;
            monitor.notifyAll();
        }
    }

    public T take() throws InterruptedException {
        synchronized (monitor) {
            while (size == 0) {

                monitor.wait();

            }
            size--;
            monitor.notifyAll();
            return queue.poll();
        }
    }

    public int size() {
        return 0;
    }

    public int capacity() {
        return 0;
    }
}
