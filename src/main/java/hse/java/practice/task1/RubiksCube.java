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

    private void RotateItself(RotateDirection direction, Integer s) {
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor tmp = edges[s].getParts()[0][0];
            edges[s].getParts()[0][0] = edges[s].getParts()[2][0];
            edges[s].getParts()[2][0] = edges[s].getParts()[2][2];
            edges[s].getParts()[2][2] = edges[s].getParts()[0][2];
            edges[s].getParts()[0][2] = tmp;
            tmp = edges[s].getParts()[0][1];
            edges[s].getParts()[0][1] = edges[s].getParts()[1][0];
            edges[s].getParts()[1][0] = edges[s].getParts()[2][1];
            edges[s].getParts()[2][1] = edges[s].getParts()[1][2];
            edges[s].getParts()[1][2] = tmp;
        }
        else if (direction == RotateDirection.COUNTERCLOCKWISE) {
            CubeColor tmp = edges[s].getParts()[0][0];
            edges[s].getParts()[0][0] = edges[s].getParts()[0][2];
            edges[s].getParts()[0][2] = edges[s].getParts()[2][2];
            edges[s].getParts()[2][2] = edges[s].getParts()[2][0];
            edges[s].getParts()[2][0] = tmp;
            tmp = edges[s].getParts()[0][1];
            edges[s].getParts()[0][1] = edges[s].getParts()[1][2];
            edges[s].getParts()[1][2] = edges[s].getParts()[2][1];
            edges[s].getParts()[2][1] = edges[s].getParts()[1][0];
            edges[s].getParts()[1][0] = tmp;
        }
    }

    public void front(RotateDirection direction) {
        RotateItself(direction,4);
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] up_color = edges[0].getParts()[2].clone();
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[2][i] = edges[2].getParts()[2 - i][2];
                edges[2].getParts()[2 - i][2] = edges[1].getParts()[0][2 - i];
                edges[1].getParts()[0][2 - i] = edges[3].getParts()[i][0];
                edges[3].getParts()[i][0] = up_color[i];
            }
        }
        else if (direction == RotateDirection.COUNTERCLOCKWISE) {
            CubeColor[] up_color = edges[0].getParts()[2].clone();
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[2][i] = edges[3].getParts()[i][0];
                edges[3].getParts()[i][0] = edges[1].getParts()[0][2-i];
                edges[1].getParts()[0][2-i] = edges[2].getParts()[2-i][2];
                edges[2].getParts()[2-i][2] = up_color[i];
            }
        }
    }

    public void back(RotateDirection direction) {
        RotateItself(direction,5);
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] up_color = edges[0].getParts()[0].clone();
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[0][i] = edges[3].getParts()[i][2];
                edges[3].getParts()[i][2] = edges[1].getParts()[2][2-i];
                edges[1].getParts()[2][2-i] = edges[2].getParts()[2-i][0];
                edges[2].getParts()[2-i][0] = up_color[i];
            }
        }
        else if (direction == RotateDirection.COUNTERCLOCKWISE) {
            CubeColor[] up_color = edges[0].getParts()[0].clone()  ;
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[0][i] = edges[2].getParts()[2-i][0];
                edges[2].getParts()[2-i][0] = edges[1].getParts()[2][2-i];
                edges[1].getParts()[2][2-i] = edges[3].getParts()[i][2];
                edges[3].getParts()[i][2] = up_color[i];
            }
        }
    }

    public void up(RotateDirection direction) {
        RotateItself(direction,0);
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] front_color = edges[4].getParts()[0].clone();
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[0][i] = edges[3].getParts()[0][i];
                edges[3].getParts()[0][i] = edges[5].getParts()[0][i];
                edges[5].getParts()[0][i] = edges[2].getParts()[0][i];
                edges[2].getParts()[0][i] = front_color[i];
            }
        }
        else if (direction == RotateDirection.COUNTERCLOCKWISE) {
            CubeColor[] front_color = edges[4].getParts()[0].clone()  ;
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[0][i] = edges[2].getParts()[0][i];
                edges[2].getParts()[0][i] = edges[5].getParts()[0][i];
                edges[5].getParts()[0][i] = edges[3].getParts()[0][i];
                edges[3].getParts()[0][i] = front_color[i];
            }
        }
    }

    public void down(RotateDirection direction) {
        RotateItself(direction,1);
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] front_color = edges[4].getParts()[2].clone();
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[2][i] = edges[2].getParts()[2][i];
                edges[2].getParts()[2][i] = edges[5].getParts()[2][i];
                edges[5].getParts()[2][i] = edges[3].getParts()[2][i];
                edges[3].getParts()[2][i] = front_color[i];
            }
        }
        else if (direction == RotateDirection.COUNTERCLOCKWISE) {
            CubeColor[] front_color = edges[4].getParts()[2].clone()  ;
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[2][i] = edges[3].getParts()[2][i];
                edges[2].getParts()[2][i] = edges[5].getParts()[2][i];
                edges[5].getParts()[2][i] = edges[2].getParts()[2][i];
                edges[3].getParts()[2][i] = front_color[i];
            }
        }
    }

    public void left(RotateDirection direction) {
        RotateItself(direction,2);
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] front_color = new CubeColor[3];
            for (int i = 0; i < 3; i++) {
                front_color[i] = edges[4].getParts()[i][0];
            }
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[i][0] = edges[0].getParts()[i][0];
                edges[0].getParts()[i][0] = edges[5].getParts()[i][2];
                edges[5].getParts()[i][2] = edges[1].getParts()[i][2];
                edges[1].getParts()[i][2] = front_color[i];
            }
        }
        else if (direction == RotateDirection.COUNTERCLOCKWISE) {
            CubeColor[] front_color = new CubeColor[3];
            for (int i = 0; i < 3; i++) {
                front_color[i] = edges[4].getParts()[i][0];
            }
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[i][0] = edges[1].getParts()[2-i][2];
                edges[1].getParts()[2-i][2] = edges[5].getParts()[2-i][2];
                edges[5].getParts()[2-i][2] = edges[0].getParts()[i][0];
                edges[0].getParts()[i][0] = front_color[i];
            }
        }
    }

    public void right(RotateDirection direction) {
        RotateItself(direction,3);
        if (direction == RotateDirection.CLOCKWISE) {
            CubeColor[] front_color = new CubeColor[3];
            for (int i = 0; i < 3; i++) {
                front_color[i] = edges[4].getParts()[i][2];
            }
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[i][2] = edges[1].getParts()[2-i][0];
                edges[1].getParts()[2-i][0] = edges[5].getParts()[2-i][0];
                edges[5].getParts()[2-i][0] = edges[0].getParts()[i][2];
                edges[0].getParts()[i][2] = front_color[i];
            }
        }
        else if (direction == RotateDirection.COUNTERCLOCKWISE) {
            CubeColor[] front_color = new CubeColor[3];
            for (int i = 0; i < 3; i++) {
                front_color[i] = edges[4].getParts()[i][2];
            }
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[i][2] = edges[0].getParts()[i][2];
                edges[0].getParts()[i][2] = edges[5].getParts()[2-i][0];
                edges[5].getParts()[2-i][0] = edges[1].getParts()[2-i][0];
                edges[1].getParts()[2-i][0] = front_color[i];
            }
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
