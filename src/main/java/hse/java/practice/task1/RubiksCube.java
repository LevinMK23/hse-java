package hse.java.practice.task1;

import java.util.Arrays;

/**
 * Необходимо реализовать интерфейс Cube
 * При повороте передней грани, меняются верх низ право и лево
 */
public class RubiksCube implements Cube {

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

    @Override
    public void up(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            for (int i = 0; i < 3; ++i) {
                up(RotateDirection.CLOCKWISE);
            }
            return;
        }

        rotateFaceClockWise(edges[EdgePosition.UP.ordinal()]);

        CubeColor[] tmp = new CubeColor[3];
        for (int i = 0; i < 3; ++i) {
            tmp[i] = edges[EdgePosition.BACK.ordinal()].getParts()[0][i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.BACK.ordinal()].getParts()[0][i] = edges[EdgePosition.LEFT.ordinal()].getParts()[0][i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.LEFT.ordinal()].getParts()[0][i] = edges[EdgePosition.FRONT.ordinal()].getParts()[0][i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.FRONT.ordinal()].getParts()[0][i] = edges[EdgePosition.RIGHT.ordinal()].getParts()[0][i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.RIGHT.ordinal()].getParts()[0][i] = tmp[i];
        }
    }

    @Override
    public void down(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            for (int i = 0; i < 3; ++i) {
                down(RotateDirection.CLOCKWISE);
            }
            return;
        }

        rotateFaceClockWise(edges[EdgePosition.DOWN.ordinal()]);

        CubeColor[] tmp = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            tmp[i] = edges[EdgePosition.FRONT.ordinal()].getParts()[2][i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.FRONT.ordinal()].getParts()[2][i] = edges[EdgePosition.LEFT.ordinal()].getParts()[2][i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.LEFT.ordinal()].getParts()[2][i] = edges[EdgePosition.BACK.ordinal()].getParts()[2][i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.BACK.ordinal()].getParts()[2][i] = edges[EdgePosition.RIGHT.ordinal()].getParts()[2][i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.RIGHT.ordinal()].getParts()[2][i] = tmp[i];
        }
    }

    @Override
    public void left(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            for (int i = 0; i < 3; ++i) {
                left(RotateDirection.CLOCKWISE);
            }
            return;
        }

        rotateFaceClockWise(edges[EdgePosition.LEFT.ordinal()]);
        CubeColor[] tmp = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            tmp[i] = edges[EdgePosition.UP.ordinal()].getParts()[i][0];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.UP.ordinal()].getParts()[i][0] = edges[EdgePosition.BACK.ordinal()].getParts()[2 - i][2];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.BACK.ordinal()].getParts()[2 - i][2] = edges[EdgePosition.DOWN.ordinal()].getParts()[i][0];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.DOWN.ordinal()].getParts()[i][0] = edges[EdgePosition.FRONT.ordinal()].getParts()[i][0];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.FRONT.ordinal()].getParts()[i][0] = tmp[i];
        }
    }

    @Override
    public void right(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            for (int i = 0; i < 3; ++i) {
                right(RotateDirection.CLOCKWISE);
            }
            return;
        }

        rotateFaceClockWise(edges[EdgePosition.RIGHT.ordinal()]);

        CubeColor[] tmp = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            tmp[i] = edges[EdgePosition.UP.ordinal()].getParts()[2 - i][2];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.UP.ordinal()].getParts()[2 - i][2] = edges[EdgePosition.FRONT.ordinal()].getParts()[2 - i][2];
        }
        for (int i = 0; i < 3; i++)  {
            edges[EdgePosition.FRONT.ordinal()].getParts()[2 - i][2] = edges[EdgePosition.DOWN.ordinal()].getParts()[2 - i][2];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.DOWN.ordinal()].getParts()[2 - i][2] = edges[EdgePosition.BACK.ordinal()].getParts()[i][0];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.BACK.ordinal()].getParts()[i][0] = tmp[i];
        }
    }

    @Override
    public void front(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            for (int i = 0; i < 3; ++i) {
                front(RotateDirection.CLOCKWISE);
            }
            return;
        }

        rotateFaceClockWise(edges[EdgePosition.FRONT.ordinal()]);

        CubeColor[] tmp = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            tmp[i] = edges[EdgePosition.UP.ordinal()].getParts()[2][i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.UP.ordinal()].getParts()[2][i] = edges[EdgePosition.LEFT.ordinal()].getParts()[2 - i][2];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.LEFT.ordinal()].getParts()[i][2] = edges[EdgePosition.DOWN.ordinal()].getParts()[0][i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.DOWN.ordinal()].getParts()[0][i] = edges[EdgePosition.RIGHT.ordinal()].getParts()[2 - i][0];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.RIGHT.ordinal()].getParts()[i][0] = tmp[i];
        }
    }

    @Override
    public void back(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            for (int i = 0; i < 3; ++i) {
                up(RotateDirection.CLOCKWISE);
            }
            return;
        }

        rotateFaceClockWise(edges[EdgePosition.BACK.ordinal()]);

        CubeColor[] tmp = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            tmp[i] = edges[EdgePosition.UP.ordinal()].getParts()[0][2 - i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.UP.ordinal()].getParts()[0][2 - i] = edges[EdgePosition.RIGHT.ordinal()].getParts()[2 - i][2];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.RIGHT.ordinal()].getParts()[2 - i][2] = edges[EdgePosition.DOWN.ordinal()].getParts()[2][i];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.DOWN.ordinal()].getParts()[2][i] = edges[EdgePosition.LEFT.ordinal()].getParts()[i][0];
        }
        for (int i = 0; i < 3; i++) {
            edges[EdgePosition.LEFT.ordinal()].getParts()[i][0] = tmp[i];
        }
    }

    private void rotateFaceClockWise(Edge edge) {
        CubeColor[][] face = edge.getParts();
        CubeColor tmp = face[0][0];
        face[0][0] = face[2][0];
        face[2][0] = face[2][2];
        face[2][2] = face[0][2];
        face[0][2] = tmp;
        tmp = face[0][1];
        face[0][1] = face[1][0];
        face[1][0] = face[2][1];
        face[2][1] = face[1][2];
        face[1][2] = tmp;
    }
    
    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}
