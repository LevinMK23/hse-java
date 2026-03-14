package hse.java.lectures.lecture6.tasks.queue;

public class BoundedBlockingQueue<T> {

    private final Object[] array;
    int capacity;
    int tail;
    int head;
    int fill;


    public BoundedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.array = new Object[capacity];
        this.capacity = capacity;

        this.head = capacity - 1;
        this.tail = this.head;
        this.fill = 0;
    }

    public void put(T item) throws InterruptedException {
        if (item == null) {
            throw new NullPointerException();
        }
        synchronized (this) {
            while (fill == capacity) {
                this.wait();
            }

            tail = (tail + 1) % capacity;
            fill++;
            this.notifyAll();

        }

    }

    public T take() throws InterruptedException {
        synchronized (this) {
            while (fill == 0) {
                wait();
            }

            T item = (T) array[head];
            array[head] = null;
            head = (head + 1) % capacity;
            fill--;

            this.notifyAll();
            return item;

        }


    }

    public int size() {
        return fill;
    }

    public int capacity() {

        return capacity;
    }
}
