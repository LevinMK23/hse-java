package hse.java.practice.task1;

import java.util.Arrays;

/**
 * Необходимо реализовать интерфейс Cube
 * При повороте передней грани, меняются верх низ право и лево
 */
public class RubiksCube implements Cube {


    private static final int EDGES_COUNT = 6;
    private static final int CUBE_DIMENSION = 3;
    private static final int CHANGEABLE = 48;
    private static final int[] FACE_ORDER = {2, 4, 3, 5, 0, 1};

    private final Edge[] edges = new Edge[EDGES_COUNT];
    private final int[] perm = new int[CHANGEABLE + 1];


    private CubeColor

    /**
     * Создать валидный собранный кубик
     * грани разместить по ордеру в енуме цветов
     * грань 0 -> цвет 0
     * грань 1 -> цвет 1
     * ...
     */
    public RubiksCube() {
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < EDGES_COUNT; i++) {
            edges[i] = new Edge(colors[i]);
        }
        for (int i = 1; i <= CHANGEABLE; i++) {
            perm[i] = i;
        }
    }

    @Override
    public void up(RotateDirection direction) {
        turn(direction, U);
    }

    @Override
    public void down(RotateDirection direction) {
        turn(direction, D);
    }

    @Override
    public void left(RotateDirection direction) {
        turn(direction, L);
    }

    @Override
    public void right(RotateDirection direction) {
        turn(direction, R);
    }

    @Override
    public void front(RotateDirection direction) {
        turn(direction, F);
    }

    @Override
    public void back(RotateDirection direction) {
        turn(direction, B);
    }

    private void turn(RotateDirection direction, int[][] matrix_of_perm) {
        for (int[] row : matrix_of_perm) {
            if (direction.CLOCKWISE) {
                direct_rotate(row);
            }
            else {
                reverse_rotate(row);
            }
        }
    }

    private void direct_rotate(int[] row) {
        int last = perm[row[row.length - 1]];
        for (int i = 0; i < row.length; i++) {
            int current = perm[row[i]];
            perm[row[i]] = last;
            last = cur;
        }
    }

    private void reverse_rotate(int[] row) {
        int first = perm[row[0]];
        for (int i = row.length - 1; i >= 0; i--) {
            int current = perm[row[i]];
            perm[row[i]] = first;
            first = current;
        }
    }

    private static final int[][] U = {
        {33, 35, 40, 38},
        {34, 37, 39, 36},
        {25, 17, 9, 1},
        {26, 18, 10, 2},
        {27, 19, 11, 3}
    };

    private static final int[][] D = {
		{41, 43, 48, 46},
		{42, 45, 47, 44},
		{6, 14, 22, 30},
		{7, 15, 23, 31},
		{8, 16, 24, 32}
	};

    private static final int[][] L = {
		{1, 3, 8, 6},
		{2, 5, 7, 4},
		{33, 9, 41, 32},
		{36, 12, 44, 29},
		{38, 14, 46, 27}
	};

    private static final int[][] R = {
		{17, 19, 24, 22},
		{18, 21, 23, 20},
		{48, 16, 40, 25},
		{45, 13, 37, 28},
		{43, 11, 35, 30}
	};

    private static final int[][] F = {
		{9, 11, 16, 14},
		{10, 13, 15, 12},
		{38, 17, 43, 8},
		{39, 20, 42, 5},
		{40, 22, 41, 3}
	};

    private static final int[][] B = {
		{25, 27, 32, 30},
		{26, 29, 31, 28},
		{19, 33, 6, 48},
		{21, 34, 4, 47},
		{24, 35, 1, 46}
	};


    public Edge[] getEdges() {
        for (int i = 0; i < EDGES_COUNT; i++) {
            edges[FACE_ORDER[i]].setParts(build(i));
        }
        return edges;
    }

    private CubeColor[][] build(int i) {
        CubeColor[] colors = CubeColor.values();
        CubeColor[][] result = new CubeColor[CUBE_DIMENSION][CUBE_DIMENSION];
        CubeColor center = colors[FACE_ORDER[i]];
        for (int j = 0; j < CUBE_DIMENSION; j++) {
            for (int k = 0; k < CUBE_DIMENSION; k++) {
                if (j == 1 && k == 1) {
                    result[j][k] = center;
                }
                else {
                    int current_idx = get_idx(k, j);
                    int destination = (perm[i * 8 + current_idx] - 1) / 8;
                    result[j][k] = colors[FACE_ORDER[destination]];
                }
            }
        }
    }

    private int get_idx(int k, int j) {
        int current = k + j * CUBE_DIMENSION + 1;
        return current > 5 ? current - 1 : current;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}
