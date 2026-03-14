package hse.java.lectures.lecture6.tasks.queue;

import java.util.LinkedList;

public class BoundedBlockingQueue<T> {
    private final LinkedList<T> list = new LinkedList<>();
    private int capacity;

    public BoundedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        if (item == null) {
            throw new NullPointerException("item must be non-null");
        }

        synchronized (this) {
            while (list.size() == capacity) {
                this.wait();
            }
            list.addLast(item);
            notifyAll();
        }
    }

    public T take() throws InterruptedException {
        synchronized (this) {
            while (list.isEmpty()) {
                this.wait();
            }
            T item = list.getFirst();
            list.removeFirst();
            notifyAll();
            return item;
        }
    }

    public int size() {
        synchronized (this) {
            return list.size();
        }
    }

    public int capacity() {
        return this.capacity;
    }
}
