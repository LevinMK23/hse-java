package hse.java.lectures.lecture6.tasks.queue;

import java.util.LinkedList;
import java.util.Queue;

public class BoundedBlockingQueue<T>
{


    private final int capacity;

    private final Queue<T> queue = new LinkedList<>();


    private final Object lock = new Object();

    public BoundedBlockingQueue(int capacity)
    {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException
    {
        if (item == null) throw new IllegalArgumentException("Task must not be null");
        synchronized (lock)
        {
            while (queue.size() == capacity) lock.wait();

            queue.add(item);
            lock.notifyAll();
        }
    }

    public T take() throws InterruptedException
    {
        synchronized (lock)
        {
            while (queue.isEmpty()) lock.wait();

            T task = queue.remove();

            lock.notifyAll();
            return task;
        }
    }

    public int size()
    {
        synchronized (lock)
        {
            return queue.size();
        }
    }

    public int capacity()
    {
        return capacity;
    }
}
