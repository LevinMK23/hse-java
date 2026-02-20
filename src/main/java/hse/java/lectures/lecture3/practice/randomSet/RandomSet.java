package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Objects;
import java.util.Random;

import static hse.java.lectures.lecture3.practice.randomSet.HashMap.getDefaultCapacity;


public class RandomSet<T> {
    HashMap<T , Integer> hashMap ;
    DynamicArray<T> elements;
    private final Random random = new Random();
    RandomSet() {
        hashMap = new HashMap<>();
        elements = new DynamicArray<>();
    }




    public boolean insert(T value) {
        if (hashMap.containsKey(value)) {
            return false;
        }
        elements.add(value);
        hashMap.put(value ,elements.size() - 1) ;
        return true;
    }

    public boolean remove(T value) {
        Integer index = hashMap.get(value);
        if ( index == null) {
            return  false;
        }
        T lastElement = elements.removeLast();

        if (!value.equals(lastElement)) {
            elements.set(index , lastElement);
            hashMap.put(lastElement ,index);
        }
        hashMap.remove(value);
        return true;

    }

    public boolean contains(T value) {
        return hashMap.containsKey(value);
    }

    public T getRandom() {
        if (elements.size() == 0) {
            throw new EmptySetException("Пустое множество");
        }
        int randomIndex = random.nextInt(elements.size());
        return elements.get(randomIndex);
    }


}
