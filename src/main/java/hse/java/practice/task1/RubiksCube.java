package hse.java.practice.task1;

import java.util.Arrays;

public class RubiksCube implements Cube {

    private static final int EDGES_COUNT = 6;

    private final Edge[] edges = new Edge[EDGES_COUNT];

    public RubiksCube() {
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            edges[i] = new Edge(colors[i]);
        }
    }

    @Override
    public void front(RotateDirection direction) {
        rotateFace(edges[EdgePosition.FRONT.ordinal()], direction);

        CubeColor[] line1 = getRow(EdgePosition.UP, 2);
        CubeColor[] line2 = getCol(EdgePosition.RIGHT, 0);
        CubeColor[] line3 = getRow(EdgePosition.DOWN, 0);
        CubeColor[] line4 = getCol(EdgePosition.LEFT, 2);

        if (direction == RotateDirection.CLOCKWISE) {
            setRow(EdgePosition.UP, 2, reverse(line4));
            setCol(EdgePosition.RIGHT, 0, line1);
            setRow(EdgePosition.DOWN, 0, reverse(line2));
            setCol(EdgePosition.LEFT, 2, line3);
        } else {
            setRow(EdgePosition.UP, 2, line2);
            setCol(EdgePosition.RIGHT, 0, reverse(line3));
            setRow(EdgePosition.DOWN, 0, line4);
            setCol(EdgePosition.LEFT, 2, reverse(line1));
        }
    }

    @Override
    public void back(RotateDirection direction) {
        rotateFace(edges[EdgePosition.BACK.ordinal()], direction);

        CubeColor[] line1 = getRow(EdgePosition.UP, 0);
        CubeColor[] line2 = getCol(EdgePosition.LEFT, 0);
        CubeColor[] line3 = getRow(EdgePosition.DOWN, 2);
        CubeColor[] line4 = getCol(EdgePosition.RIGHT, 2);

        if (direction == RotateDirection.CLOCKWISE) {
            setRow(EdgePosition.UP, 0, reverse(line4));
            setCol(EdgePosition.LEFT, 0, line1);
            setRow(EdgePosition.DOWN, 2, reverse(line2));
            setCol(EdgePosition.RIGHT, 2, line3);
        } else {
            setRow(EdgePosition.UP, 0, line2);
            setCol(EdgePosition.LEFT, 0, reverse(line3));
            setRow(EdgePosition.DOWN, 2, line4);
            setCol(EdgePosition.RIGHT, 2, reverse(line1));
        }
    }

    @Override
    public void left(RotateDirection direction) {
        rotateFace(edges[EdgePosition.LEFT.ordinal()], direction);

        CubeColor[] line1 = getCol(EdgePosition.UP, 0);
        CubeColor[] line2 = getCol(EdgePosition.FRONT, 0);
        CubeColor[] line3 = getCol(EdgePosition.DOWN, 0);
        CubeColor[] line4 = getCol(EdgePosition.BACK, 2);

        if (direction == RotateDirection.CLOCKWISE) {
            setCol(EdgePosition.UP, 0, reverse(line4));
            setCol(EdgePosition.FRONT, 0, line1);
            setCol(EdgePosition.DOWN, 0, line2);
            setCol(EdgePosition.BACK, 2, reverse(line3));
        } else {
            setCol(EdgePosition.UP, 0, line2);
            setCol(EdgePosition.FRONT, 0, line3);
            setCol(EdgePosition.DOWN, 0, reverse(line4));
            setCol(EdgePosition.BACK, 2, reverse(line1));
        }
    }

    @Override
    public void right(RotateDirection direction) {
        rotateFace(edges[EdgePosition.RIGHT.ordinal()], direction);

        CubeColor[] line1 = getCol(EdgePosition.UP, 2);
        CubeColor[] line2 = getCol(EdgePosition.BACK, 0);
        CubeColor[] line3 = getCol(EdgePosition.DOWN, 2);
        CubeColor[] line4 = getCol(EdgePosition.FRONT, 2);

        if (direction == RotateDirection.CLOCKWISE) {
            setCol(EdgePosition.UP, 2, line4);
            setCol(EdgePosition.BACK, 0, reverse(line1));
            setCol(EdgePosition.DOWN, 2, reverse(line2));
            setCol(EdgePosition.FRONT, 2, line3);
        } else {
            setCol(EdgePosition.UP, 2, reverse(line2));
            setCol(EdgePosition.BACK, 0, line3);
            setCol(EdgePosition.DOWN, 2, line4);
            setCol(EdgePosition.FRONT, 2, reverse(line1));
        }
    }

    @Override
    public void up(RotateDirection direction) {
        rotateFace(edges[EdgePosition.UP.ordinal()], direction);

        CubeColor[] line1 = getRow(EdgePosition.BACK, 0);
        CubeColor[] line2 = getRow(EdgePosition.RIGHT, 0);
        CubeColor[] line3 = getRow(EdgePosition.FRONT, 0);
        CubeColor[] line4 = getRow(EdgePosition.LEFT, 0);

        if (direction == RotateDirection.CLOCKWISE) {
            setRow(EdgePosition.BACK, 0, line4);
            setRow(EdgePosition.RIGHT, 0, line1);
            setRow(EdgePosition.FRONT, 0, line2);
            setRow(EdgePosition.LEFT, 0, line3);
        } else {
            setRow(EdgePosition.BACK, 0, line2);
            setRow(EdgePosition.RIGHT, 0, line3);
            setRow(EdgePosition.FRONT, 0, line4);
            setRow(EdgePosition.LEFT, 0, line1);
        }
    }

    @Override
    public void down(RotateDirection direction) {
        rotateFace(edges[EdgePosition.DOWN.ordinal()], direction);

        CubeColor[] line1 = getRow(EdgePosition.FRONT, 2);
        CubeColor[] line2 = getRow(EdgePosition.RIGHT, 2);
        CubeColor[] line3 = getRow(EdgePosition.BACK, 2);
        CubeColor[] line4 = getRow(EdgePosition.LEFT, 2);

        if (direction == RotateDirection.CLOCKWISE) {
            setRow(EdgePosition.FRONT, 2, line4);
            setRow(EdgePosition.RIGHT, 2, line1);
            setRow(EdgePosition.BACK, 2, line2);
            setRow(EdgePosition.LEFT, 2, line3);
        } else {
            setRow(EdgePosition.FRONT, 2, line2);
            setRow(EdgePosition.RIGHT, 2, line3);
            setRow(EdgePosition.BACK, 2, line4);
            setRow(EdgePosition.LEFT, 2, line1);
        }
    }

    private void rotateFace(Edge edge, RotateDirection direction) {
        CubeColor[][] old = edge.getParts();
        CubeColor[][] rotated = new CubeColor[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (direction == RotateDirection.CLOCKWISE) {
                    rotated[j][2 - i] = old[i][j];
                } else {
                    rotated[2 - j][i] = old[i][j];
                }
            }
        }
        edge.setParts(rotated);
    }

    private CubeColor[] getRow(EdgePosition pos, int row) {
        CubeColor[] result = new CubeColor[3];
        CubeColor[][] parts = edges[pos.ordinal()].getParts();
        for (int i = 0; i < 3; i++) {
            result[i] = parts[row][i];
        }
        return result;
    }

    private CubeColor[] getCol(EdgePosition pos, int col) {
        CubeColor[] result = new CubeColor[3];
        CubeColor[][] parts = edges[pos.ordinal()].getParts();
        for (int i = 0; i < 3; i++) {
            result[i] = parts[i][col];
        }
        return result;
    }

    private void setRow(EdgePosition pos, int row, CubeColor[] data) {
        CubeColor[][] parts = edges[pos.ordinal()].getParts();
        for (int i = 0; i < 3; i++) {
            parts[row][i] = data[i];
        }
    }

    private void setCol(EdgePosition pos, int col, CubeColor[] data) {
        CubeColor[][] parts = edges[pos.ordinal()].getParts();
        for (int i = 0; i < 3; i++) {
            parts[i][col] = data[i];
        }
    }

    private CubeColor[] reverse(CubeColor[] array) {
        CubeColor[] result = new CubeColor[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[array.length - 1 - i];
        }
        return result;
    }

    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}