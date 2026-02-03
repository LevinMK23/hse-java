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

    private CubeColor[] rev(CubeColor[] s) {
        var sc = s.clone();
        var tmp = sc[0];
        sc[0] = sc[2];
        sc[2] = tmp;
        return sc;
    }

    public RubiksCube() {
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            edges[i] = new Edge(colors[i]);
        }
    }

    @Override
    public void front(RotateDirection direction) {
        var e0 = edges[0].getRow(2);
        var e1 = edges[1].getRow(0);
        var e2 = edges[2].getCol(2);
        var e3 = edges[3].getCol(0);
        if (direction == RotateDirection.CLOCKWISE) {
            edges[4].rotateClockwise();
            edges[0].setRow(2 , rev(e2));
            edges[1].setRow(0 , rev(e3));
            edges[2].setCol(2 , e1);
            edges[3].setCol(0 , e0);
        } else {
            edges[4].rotateClockwise();
            edges[4].rotateClockwise();
            edges[4].rotateClockwise();
            edges[0].setRow(2 , e3);
            edges[1].setRow(0 , e2);
            edges[2].setCol(2 , rev(e0));
            edges[3].setCol(0 , rev(e1));
        }
    }

    @Override
    public void right(RotateDirection direction) {
        var e4 = edges[4].getCol(2);
        var e0 = edges[0].getCol(2);
        var e5 = edges[5].getCol(2);
        var e1 = edges[1].getCol(2);
        if (direction == RotateDirection.CLOCKWISE) {
            edges[3].rotateClockwise();
            edges[1].setCol(2 , e5);
            edges[4].setCol(2 , e1);
            edges[0].setCol(2 , e4);
            edges[5].setCol(2 , e0);
        } else {
            edges[3].rotateClockwise();
            edges[3].rotateClockwise();
            edges[3].rotateClockwise();
            edges[1].setCol(2 , e4);
            edges[4].setCol(2 , e0);
            edges[0].setCol(2 , e5);
            edges[5].setCol(2 , e1);
        }
    }

    @Override
    public void left(RotateDirection direction) {
        var e4 = edges[4].getCol(0);
        var e0 = edges[0].getCol(0);
        var e5 = edges[5].getCol(0);
        var e1 = edges[1].getCol(0);
        if (direction == RotateDirection.CLOCKWISE) {
            edges[2].rotateClockwise();
            edges[1].setCol(0 , e4);
            edges[4].setCol(0 , e0);
            edges[0].setCol(0 , rev(e5));
            edges[5].setCol(0 , rev(e1));
        } else {
            edges[2].rotateClockwise();
            edges[2].rotateClockwise();
            edges[2].rotateClockwise();

            edges[1].setCol(0 , rev(e5));
            edges[4].setCol(0 , e1);
            edges[0].setCol(0 , e4);
            edges[5].setCol(0 , rev(e0));
        }
    }

    @Override
    public void up(RotateDirection direction) {
        var e2 = edges[2].getRow(0);
        var e4 = edges[4].getRow(0);
        var e3 = edges[3].getRow(0);
        var e5 = edges[5].getRow(0);
        if (direction == RotateDirection.CLOCKWISE) {
            edges[0].rotateClockwise();
            edges[3].setRow(0 , rev(e5));
            edges[4].setRow(0 , e3);
            edges[2].setRow(0 , e4);
            edges[5].setRow(0 , rev(e2));
        } else {
            edges[0].rotateClockwise();
            edges[0].rotateClockwise();
            edges[0].rotateClockwise();

            edges[3].setRow(0 , e4);
            edges[4].setRow(0 , e2);
            edges[2].setRow(0 , rev(e5));
            edges[5].setRow(0 , rev(e3));
        }
    }

    @Override
    public void down(RotateDirection direction) {
        var e2 = edges[2].getRow(2);
        var e4 = edges[4].getRow(2);
        var e3 = edges[3].getRow(2);
        var e5 = edges[5].getRow(2);
        if (direction == RotateDirection.CLOCKWISE) {
            edges[1].rotateClockwise();
            edges[4].setRow(2 , e2);
            edges[3].setRow(2 , e4);
            edges[5].setRow(2 , rev(e3));
            edges[2].setRow(2 , rev(e5));
        } else {
            edges[1].rotateClockwise();
            edges[1].rotateClockwise();
            edges[1].rotateClockwise();

            edges[4].setRow(2 , e3);
            edges[3].setRow(2 , rev(e5));
            edges[5].setRow(2 , rev(e2));
            edges[2].setRow(2 , e4);
        }
    }

    @Override
    public void back(RotateDirection direction) {
        var e0 = edges[0].getRow(0);
        var e1 = edges[1].getRow(2);
        var e2 = edges[2].getCol(0);
        var e3 = edges[3].getCol(2);
        if (direction == RotateDirection.CLOCKWISE) {
            edges[5].rotateClockwise();
            edges[0].setRow(0 , e3);
            edges[1].setRow(2 , e2);
            edges[2].setCol(0 ,rev(e0));
            edges[3].setCol(2 ,rev(e1));
        } else {
            edges[5].rotateClockwise();
            edges[5].rotateClockwise();
            edges[5].rotateClockwise();
            edges[0].setRow(0 , rev(e2));
            edges[1].setRow(2 , rev(e3));
            edges[2].setCol(0 , e1);
            edges[3].setCol(2 , e0);
        }
    }
    
    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }


    public void debug() {
        for (int i = 0; i < edges.length; i++) {
            System.out.println(i);
            for (int j = 0; j < edges[i].getParts().length; j++) {
                System.out.println(Arrays.toString(edges[i].getParts()[j]));
            }
        }
    }
}
