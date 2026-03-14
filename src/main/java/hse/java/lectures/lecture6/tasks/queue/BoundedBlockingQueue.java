package hse.java.lectures.lecture6.tasks.queue;

import java.util.Queue;

public class BoundedBlockingQueue<T> {

    Queue<T> queue;
    private int capacity;
    private int size = 0;
    Object monitor;
    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) {
        synchronized (monitor) {
            if (size == capacity) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            queue.add(item);
            size++;
            monitor.notifyAll();
        }
    }

    public T take() {
        synchronized (monitor) {
            if (size == 0) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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
