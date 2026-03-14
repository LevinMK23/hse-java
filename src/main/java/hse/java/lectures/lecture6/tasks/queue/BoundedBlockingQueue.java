package hse.java.lectures.lecture6.tasks.queue;

import java.util.LinkedList;

public class BoundedBlockingQueue<T> {

    final Object monitor;
    int capacity;
    LinkedList<T> queue;
    int curr_len;

    public BoundedBlockingQueue(int capacity_) throws IllegalArgumentException {

        if (capacity_ <= 0) {
            throw new IllegalArgumentException();
        }

        monitor = new Object();
        capacity = capacity_;
        queue = new LinkedList<>();
        curr_len = 0;
    }

    public void put(T item) throws InterruptedException {
        if (item == null) {
            throw new NullPointerException();
        }
        synchronized (monitor) {
            while (curr_len >= capacity) {
                monitor.wait();
            }
            queue.offer(item);
            curr_len++;
            monitor.notifyAll();
        }
    }

    public T take() throws InterruptedException {
        synchronized (monitor) {
            while (curr_len <= 0) {
                monitor.wait();
            }
            curr_len--;
            T item = queue.poll();
            monitor.notifyAll();
            return item;
        }
    }

    public int size() {
        synchronized (monitor) {
            return curr_len;
        }
    }

    public int capacity() {
        return capacity;
    }
}
