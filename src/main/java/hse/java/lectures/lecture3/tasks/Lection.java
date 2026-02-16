package hse.java.lectures.lecture3.tasks;

import java.util.ArrayList;

class Methods {
    public <T,K> T get(T temp) {
        return temp;
    }
}


public class Lection {
    public static void main(String[] args) {
        boolean a = new Methods().get(new ArrayList<Integer>()).add(1);
        System.out.println("bool a : " + a);
    }

}
