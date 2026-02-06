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

    private CubeColor[][] copyParts(CubeColor[][] otherParts) {
        CubeColor[][] parts = new CubeColor[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(otherParts[i], 0, parts[i], 0, 3);
        }
        return parts;
    }

    private void rotateInnerClockwise(EdgePosition position) {
        CubeColor[][] parts = edges[position.ordinal()].getParts();
        CubeColor[][] newParts = new CubeColor[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newParts[2 - j][i] = parts[i][j];
            }
        }

        edges[4].setParts(newParts);
    }

    @Override
    public void up(RotateDirection direction) {
        switch (direction) {
            case CLOCKWISE -> {
                rotateUpClockwise();
            }
            case COUNTERCLOCKWISE -> {
                rotateUpClockwise();
                rotateUpClockwise();
                rotateUpClockwise();
            }
        }
    }

    private void rotateUpClockwise() {
        rotateInnerClockwise(EdgePosition.UP);

        CubeColor[][] upParts = edges[5].getParts();
        CubeColor[][] leftParts = edges[2].getParts();
        CubeColor[][] rightParts = edges[3].getParts();
        CubeColor[][] downParts = edges[4].getParts();

        CubeColor[][] upPartsCopy = copyParts(upParts);
        CubeColor[][] leftPartsCopy = copyParts(leftParts);
        CubeColor[][] rightPartsCopy = copyParts(rightParts);
        CubeColor[][] downPartsCopy = copyParts(downParts);

        for (int i = 0; i < 3; i++) {
            upParts[0][i] = leftPartsCopy[0][i];
            rightParts[0][i] = upPartsCopy[0][i];
            downParts[0][i] = rightPartsCopy[0][i];
            leftParts[0][i] = downPartsCopy[0][i];
        }
    }

    @Override
    public void down(RotateDirection direction) {
        switch (direction) {
            case CLOCKWISE -> {
                rotateDownClockwise();
            }
            case COUNTERCLOCKWISE -> {
                rotateDownClockwise();
                rotateDownClockwise();
                rotateDownClockwise();
            }
        }

    }

    private void rotateDownClockwise() {
        rotateInnerClockwise(EdgePosition.DOWN);

        CubeColor[][] upParts = edges[4].getParts();
        CubeColor[][] leftParts = edges[2].getParts();
        CubeColor[][] rightParts = edges[3].getParts();
        CubeColor[][] downParts = edges[5].getParts();

        CubeColor[][] upPartsCopy = copyParts(upParts);
        CubeColor[][] leftPartsCopy = copyParts(leftParts);
        CubeColor[][] rightPartsCopy = copyParts(rightParts);
        CubeColor[][] downPartsCopy = copyParts(downParts);

        for (int i = 0; i < 3; i++) {
            upParts[2][i] = leftPartsCopy[2][i];
            rightParts[2][i] = upPartsCopy[2][i];
            downParts[2][i] = rightPartsCopy[2][i];
            leftParts[2][i] = downPartsCopy[2][i];
        }
    }

    @Override
    public void left(RotateDirection direction) {
        switch (direction) {
            case CLOCKWISE -> {
                rotateLeftClockwise();
            }
            case COUNTERCLOCKWISE -> {
                rotateLeftClockwise();
                rotateLeftClockwise();
                rotateLeftClockwise();
            }
        }
    }

    private void rotateLeftClockwise() {
        rotateInnerClockwise(EdgePosition.LEFT);

        CubeColor[][] upParts = edges[0].getParts();
        CubeColor[][] leftParts = edges[5].getParts();
        CubeColor[][] rightParts = edges[4].getParts();
        CubeColor[][] downParts = edges[1].getParts();

        CubeColor[][] upPartsCopy = copyParts(upParts);
        CubeColor[][] leftPartsCopy = copyParts(leftParts);
        CubeColor[][] rightPartsCopy = copyParts(rightParts);
        CubeColor[][] downPartsCopy = copyParts(downParts);

        for (int i = 0; i < 3; i++) {
            upParts[i][0] = leftPartsCopy[2-i][2];
            rightParts[i][0] = upPartsCopy[i][0];
            downParts[i][0] = rightPartsCopy[i][0];
            leftParts[i][2] = downPartsCopy[0][2-i];
        }
    }

    @Override
    public void right(RotateDirection direction) {
        switch (direction) {
            case CLOCKWISE -> {
                rotateRightClockwise();
            }
            case COUNTERCLOCKWISE -> {
                rotateRightClockwise();
                rotateRightClockwise();
                rotateRightClockwise();
            }
        }
    }

    private void rotateRightClockwise() {
        rotateInnerClockwise(EdgePosition.RIGHT);

        CubeColor[][] upParts = edges[0].getParts();
        CubeColor[][] leftParts = edges[4].getParts();
        CubeColor[][] rightParts = edges[5].getParts();
        CubeColor[][] downParts = edges[1].getParts();

        CubeColor[][] upPartsCopy = copyParts(upParts);
        CubeColor[][] leftPartsCopy = copyParts(leftParts);
        CubeColor[][] rightPartsCopy = copyParts(rightParts);
        CubeColor[][] downPartsCopy = copyParts(downParts);

        for (int i = 0; i < 3; i++) {
            upParts[i][2] = leftPartsCopy[i][2];
            rightParts[i][0] = upPartsCopy[2-i][2];
            downParts[i][2] = rightPartsCopy[2-i][0];
            leftParts[i][2] = downPartsCopy[i][2];
        }
    }

    public void front(RotateDirection direction) {
        switch (direction) {
            case CLOCKWISE -> {
                rotateFrontClockwise();
            }
            case COUNTERCLOCKWISE -> {
                rotateFrontClockwise();
                rotateFrontClockwise();
                rotateFrontClockwise();
            }
        }
    }

    private void rotateFrontClockwise() {
        rotateInnerClockwise(EdgePosition.FRONT);

        CubeColor[][] upParts = edges[0].getParts();
        CubeColor[][] leftParts = edges[2].getParts();
        CubeColor[][] rightParts = edges[3].getParts();
        CubeColor[][] downParts = edges[1].getParts();

        CubeColor[][] upPartsCopy = copyParts(upParts);
        CubeColor[][] leftPartsCopy = copyParts(leftParts);
        CubeColor[][] rightPartsCopy = copyParts(rightParts);
        CubeColor[][] downPartsCopy = copyParts(downParts);

        for (int i = 0; i < 3; i++) {
            upParts[2][i] = leftPartsCopy[2-i][2];
            rightParts[i][0] = upPartsCopy[2][i];
            downParts[0][i] = rightPartsCopy[2-i][0];
            leftParts[i][2] = downPartsCopy[0][i];
        }
    }

    @Override
    public void back(RotateDirection direction) {
        switch (direction) {
            case CLOCKWISE -> {
                rotateBackClockwise();
            }
            case COUNTERCLOCKWISE -> {
                rotateBackClockwise();
                rotateBackClockwise();
                rotateBackClockwise();
            }
        }
    }

    private void rotateBackClockwise() {
        rotateInnerClockwise(EdgePosition.BACK);

        CubeColor[][] upParts = edges[0].getParts();
        CubeColor[][] leftParts = edges[3].getParts();
        CubeColor[][] rightParts = edges[2].getParts();
        CubeColor[][] downParts = edges[1].getParts();

        CubeColor[][] upPartsCopy = copyParts(upParts);
        CubeColor[][] leftPartsCopy = copyParts(leftParts);
        CubeColor[][] rightPartsCopy = copyParts(rightParts);
        CubeColor[][] downPartsCopy = copyParts(downParts);

        for (int i = 0; i < 3; i++) {
            upParts[0][i] = leftPartsCopy[i][2];
            rightParts[i][0] = upPartsCopy[0][2-i];
            downParts[2][i] = rightPartsCopy[i][0];
            leftParts[i][2] = downPartsCopy[2][2-i];
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