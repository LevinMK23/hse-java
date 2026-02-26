package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Objects;
import java.util.Random;

public class RandomSet<T>
{

    private final Random random = new Random();
    private Node<T>[] buckets;
    private int mapSize;
    private T[] elements;
    private int elementsSize;

    public RandomSet()
    {
        this.buckets = (Node<T>[]) new Node[32];
        this.mapSize = 0;

        this.elements = (T[]) new Object[32];
        this.elementsSize = 0;
    }


    private int getIndexFromMap(T key)
    {
        int b = getBucketIndex(key);
        Node<T> curr = buckets[b];

        while (curr != null)
        {
            if (Objects.equals(curr.key, key)) return curr.index;
            curr = curr.next;
        }
        return -1;
    }

    private int getBucketIndex(T key)
    {
        if (key == null) return 0;

        return (key.hashCode() & 0x7FFFFFFF) & (buckets.length - 1);

    }

    private void resizeMap()
    {
        Node<T>[] oldBuckets = buckets;
        buckets = (Node<T>[]) new Node[oldBuckets.length * 2];

        mapSize = 0;
        for (int i = 0; i < oldBuckets.length; ++i)
        {
            Node<T> node = oldBuckets[i];
            while (node != null)
            {
                addToMap(node.key, node.index);
                node = node.next;
            }
        }
    }

    private void resizeArray()
    {
        T[] newElements = (T[]) new Object[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }

    private void addToMap(T key, int index)
    {
        int b = getBucketIndex(key);
        Node<T> curr = buckets[b];

        while (curr != null)
        {
            if (Objects.equals(key, curr.key))
            {
                curr.index = index;
                return;
            }
            curr = curr.next;
        }
        buckets[b] = new Node<>(key, index, buckets[b]);
        mapSize++;

        if (mapSize > buckets.length * 0.75) resizeMap();
    }

    private void removeFromMap(T key)
    {
        int b = getBucketIndex(key);

        Node<T> curr = buckets[b];
        Node<T> prev = null;

        while (curr != null)
        {
            if (Objects.equals(key, curr.key))
            {
                if (prev == null) buckets[b] = curr.next;
                else prev.next = curr.next;
                mapSize--;
                return;
            }
            prev = curr;
            curr = curr.next;
        }
    }


    public boolean insert(T value)
    {
        if (contains(value)) return false;

        if (elementsSize == elements.length) resizeArray();

        elements[elementsSize] = value;

        addToMap(value, elementsSize);

        elementsSize++;
        return true;
    }

    public boolean remove(T value)
    {
        int indexToRemove = getIndexFromMap(value);
        if (indexToRemove == -1) return false;

        int lastElemIndex = elementsSize - 1;

        T lastElem = elements[lastElemIndex];

        elements[indexToRemove] = lastElem;
        elements[lastElemIndex] = null;
        elementsSize--;

        removeFromMap(value);

        if (indexToRemove != lastElemIndex) addToMap(lastElem, indexToRemove);
        return true;
    }

    public boolean contains(T value)
    {
        return getIndexFromMap(value) != -1;
    }

    public T getRandom()
    {
        if (elementsSize == 0) throw new EmptySetException("Set is empty");

        return elements[random.nextInt(elementsSize)];
    }

    private static class Node<T>
    {
        T key;
        int index;
        Node<T> next;

        Node(T key, int index, Node<T> next)
        {
            this.key = key;
            this.index = index;
            this.next = next;
        }


    }

}
