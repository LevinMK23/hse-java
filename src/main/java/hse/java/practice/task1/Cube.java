package hse.java.practice.task1;

public interface Cube {

    // Метод вращения верхней грани кубика
    void up(RotateDirection direction);

    // Метод вращения нижней грани кубика
    void down(RotateDirection direction);

    // Метод вращения левой грани кубика
    void left(RotateDirection direction);

    // Метод вращения правой грани кубика
    void right(RotateDirection direction);

    // Метод вращения передней грани кубика
    void front(RotateDirection direction);

    // Метод вращения задней грани кубика
    void back(RotateDirection direction);
}
