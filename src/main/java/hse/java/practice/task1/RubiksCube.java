package hse.java.practice.task1;

import java.util.Arrays;

/**
 * Необходимо реализовать интерфейс Cube
 * При повороте передней грани, меняются верх низ право и лево
 */
public class RubiksCube implements Cube{

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
        Edge[] edges_copy = CopyArray();
        rotateFace(edges[0], edges_copy[0], direction);

        switch (direction) {
            case CLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[2].getParts()[0][i] = edges_copy[4].getParts()[0][i];
                    edges[5].getParts()[0][i] = edges_copy[2].getParts()[0][i];
                    edges[3].getParts()[0][i] = edges_copy[5].getParts()[0][i];
                    edges[4].getParts()[0][i] = edges_copy[3].getParts()[0][i];
                }
                break;

            case COUNTERCLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[4].getParts()[0][i] = edges_copy[2].getParts()[0][i];
                    edges[2].getParts()[0][i] = edges_copy[5].getParts()[0][i];
                    edges[5].getParts()[0][i] = edges_copy[3].getParts()[0][i];
                    edges[3].getParts()[0][i] = edges_copy[4].getParts()[0][i];
                }
                break;
        }
    }

    @Override
    public void down(RotateDirection direction) {
        Edge[] edges_copy = CopyArray();
        rotateFace(edges[1], edges_copy[1], direction);

        switch (direction) {
            case CLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[4].getParts()[2][i] = edges_copy[2].getParts()[2][i];
                    edges[3].getParts()[2][i] = edges_copy[4].getParts()[2][i];
                    edges[5].getParts()[2][i] = edges_copy[3].getParts()[2][i];
                    edges[2].getParts()[2][i] = edges_copy[5].getParts()[2][i];
                }
                break;

            case COUNTERCLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[2].getParts()[2][i] = edges_copy[4].getParts()[2][i];
                    edges[4].getParts()[2][i] = edges_copy[3].getParts()[2][i];
                    edges[3].getParts()[2][i] = edges_copy[5].getParts()[2][i];
                    edges[5].getParts()[2][i] = edges_copy[2].getParts()[2][i];
                }
                break;
        }
    }

    @Override
    public void left(RotateDirection direction) {
        Edge[] edges_copy = CopyArray();
        rotateFace(edges[2], edges_copy[2], direction);

        switch (direction) {
            case CLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[4].getParts()[i][0] = edges_copy[0].getParts()[i][0];
                    edges[1].getParts()[i][0] = edges_copy[4].getParts()[i][0];
                    edges[5].getParts()[2 - i][2] = edges_copy[1].getParts()[i][0];
                    edges[0].getParts()[i][0] = edges_copy[5].getParts()[2 - i][2];
                }
                break;

            case COUNTERCLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[0].getParts()[i][0] = edges_copy[4].getParts()[i][0];
                    edges[4].getParts()[i][0] = edges_copy[1].getParts()[i][0];
                    edges[1].getParts()[i][0] = edges_copy[5].getParts()[2 - i][2];
                    edges[5].getParts()[2 - i][2] = edges_copy[0].getParts()[i][0];
                }
                break;
        }
    }

    @Override
    public void right(RotateDirection direction) {
        Edge[] edges_copy = CopyArray();
        rotateFace(edges[3], edges_copy[3], direction);

        switch (direction) {
            case CLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[5].getParts()[2 - i][0] = edges_copy[0].getParts()[i][2];
                    edges[1].getParts()[i][2] = edges_copy[5].getParts()[2 - i][0];
                    edges[4].getParts()[i][2] = edges_copy[1].getParts()[i][2];
                    edges[0].getParts()[i][2] = edges_copy[4].getParts()[i][2];
                }
                break;

            case COUNTERCLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[0].getParts()[i][2] = edges_copy[5].getParts()[2 - i][0];
                    edges[5].getParts()[2 - i][0] = edges_copy[1].getParts()[i][2];
                    edges[1].getParts()[i][2] = edges_copy[4].getParts()[i][2];
                    edges[4].getParts()[i][2] = edges_copy[0].getParts()[i][2];
                }
                break;
        }
    }

    @Override
    public void front(RotateDirection direction) {
        Edge[] edges_copy = CopyArray();
        rotateFace(edges[4], edges_copy[4], direction);
        switch (direction) {
            case CLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[0].getParts()[2][i] = edges_copy[2].getParts()[i][2];
                    edges[1].getParts()[0][i] = edges_copy[3].getParts()[i][0];
                    edges[2].getParts()[i][2] = edges_copy[1].getParts()[0][i];
                    edges[3].getParts()[i][0] = edges_copy[0].getParts()[2][i];
                }
                break;

            case COUNTERCLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[2].getParts()[i][2] = edges_copy[0].getParts()[2][i];
                    edges[3].getParts()[i][0] = edges_copy[1].getParts()[0][i];
                    edges[1].getParts()[0][i] = edges_copy[2].getParts()[i][2];
                    edges[0].getParts()[2][i] = edges_copy[3].getParts()[i][0];
                }
                break;
        }
    }

    @Override
    public void back(RotateDirection direction) {
        Edge[] edges_copy = CopyArray();
        rotateFace(edges[5], edges_copy[5], direction);

        switch (direction) {
            case CLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[0].getParts()[0][i] = edges_copy[3].getParts()[i][2];
                    edges[2].getParts()[2 - i][0] = edges_copy[0].getParts()[0][i];
                    edges[1].getParts()[2][i] = edges_copy[2].getParts()[i][0];
                    edges[3].getParts()[2 - i][2] = edges_copy[1].getParts()[2][i];
                }
                break;

            case COUNTERCLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[3].getParts()[i][2] = edges_copy[0].getParts()[0][i];
                    edges[0].getParts()[0][i] = edges_copy[2].getParts()[2 - i][0];
                    edges[2].getParts()[i][0] = edges_copy[1].getParts()[2][i];
                    edges[1].getParts()[2][i] = edges_copy[3].getParts()[2 - i][2];
                }
                break;
        }
    }

    public void rotateFace(Edge Orig, Edge Copy, RotateDirection direction) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (direction == RotateDirection.CLOCKWISE) {
                    Orig.getParts()[j][2 - i] = Copy.getParts()[i][j];
                } else {
                    Orig.getParts()[2 - j][i] = Copy.getParts()[i][j];
                }
            }
        }
    }

    public Edge[] CopyArray() {
        Edge[] edges_copy = new Edge[edges.length];
        for (int i = 0; i < 6; i++) {
            CubeColor[][] parts =  new CubeColor[3][3];
            for (int j = 0; j < 3; j++) {
                 parts[j] = Arrays.copyOf(edges[i].getParts()[j], 3);
            }
            edges_copy[i] =  new Edge(parts);
        }
        return edges_copy;
    }


    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}
