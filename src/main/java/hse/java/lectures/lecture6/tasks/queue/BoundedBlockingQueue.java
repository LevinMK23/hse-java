package hse.java.lectures.lecture6.tasks.queue;

import java.util.LinkedList;
import java.util.Queue;

public class BoundedBlockingQueue<T> {

    Queue<T> queue;
    int capacity;
    int currentAmount;

    public BoundedBlockingQueue(int capacity) {
        if (capacity<=0){
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        queue = new LinkedList<>();
        currentAmount = 0;
    }

    public synchronized void put(T item) throws InterruptedException {
        if (item == null){
            throw new NullPointerException();
        }
        if (currentAmount==capacity){
            wait();
        }
        queue.add(item);
        currentAmount++;
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        if (currentAmount==0){
            wait();
        }
        T val = queue.poll();
        currentAmount--;
        notifyAll();
        return val;
    }

    public synchronized int size() {
        return currentAmount;
    }

    public synchronized int capacity() {
        return capacity;
    }
}
