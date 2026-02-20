package hse.java.practice.task1;

import java.util.Arrays;


/**
 * Необходимо реализовать интерфейс Cube
 * При повороте передней грани, меняются верх низ право и лево
 */
public class RubiksCube implements Cube {

    private static final int EDGES_COUNT = 6;
    private static final int ROW_LENGTH = 3;

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
        CubeColor[] front = get_row(0, 4);
        CubeColor[] right = get_row(0, 3);
        CubeColor[] back = get_row(0, 5);
        CubeColor[] left = get_row(0, 2);

        if (direction == RotateDirection.CLOCKWISE) {
            matrix_rotate_right(0);
            set_row(0, 2, front);
            set_row(0, 5, left);
            set_row(0, 3, back);
            set_row(0, 4, right);
        } else {
            matrix_rotate_left(0);
            set_row(0, 4, left);
            set_row(0, 3, front);
            set_row(0, 5, right);
            set_row(0, 2, back);
        }
    }

    @Override
    public void down(RotateDirection direction) {
        CubeColor[] front = get_row(2, 4);
        CubeColor[] right = get_row(2, 3);
        CubeColor[] back = get_row(2, 5);
        CubeColor[] left = get_row(2, 2);

        if (direction == RotateDirection.CLOCKWISE) {
            matrix_rotate_right(1);
            set_row(2, 3, front);
            set_row(2, 5, right);
            set_row(2, 2, back);
            set_row(2, 4, left);
        } else {
            matrix_rotate_left(1);
            set_row(2, 2, front);
            set_row(2, 4, right);
            set_row(2, 3, back);
            set_row(2, 5, left);
        }
    }

    @Override
    public void left(RotateDirection direction) {
        CubeColor[] up = get_column(0, 0);
        CubeColor[] front = get_column(0, 4);
        CubeColor[] down = get_column(0, 1);
        CubeColor[] back = reverse(get_column(2, 5));

        if (direction == RotateDirection.CLOCKWISE) {
            matrix_rotate_right(2);
            set_column(0, 4, up);
            set_column(0, 1, front);
            set_column(2, 5, reverse(down));
            set_column(0, 0, back);
        } else {
            matrix_rotate_left(2);
            set_column(0, 0, front);
            set_column(0, 4, down);
            set_column(0, 1, back);
            set_column(2, 5, reverse(up));
        }
    }

    @Override
    public void right(RotateDirection direction) {
        CubeColor[] up = get_column(2, 0);
        CubeColor[] back = reverse(get_column(0, 5));
        CubeColor[] down = get_column(2, 1);
        CubeColor[] front = get_column(2, 4);

        if (direction == RotateDirection.CLOCKWISE) {
            matrix_rotate_right(3);
            set_column(0, 5, reverse(up));
            set_column(2, 1, back);
            set_column(2, 4, down);
            set_column(2, 0, front);
        } else {
            matrix_rotate_left(3);
            set_column(2, 0, back);
            set_column(0, 5, reverse(down));
            set_column(2, 1, front);
            set_column(2, 4, up);
        }
    }

    @Override
    public void front(RotateDirection direction) {
        CubeColor[] up = get_row(2, 0);
        CubeColor[] right = get_column(0, 3);
        CubeColor[] down = reverse(get_row(0, 1));
        CubeColor[] left = reverse(get_column(2, 2));

        if (direction == RotateDirection.CLOCKWISE) {
            matrix_rotate_right(4);
            set_column(0, 3, up);
            set_row(0, 1, reverse(right));
            set_column(2, 2, reverse(down));
            set_row(2, 0, left);
        } else {
            matrix_rotate_left(4);
            set_row(2, 0, right);
            set_column(0, 3, down);
            set_row(0, 1, reverse(left));
            set_column(2, 2, reverse(up));
        }
    }

    @Override
    public void back(RotateDirection direction) {
        CubeColor[] up = get_row(0, 0);
        CubeColor[] left = reverse(get_column(0, 2));
        CubeColor[] down = reverse(get_row(2, 1));
        CubeColor[] right = get_column(2, 3);

        if (direction == RotateDirection.CLOCKWISE) {
            matrix_rotate_right(5);
            set_column(0, 2, reverse(up));
            set_row(2, 1, reverse(left));
            set_column(2, 3, down);
            set_row(0, 0, right);
        } else {
            matrix_rotate_left(5);
            set_row(0, 0, left);
            set_column(2, 3, up);
            set_row(2, 1, reverse(right));
            set_column(0, 2, reverse(down));
        }
    }

    public Edge[] getEdges() {
        return edges;
    }

    private CubeColor[] get_column(int column, int side_number) {
        CubeColor[] column_vector = new CubeColor[ROW_LENGTH];
        for (int i = 0; i < ROW_LENGTH; i++) {
            column_vector[i] = edges[side_number].getParts()[i][column];
        }
        return column_vector;
    }

    private void set_column(int column, int side_number, CubeColor[] values) {
        for (int i = 0; i < ROW_LENGTH; i++) {
            edges[side_number].getParts()[i][column] = values[i];
        }
    }

    private CubeColor[] get_row(int row, int side_number) {
        CubeColor[] row_vector = new CubeColor[ROW_LENGTH];
        for (int i = 0; i < ROW_LENGTH; i++) {
            row_vector[i] = edges[side_number].getParts()[row][i];
        }
        return row_vector;
    }

    private void set_row(int row, int side_number, CubeColor[] values) {
        for (int i = 0; i < ROW_LENGTH; i++) {
            edges[side_number].getParts()[row][i] = values[i];
        }
    }

    private CubeColor[] reverse(CubeColor[] arr) {
        return new CubeColor[]{arr[2], arr[1], arr[0]};
    }

    private void matrix_rotate_left(int side_number) {
        CubeColor[][] rotated_side = new CubeColor[ROW_LENGTH][ROW_LENGTH];
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < ROW_LENGTH; j++) {
                rotated_side[ROW_LENGTH - j - 1][i] = edges[side_number].getParts()[i][j];
            }
        }
        edges[side_number].setParts(rotated_side);
    }

    private void matrix_rotate_right(int side_number) {
        CubeColor[][] rotated_side = new CubeColor[ROW_LENGTH][ROW_LENGTH];
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < ROW_LENGTH; j++) {
                rotated_side[j][ROW_LENGTH - i - 1] = edges[side_number].getParts()[i][j];
            }
        }
        edges[side_number].setParts(rotated_side);
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}