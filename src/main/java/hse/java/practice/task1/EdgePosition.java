package hse.java.practice.task1;

/**
 * Ордер в енуме соответствует позиции грани куба
 * в массиве граней
 * edges[0] -> UP
 * edges[1] -> DOWN
 * edges[2] -> LEFT
 * ...
 */

// то есть имеется ввиду , что какие-то элементы храняться сверху / сбоку и тд
public enum EdgePosition {
    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3),
    FRONT(4),
    BACK(5);

    final int edgeNumber;

    EdgePosition(int edgeNumber) {
        this.edgeNumber = edgeNumber;
    }

    public int getEdgeNumber() {
        return edgeNumber;
    }

}
