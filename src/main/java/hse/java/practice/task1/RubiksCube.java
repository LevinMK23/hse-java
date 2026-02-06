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

    public CubeColor[][] copy(CubeColor[][] el) {
        CubeColor[][] copy = new CubeColor[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copy[i][j] = el[i][j];
            }
        }
        return copy;
    }

    public void front(RotateDirection direction) {
        CubeColor[][] parts_right = edges[3].getParts(),parts_down = edges[1].getParts(), parts_left = edges[2].getParts();
        CubeColor[][] parts_up = edges[0].getParts(), parts_front = edges[4].getParts(), parts_back = edges[5].getParts();

        CubeColor[][] color_up = copy(parts_up), color_right = copy(parts_right), color_left = copy(parts_left);
        CubeColor[][] color_down = copy(parts_down), color_front = copy(parts_front), color_back = copy(parts_back);

        switch (direction) {
            case CLOCKWISE -> {
                for (int i = 0; i < 3; i++) {
                    parts_right[i][0] = color_up[2][i];
                    parts_down[0][i] = color_right[2 - i][0];
                    parts_left[i][2] = color_down[0][i];
                    parts_up[2][i] = color_left[2 - i][2];
                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        parts_front[i][j] = color_front[j][2 - i];
                    }
                }

                edges[3].setParts(parts_right);
                edges[1].setParts(parts_down);
                edges[2].setParts(parts_left);
                edges[0].setParts(parts_up);
                edges[4].setParts(parts_front);
            }

            case COUNTERCLOCKWISE -> {
                front(RotateDirection.CLOCKWISE);
                front(RotateDirection.CLOCKWISE);
                front(RotateDirection.CLOCKWISE);
            }
        }
    }

    @Override
    public void back(RotateDirection direction) {
        CubeColor[][] parts_right = edges[3].getParts(),parts_down = edges[1].getParts(), parts_left = edges[2].getParts();
        CubeColor[][] parts_up = edges[0].getParts(), parts_front = edges[4].getParts(), parts_back = edges[5].getParts();

        CubeColor[][] color_up = copy(parts_up), color_right = copy(parts_right), color_left = copy(parts_left);
        CubeColor[][] color_down = copy(parts_down), color_front = copy(parts_front), color_back = copy(parts_back);

        switch (direction) {
            case CLOCKWISE -> {
                for (int i = 0; i < 3; i++) {
                    parts_right[i][2] = color_up[0][i];
                    parts_down[2][i] = color_right[2 - i][2];
                    parts_left[i][0] = color_down[2][i];
                    parts_up[0][i] = color_left[2 - i][0];
                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        parts_back[i][j] = color_back[j][2 - i];
                    }
                }

                edges[3].setParts(parts_right);
                edges[1].setParts(parts_down);
                edges[2].setParts(parts_left);
                edges[0].setParts(parts_up);
                edges[5].setParts(parts_back);
            }

            case COUNTERCLOCKWISE -> {
                back(RotateDirection.CLOCKWISE);
                back(RotateDirection.CLOCKWISE);
                back(RotateDirection.CLOCKWISE);
            }
        }
    }


    public void up(RotateDirection direction) {
        CubeColor[][] parts_right = edges[3].getParts(),parts_down = edges[1].getParts(), parts_left = edges[2].getParts();
        CubeColor[][] parts_up = edges[0].getParts(), parts_front = edges[4].getParts(), parts_back = edges[5].getParts();

        CubeColor[][] color_up = copy(parts_up), color_right = copy(parts_right), color_left = copy(parts_left);
        CubeColor[][] color_down = copy(parts_down), color_front = copy(parts_front), color_back = copy(parts_back);

        switch (direction) {
            case CLOCKWISE -> {
                for (int i = 0; i < 3; i++) {
                    parts_right[0][i] = color_back[0][i];
                    parts_front[0][i] = color_right[0][i];
                    parts_left[0][i] = color_front[0][i];
                    parts_back[0][i] = color_left[0][i];
                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        parts_up[i][j] = color_up[j][2 - i];
                    }
                }

                edges[3].setParts(parts_right);
                edges[5].setParts(parts_back);
                edges[2].setParts(parts_left);
                edges[0].setParts(parts_up);
                edges[4].setParts(parts_front);
            }

            case COUNTERCLOCKWISE -> {
                up(RotateDirection.CLOCKWISE);
                up(RotateDirection.CLOCKWISE);
                up(RotateDirection.CLOCKWISE);
            }
        }
    }

    @Override
    public void down(RotateDirection direction) {
        CubeColor[][] parts_right = edges[3].getParts(),parts_down = edges[1].getParts(), parts_left = edges[2].getParts();
        CubeColor[][] parts_up = edges[0].getParts(), parts_front = edges[4].getParts(), parts_back = edges[5].getParts();

        CubeColor[][] color_up = copy(parts_up), color_right = copy(parts_right), color_left = copy(parts_left);
        CubeColor[][] color_down = copy(parts_down), color_front = copy(parts_front), color_back = copy(parts_back);

        switch (direction) {
            case CLOCKWISE -> {
                for (int i = 0; i < 3; i++) {
                    parts_right[2][i] = color_back[2][i];
                    parts_front[2][i] = color_right[2][i];
                    parts_left[2][i] = color_front[2][i];
                    parts_back[2][i] = color_left[2][i];
                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        parts_down[i][j] = color_down[j][2 - i];
                    }
                }

                edges[3].setParts(parts_right);
                edges[1].setParts(parts_down);
                edges[2].setParts(parts_left);
                edges[5].setParts(parts_back);
                edges[4].setParts(parts_front);
            }

            case COUNTERCLOCKWISE -> {
                down(RotateDirection.CLOCKWISE);
                down(RotateDirection.CLOCKWISE);
                down(RotateDirection.CLOCKWISE);
            }
        }

    }

    @Override
    public void left(RotateDirection direction) {
        CubeColor[][] parts_right = edges[3].getParts(),parts_down = edges[1].getParts(), parts_left = edges[2].getParts();
        CubeColor[][] parts_up = edges[0].getParts(), parts_front = edges[4].getParts(), parts_back = edges[5].getParts();

        CubeColor[][] color_up = copy(parts_up), color_right = copy(parts_right), color_left = copy(parts_left);
        CubeColor[][] color_down = copy(parts_down), color_front = copy(parts_front), color_back = copy(parts_back);

        switch (direction) {
            case CLOCKWISE -> {
                for (int i = 0; i < 3; i++) {
                    parts_front[i][0] = color_down[i][0];
                    parts_up[i][0] = color_front[i][0];
                    parts_back[i][2] = color_up[2 - i][0];
                    parts_down[i][0] = color_back[2 - i][2];

                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        parts_left[i][j] = color_left[2 - j][i];
                    }
                }

                edges[5].setParts(parts_back);
                edges[1].setParts(parts_down);
                edges[2].setParts(parts_left);
                edges[0].setParts(parts_up);
                edges[4].setParts(parts_front);
            }

            case COUNTERCLOCKWISE -> {
                left(RotateDirection.CLOCKWISE);
                left(RotateDirection.CLOCKWISE);
                left(RotateDirection.CLOCKWISE);
            }
        }

    }

    @Override
    public void right(RotateDirection direction) {
        CubeColor[][] parts_right = edges[3].getParts(),parts_down = edges[1].getParts(), parts_left = edges[2].getParts();
        CubeColor[][] parts_up = edges[0].getParts(), parts_front = edges[4].getParts(), parts_back = edges[5].getParts();

        CubeColor[][] color_up = copy(parts_up), color_right = copy(parts_right), color_left = copy(parts_left);
        CubeColor[][] color_down = copy(parts_down), color_front = copy(parts_front), color_back = copy(parts_back);

        switch (direction) {
            case CLOCKWISE -> {
                for (int i = 0; i < 3; i++) {
                    parts_up[i][2] = color_front[i][2];
                    parts_back[i][0] = color_up[2 - i][2];
                    parts_down[i][2] = color_back[2 - i][0];
                    parts_front[i][2] = color_down[i][2];

                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        parts_right[i][j] = color_right[j][2 - i];
                    }
                }

                edges[3].setParts(parts_right);
                edges[1].setParts(parts_down);
                edges[5].setParts(parts_back);
                edges[0].setParts(parts_up);
                edges[4].setParts(parts_front);
            }

            case COUNTERCLOCKWISE -> {
                right(RotateDirection.CLOCKWISE);
                right(RotateDirection.CLOCKWISE);
                right(RotateDirection.CLOCKWISE);
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
