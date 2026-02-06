package hse.java.practice.task1;

import java.util.Arrays;

/**
 * Необходимо реализовать интерфейс Cube
 * При повороте передней грани, меняются верх низ право и лево
 */
public class RubiksCube {

    private static final int EDGES_COUNT = 6;

    private final Edge[] edges = new Edge[EDGES_COUNT];

    /**
     * Создать валидный собранный кубик
     * грани разместить по ордеру в енуме цветов
     * грань 0 -> цвет 0
     * грань 1 -> цвет 1
     * ...
     */
    public RubiksCube() {
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            edges[i] = new Edge(colors[i]);
        }
    }

    private static void reverse(CubeColor[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            CubeColor temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
    }

    public void front(RotateDirection direction) {
        edges[EdgePosition.FRONT.ordinal()].rotate(direction);
        
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] upRow = edges[EdgePosition.UP.ordinal()].getRow(2);
            CubeColor[] rightColumn = edges[EdgePosition.RIGHT.ordinal()].getColumn(0);
            CubeColor[] downRow = edges[EdgePosition.DOWN.ordinal()].getRow(0);
            CubeColor[] leftColumn = edges[EdgePosition.LEFT.ordinal()].getColumn(2);

            reverse(leftColumn);
            edges[EdgePosition.UP.ordinal()].setRow(2, leftColumn);
            edges[EdgePosition.RIGHT.ordinal()].setColumn(0, upRow);
            reverse(rightColumn);
            edges[EdgePosition.DOWN.ordinal()].setRow(0, rightColumn);
            edges[EdgePosition.LEFT.ordinal()].setColumn(2, downRow);
        } else {
            CubeColor[] upRow = edges[EdgePosition.UP.ordinal()].getRow(2);
            CubeColor[] rightColumn = edges[EdgePosition.RIGHT.ordinal()].getColumn(0);
            CubeColor[] downRow = edges[EdgePosition.DOWN.ordinal()].getRow(0);
            CubeColor[] leftColumn = edges[EdgePosition.LEFT.ordinal()].getColumn(2);

            edges[EdgePosition.UP.ordinal()].setRow(2, rightColumn);
            reverse(downRow);
            edges[EdgePosition.RIGHT.ordinal()].setColumn(0, downRow);
            edges[EdgePosition.DOWN.ordinal()].setRow(0, leftColumn);
            reverse(upRow);
            edges[EdgePosition.LEFT.ordinal()].setColumn(2, upRow);
        }
    }

    public void back(RotateDirection direction) {
        edges[EdgePosition.BACK.ordinal()].rotate(direction);
        
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] upRow = edges[EdgePosition.UP.ordinal()].getRow(0);
            CubeColor[] rightColumn = edges[EdgePosition.RIGHT.ordinal()].getColumn(2);
            CubeColor[] downRow = edges[EdgePosition.DOWN.ordinal()].getRow(2);
            CubeColor[] leftColumn = edges[EdgePosition.LEFT.ordinal()].getColumn(0);

            reverse(rightColumn);
            edges[EdgePosition.UP.ordinal()].setRow(0, rightColumn);
            edges[EdgePosition.RIGHT.ordinal()].setColumn(2, downRow);
            reverse(leftColumn);
            edges[EdgePosition.DOWN.ordinal()].setRow(2, leftColumn);
            edges[EdgePosition.LEFT.ordinal()].setColumn(0, upRow);
        } else {
            CubeColor[] upRow = edges[EdgePosition.UP.ordinal()].getRow(0);
            CubeColor[] rightColumn = edges[EdgePosition.RIGHT.ordinal()].getColumn(2);
            CubeColor[] downRow = edges[EdgePosition.DOWN.ordinal()].getRow(2);
            CubeColor[] leftColumn = edges[EdgePosition.LEFT.ordinal()].getColumn(0);

            edges[EdgePosition.UP.ordinal()].setRow(0, leftColumn);
            reverse(upRow);
            edges[EdgePosition.RIGHT.ordinal()].setColumn(2, upRow);
            edges[EdgePosition.DOWN.ordinal()].setRow(2, rightColumn);
            reverse(downRow);
            edges[EdgePosition.LEFT.ordinal()].setColumn(0, downRow);
        }
    }

    public void up(RotateDirection direction) {
        edges[EdgePosition.UP.ordinal()].rotate(direction);
        
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] frontRow = edges[EdgePosition.FRONT.ordinal()].getRow(0);
            CubeColor[] rightRow = edges[EdgePosition.RIGHT.ordinal()].getRow(0);
            CubeColor[] backRow = edges[EdgePosition.BACK.ordinal()].getRow(0);
            CubeColor[] leftRow = edges[EdgePosition.LEFT.ordinal()].getRow(0);

            edges[EdgePosition.FRONT.ordinal()].setRow(0, rightRow);
            edges[EdgePosition.RIGHT.ordinal()].setRow(0, backRow);
            edges[EdgePosition.BACK.ordinal()].setRow(0, leftRow);
            edges[EdgePosition.LEFT.ordinal()].setRow(0, frontRow);
        } else {
            CubeColor[] frontRow = edges[EdgePosition.FRONT.ordinal()].getRow(0);
            CubeColor[] rightRow = edges[EdgePosition.RIGHT.ordinal()].getRow(0);
            CubeColor[] backRow = edges[EdgePosition.BACK.ordinal()].getRow(0);
            CubeColor[] leftRow = edges[EdgePosition.LEFT.ordinal()].getRow(0);

            edges[EdgePosition.FRONT.ordinal()].setRow(0, leftRow);
            edges[EdgePosition.RIGHT.ordinal()].setRow(0, frontRow);
            edges[EdgePosition.BACK.ordinal()].setRow(0, rightRow);
            edges[EdgePosition.LEFT.ordinal()].setRow(0, backRow);
        }
    }

    public void down(RotateDirection direction) {
        edges[EdgePosition.DOWN.ordinal()].rotate(direction);
        
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] frontRow = edges[EdgePosition.FRONT.ordinal()].getRow(2);
            CubeColor[] rightRow = edges[EdgePosition.RIGHT.ordinal()].getRow(2);
            CubeColor[] backRow = edges[EdgePosition.BACK.ordinal()].getRow(2);
            CubeColor[] leftRow = edges[EdgePosition.LEFT.ordinal()].getRow(2);

            edges[EdgePosition.FRONT.ordinal()].setRow(2, leftRow);
            edges[EdgePosition.RIGHT.ordinal()].setRow(2, frontRow);
            edges[EdgePosition.BACK.ordinal()].setRow(2, rightRow);
            edges[EdgePosition.LEFT.ordinal()].setRow(2, backRow);
        } else {
            CubeColor[] frontRow = edges[EdgePosition.FRONT.ordinal()].getRow(2);
            CubeColor[] rightRow = edges[EdgePosition.RIGHT.ordinal()].getRow(2);
            CubeColor[] backRow = edges[EdgePosition.BACK.ordinal()].getRow(2);
            CubeColor[] leftRow = edges[EdgePosition.LEFT.ordinal()].getRow(2);

            edges[EdgePosition.FRONT.ordinal()].setRow(2, rightRow);
            edges[EdgePosition.RIGHT.ordinal()].setRow(2, backRow);
            edges[EdgePosition.BACK.ordinal()].setRow(2, leftRow);
            edges[EdgePosition.LEFT.ordinal()].setRow(2, frontRow);
        }
    }

    public void left(RotateDirection direction) {
        edges[EdgePosition.LEFT.ordinal()].rotate(direction);
        
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] upColumn = edges[EdgePosition.UP.ordinal()].getColumn(0);
            CubeColor[] frontColumn = edges[EdgePosition.FRONT.ordinal()].getColumn(0);
            CubeColor[] downColumn = edges[EdgePosition.DOWN.ordinal()].getColumn(0);
            CubeColor[] backColumn = edges[EdgePosition.BACK.ordinal()].getColumn(2);

            reverse(backColumn);
            edges[EdgePosition.UP.ordinal()].setColumn(0, backColumn);
            edges[EdgePosition.FRONT.ordinal()].setColumn(0, upColumn);
            edges[EdgePosition.DOWN.ordinal()].setColumn(0, frontColumn);
            reverse(downColumn);
            edges[EdgePosition.BACK.ordinal()].setColumn(2, downColumn);
        } else {
            CubeColor[] upColumn = edges[EdgePosition.UP.ordinal()].getColumn(0);
            CubeColor[] frontColumn = edges[EdgePosition.FRONT.ordinal()].getColumn(0);
            CubeColor[] downColumn = edges[EdgePosition.DOWN.ordinal()].getColumn(0);
            CubeColor[] backColumn = edges[EdgePosition.BACK.ordinal()].getColumn(2);

            edges[EdgePosition.UP.ordinal()].setColumn(0, frontColumn);
            edges[EdgePosition.FRONT.ordinal()].setColumn(0, downColumn);
            reverse(backColumn);
            edges[EdgePosition.DOWN.ordinal()].setColumn(0, backColumn);
            reverse(upColumn);
            edges[EdgePosition.BACK.ordinal()].setColumn(2, upColumn);
        }
    }

    public void right(RotateDirection direction) {
        edges[EdgePosition.RIGHT.ordinal()].rotate(direction);
        
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] upColumn = edges[EdgePosition.UP.ordinal()].getColumn(2);
            CubeColor[] frontColumn = edges[EdgePosition.FRONT.ordinal()].getColumn(2);
            CubeColor[] downColumn = edges[EdgePosition.DOWN.ordinal()].getColumn(2);
            CubeColor[] backColumn = edges[EdgePosition.BACK.ordinal()].getColumn(0);

            edges[EdgePosition.UP.ordinal()].setColumn(2, frontColumn);
            edges[EdgePosition.FRONT.ordinal()].setColumn(2, downColumn);
            reverse(backColumn);
            edges[EdgePosition.DOWN.ordinal()].setColumn(2, backColumn);
            reverse(upColumn);
            edges[EdgePosition.BACK.ordinal()].setColumn(0, upColumn);
        } else {
            CubeColor[] upColumn = edges[EdgePosition.UP.ordinal()].getColumn(2);
            CubeColor[] frontColumn = edges[EdgePosition.FRONT.ordinal()].getColumn(2);
            CubeColor[] downColumn = edges[EdgePosition.DOWN.ordinal()].getColumn(2);
            CubeColor[] backColumn = edges[EdgePosition.BACK.ordinal()].getColumn(0);

            reverse(backColumn);
            edges[EdgePosition.UP.ordinal()].setColumn(2, backColumn);
            edges[EdgePosition.FRONT.ordinal()].setColumn(2, upColumn);
            edges[EdgePosition.DOWN.ordinal()].setColumn(2, frontColumn);
            reverse(downColumn);
            edges[EdgePosition.BACK.ordinal()].setColumn(0, downColumn);
        }
    }

    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}
