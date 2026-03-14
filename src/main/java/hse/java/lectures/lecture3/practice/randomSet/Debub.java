package hse.java.lectures.lecture3.practice.randomSet;

public class Debub {
    public static void main(String[] args) {
        RandomSet<Integer> rs = new RandomSet<Integer>();

        rs.insert(10);
        rs.insert(20);

        for (int i = 0; i < 20; i++) {
            System.out.println(rs.getRandom());
        }

        rs.remove(10);
        for (int i = 0; i < 20; i++) {
            System.out.println(rs.getRandom());
        }
        System.out.println(rs.remove(10));
    }
}
