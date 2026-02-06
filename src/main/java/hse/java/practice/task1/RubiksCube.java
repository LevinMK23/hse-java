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

    }

    @Override
    public void down(RotateDirection direction) {

    }

    @Override
    public void left(RotateDirection direction) {

    }

    @Override
    public void right(RotateDirection direction) {

    }

    @Override
    public void front(RotateDirection direction) {
        Edge[] edges_copy = CopyArray();
        rotateFace(edges[4], edges_copy[4], direction);
        switch (direction) {
            case RotateDirection.CLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[0].getParts()[2][i] = edges_copy[2].getParts()[i][2];
                    edges[1].getParts()[0][i] = edges_copy[3].getParts()[i][0];
                    edges[2].getParts()[i][2] = edges_copy[1].getParts()[0][i];
                    edges[3].getParts()[i][0] = edges_copy[0].getParts()[2][i];
                }
                break;

            case RotateDirection.COUNTERCLOCKWISE:
                for (int i = 0; i < 3; i++) {
                    edges[2].getParts()[i][2] = edges_copy[0].getParts()[2][i];
                    edges[3].getParts()[i][0] = edges_copy[1].getParts()[0][i];
                    edges[1].getParts()[0][i] = edges_copy[2].getParts()[i][2];
                    edges[0].getParts()[2][i] = edges_copy[3].getParts()[i][0];
                }
        }
    }

    @Override
    public void back(RotateDirection direction) {

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
