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

    private void swap(int a, int b) {
        Edge tmp = edges[a];
        edges[a] = edges[b];
        edges[b] = tmp;
    }


    private void RotateEdge(RotateDirection direction, CubeColor[][] edge) {
        if (RotateDirection.CLOCKWISE == direction) {
            CubeColor tmp_cell = edge[0][0];
            edge[0][0] = edge[2][0];
            edge[2][0] = edge[2][2];
            edge[2][2] = edge[0][2];
            edge[0][2] = tmp_cell;

            tmp_cell = edge[0][1];
            edge[0][1] = edge[1][0];
            edge[1][0] = edge[2][1];
            edge[2][1] = edge[1][2];
            edge[1][2] = tmp_cell;
        }
        if (RotateDirection.COUNTERCLOCKWISE == direction) {
            CubeColor tmp_cell = edge[0][0];
            edge[0][0] = edge[0][2];
            edge[0][2] = edge[2][2];
            edge[2][2] = edge[2][0];
            edge[2][0] = tmp_cell;

            tmp_cell = edge[0][1];
            edge[0][1] = edge[1][2];
            edge[1][2] = edge[2][1];
            edge[2][1] = edge[1][0];
            edge[1][0] = tmp_cell;
        }
    }

    @Override
    public void up(RotateDirection direction) {
        swap(EdgePosition.FRONT.ordinal(), EdgePosition.UP.ordinal());
        swap(EdgePosition.UP.ordinal(), EdgePosition.DOWN.ordinal());
        swap(EdgePosition.UP.ordinal(), EdgePosition.BACK.ordinal());
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.LEFT.ordinal()].getParts());
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.RIGHT.ordinal()].getParts());
        front(direction);
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.RIGHT.ordinal()].getParts());
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.LEFT.ordinal()].getParts());
        swap(EdgePosition.UP.ordinal(), EdgePosition.BACK.ordinal());
        swap(EdgePosition.UP.ordinal(), EdgePosition.DOWN.ordinal());
        swap(EdgePosition.FRONT.ordinal(), EdgePosition.UP.ordinal());
    }


    @Override
    public void down(RotateDirection direction) {
        swap(EdgePosition.DOWN.ordinal(), EdgePosition.FRONT.ordinal());
        swap(EdgePosition.DOWN.ordinal(), EdgePosition.UP.ordinal());
        swap(EdgePosition.DOWN.ordinal(), EdgePosition.BACK.ordinal());
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.LEFT.ordinal()].getParts());
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.RIGHT.ordinal()].getParts());
        front(direction);
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.LEFT.ordinal()].getParts());
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.RIGHT.ordinal()].getParts());
        swap(EdgePosition.DOWN.ordinal(), EdgePosition.BACK.ordinal());
        swap(EdgePosition.DOWN.ordinal(), EdgePosition.UP.ordinal());
        swap(EdgePosition.DOWN.ordinal(), EdgePosition.FRONT.ordinal());
    }

    @Override
    public void left(RotateDirection direction) {
        swap(EdgePosition.LEFT.ordinal(), EdgePosition.FRONT.ordinal());
        swap(EdgePosition.LEFT.ordinal(), EdgePosition.RIGHT.ordinal());
        swap(EdgePosition.LEFT.ordinal(), EdgePosition.BACK.ordinal());
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.UP.ordinal()].getParts());
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.DOWN.ordinal()].getParts());
        front(direction);
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.UP.ordinal()].getParts());
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.DOWN.ordinal()].getParts());
        swap(EdgePosition.LEFT.ordinal(), EdgePosition.BACK.ordinal());
        swap(EdgePosition.LEFT.ordinal(), EdgePosition.RIGHT.ordinal());
        swap(EdgePosition.LEFT.ordinal(), EdgePosition.FRONT.ordinal());
    }

    @Override
    public void right(RotateDirection direction) {
        swap(EdgePosition.RIGHT.ordinal(), EdgePosition.FRONT.ordinal());
        swap(EdgePosition.RIGHT.ordinal(), EdgePosition.LEFT.ordinal());
        swap(EdgePosition.RIGHT.ordinal(), EdgePosition.BACK.ordinal());
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.UP.ordinal()].getParts());
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.DOWN.ordinal()].getParts());
        front(direction);
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.UP.ordinal()].getParts());
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.DOWN.ordinal()].getParts());
        swap(EdgePosition.RIGHT.ordinal(), EdgePosition.BACK.ordinal());
        swap(EdgePosition.RIGHT.ordinal(), EdgePosition.LEFT.ordinal());
        swap(EdgePosition.RIGHT.ordinal(), EdgePosition.FRONT.ordinal());
    }

    @Override
    public void front(RotateDirection direction) {
        CubeColor[][] front = edges[4].getParts();
        CubeColor[][] up = edges[0].getParts();
        CubeColor[][] down = edges[1].getParts();
        CubeColor[][] left = edges[2].getParts();
        CubeColor[][] right = edges[3].getParts();
        RotateEdge(direction, front);
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor tmp_cell;
            for (int i = 0; i < 3; i++) {
                tmp_cell = left[i][2];
                left[i][2] = down[0][i];
                down[0][i] = right[2 - i][0];
                right[2 - i][0] = up[2][2 - i];
                up[2][2 - i] = tmp_cell;
            }

        } else if (direction == RotateDirection.COUNTERCLOCKWISE) {
            CubeColor tmp_cell;
            for (int i = 0; i < 3; i++) {
                tmp_cell = down[0][i];
                down[0][i] = left[i][2];
                left[i][2] = up[2][2 - i];
                up[2][2 - i] = right[2 - i][0];
                right[2 - i][0] = tmp_cell;
            }
        }
    }

    @Override
    public void back(RotateDirection direction) {
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.LEFT.ordinal()].getParts());
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.LEFT.ordinal()].getParts());
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.RIGHT.ordinal()].getParts());
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.RIGHT.ordinal()].getParts());
        swap(EdgePosition.UP.ordinal(), EdgePosition.DOWN.ordinal());
        swap(EdgePosition.BACK.ordinal(), EdgePosition.FRONT.ordinal());
        front(direction);
        swap(EdgePosition.BACK.ordinal(), EdgePosition.FRONT.ordinal());
        swap(EdgePosition.UP.ordinal(), EdgePosition.DOWN.ordinal());
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.RIGHT.ordinal()].getParts());
        RotateEdge(RotateDirection.CLOCKWISE, edges[EdgePosition.RIGHT.ordinal()].getParts());
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.LEFT.ordinal()].getParts());
        RotateEdge(RotateDirection.COUNTERCLOCKWISE, edges[EdgePosition.LEFT.ordinal()].getParts());
    }

    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}
