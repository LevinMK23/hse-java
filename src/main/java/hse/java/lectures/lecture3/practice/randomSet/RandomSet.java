package hse.java.lectures.lecture3.practice.randomSet;

import java.util.Random;

import static java.lang.Math.abs;

public class RandomSet<T extends Number> {
    private int mod = 1_000_003;
    private int N = 1_000_010;
    private Number[] hash_map = new Number[N];
    private int cnt_elem = 0;

    public boolean insert(T value) {
        int index;
        if(value.longValue() < 0){
            index = (int)(value.longValue() * (-1) % mod);
        }else{
            index = (int)(value.longValue() % mod);
        }
        while (hash_map[index] != null) {
            if(hash_map[index].equals(value)){
                return false;
            }
            if ((index + 1) >= N) {
                index = 0;
            } else {
                index++;
            }
        }
        hash_map[index] = value;
        cnt_elem++;
        return true;
    }

    public boolean remove(T value) {
        int index;
        if(value.longValue() < 0){
            index = (int)(value.longValue() * (-1) % mod);
        }else{
            index = (int)(value.longValue() % mod);
        }
        int copy_index = index;
        while (hash_map[index] == null || !hash_map[index].equals(value)){
            if ((index + 1) >= N) {
                index = 0;
            } else {
                index++;
            }
            if(copy_index == index){
                return false;
            }
        }
        hash_map[index] = -1;
        cnt_elem--;
        return  true;
    }

    public boolean contains(T value) {
        int index;
        if(value.longValue() < 0){
            index = (int)(value.longValue() * (-1) % mod);
        }else{
            index = (int)(value.longValue() % mod);
        }
        int copy_index = index;
        while (hash_map[index] == null || !hash_map[index].equals(value)){
            if ((index + 1) >= N) {
                index = 0;
            } else {
                index++;
            }
            if(copy_index == index){
                return false;
            }
        }
        return true;
    }

    public T getRandom() {
        if (cnt_elem == 0){
            throw new EmptySetException("zero elements");
        }
        Random random = new Random();
        int index = random.nextInt(mod);
        while(hash_map[index] == null || hash_map[index].equals(-1)){
            index = random.nextInt(mod);
        }
        return (T)hash_map[index];
    }

}
