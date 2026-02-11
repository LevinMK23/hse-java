package hse.java.practice.task1;

import java.util.Arrays;

/**
 * Необходимо реализовать интерфейс Cube
 * При повороте передней грани, меняются верх низ право и лево
 */

public class RubiksCube implements Cube {

    private static final int EDGES_COUNT = 6;
    private static final int side_size = 3;

    private final Edge[] edges = new Edge[EDGES_COUNT];

    private static final int[][] normal = {
            {0, 1, 0}, {0, -1, 0}, {-1, 0, 0},
            {1, 0, 0}, {0, 0, 1}, {0, 0, -1}
    };

    private static final int[][] right = {
            {1, 0, 0}, {1, 0, 0}, {0, 0, 1},
            {0, 0, -1}, {1, 0, 0}, {-1, 0, 0}
    };

    private static final int[][] down = {
            {0, 0, 1}, {0, 0, -1}, {0, -1, 0},
            {0, -1, 0}, {0, -1, 0}, {0, -1, 0}
    };

    private static final int[][][] rotation_permutation = {
            {{0, 2, 1}, {0, 2, 1}},
            {{2, 1, 0}, {2, 1, 0}},
            {{1, 0, 2}, {1, 0, 2}}
    };

    private static final int[][][] rotation_sign = {
            {{1, 1, -1}, {1, -1, 1}},
            {{-1, 1, 1}, {1, 1, -1}},
            {{1, -1, 1}, {-1, 1, 1}}
    };

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
        rotate_face(EdgePosition.UP, direction);
    }

    @Override
    public void down(RotateDirection direction) {
        rotate_face(EdgePosition.DOWN, direction);
    }

    @Override
    public void left(RotateDirection direction) {
        rotate_face(EdgePosition.LEFT, direction);
    }

    @Override
    public void right(RotateDirection direction) {
        rotate_face(EdgePosition.RIGHT, direction);
    }

    @Override
    public void front(RotateDirection direction) {
        rotate_face(EdgePosition.FRONT, direction);
    }

    @Override
    public void back(RotateDirection direction) {
        rotate_face(EdgePosition.BACK, direction);
    }

    private void rotate_face(EdgePosition face, RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            rotate_face(face, RotateDirection.CLOCKWISE);
            rotate_face(face, RotateDirection.CLOCKWISE);
            rotate_face(face, RotateDirection.CLOCKWISE);
            return;
        }

        int rotation_axis = axis_for_face(face);
        int fixed_layer = layer_for_face(face);
        boolean is_clockwise_internal = fixed_layer != -1;

        CubeColor[][][] cur_state = read_state();
        CubeColor[][][] next_state = copy_state(cur_state);

        for (int i = 0; i < EDGES_COUNT; i += 1) {
            for (int row = 0; row < side_size; row += 1) {
                for (int col = 0; col < side_size; col += 1) {
                    int dx = dx(i, row, col);
                    int dy = dy(i, row, col);
                    int dz = dz(i, row, col);

                    if (axis_component(rotation_axis, dx, dy, dz) != fixed_layer) continue;

                    int[] rotated_pos = rotate_vector(rotation_axis, is_clockwise_internal, dx, dy, dz);
                    int[] rotated_normal = rotate_vector(rotation_axis, is_clockwise_internal, normal[i][0], normal[i][1], normal[i][2]);

                    int target = by_normal(rotated_normal[0], rotated_normal[1], rotated_normal[2]);
                    int[] target_coords = coords(target, rotated_pos[0], rotated_pos[1], rotated_pos[2]);

                    next_state[target][target_coords[0]][target_coords[1]] = cur_state[i][row][col];
                }
            }
        }

        for (int i = 0; i < EDGES_COUNT; i += 1) edges[i].setParts(next_state[i]);
    }

    private CubeColor[][][] read_state() {
        CubeColor[][][] state = new CubeColor[EDGES_COUNT][side_size][side_size];
        for (int i = 0; i < EDGES_COUNT; i += 1) {
            CubeColor[][] parts = edges[i].getParts();
            for (int row = 0; row < side_size; row += 1) {
                for (int col = 0; col < side_size; col += 1) {
                    state[i][row][col] = parts[row][col];
                }
            }
        }
        return state;
    }

    private CubeColor[][][] copy_state(CubeColor[][][] source) {
        CubeColor[][][] copy = new CubeColor[EDGES_COUNT][side_size][side_size];
        for (int i = 0; i < EDGES_COUNT; i += 1) {
            for (int row = 0; row < side_size; row += 1) {
                for (int col = 0; col < side_size; col += 1) {
                    copy[i][row][col] = source[i][row][col];
                }
            }
        }
        return copy;
    }

    private int axis_for_face(EdgePosition face) {
        if (face == EdgePosition.LEFT || face == EdgePosition.RIGHT) return 0;
        if (face == EdgePosition.UP || face == EdgePosition.DOWN) return 1;
        return 2;
    }

    private int layer_for_face(EdgePosition face) {
        if (face == EdgePosition.UP || face == EdgePosition.RIGHT || face == EdgePosition.FRONT) return 1;
        return -1;
    }

    private int axis_component(int rotation_axis, int dx, int dy, int dz) {
        if (rotation_axis == 0) return dx;
        if (rotation_axis == 1) return dy;
        return dz;
    }

    private int dx(int i, int row, int col) {
        int col_shift = col - 1;
        int row_shift = row - 1;
        return normal[i][0] + col_shift * right[i][0] + row_shift * down[i][0];
    }

    private int dy(int i, int row, int col) {
        int col_shift = col - 1;
        int row_shift = row - 1;
        return normal[i][1] + col_shift * right[i][1] + row_shift * down[i][1];
    }

    private int dz(int i, int row, int col) {
        int col_shift = col - 1;
        int row_shift = row - 1;
        return normal[i][2] + col_shift * right[i][2] + row_shift * down[i][2];
    }

    private int[] rotate_vector(int rotation_axis, boolean is_clockwise_internal, int x, int y, int z) {
        int direction = is_clockwise_internal ? 0 : 1;
        int[] input_vector = {x, y, z};
        int[] permutation = rotation_permutation[rotation_axis][direction];
        int[] sign = rotation_sign[rotation_axis][direction];
        return new int[]{sign[0] * input_vector[permutation[0]], sign[1] * input_vector[permutation[1]], sign[2] * input_vector[permutation[2]]};
    }

    private int by_normal(int normal_x, int normal_y, int normal_z) {
        if (normal_y == 1) return EdgePosition.UP.ordinal();
        if (normal_y == -1) return EdgePosition.DOWN.ordinal();
        if (normal_x == -1) return EdgePosition.LEFT.ordinal();
        if (normal_x == 1) return EdgePosition.RIGHT.ordinal();
        if (normal_z == 1) return EdgePosition.FRONT.ordinal();
        return EdgePosition.BACK.ordinal();
    }

    private int[] coords(int i, int dx, int dy, int dz) {
        int local_x = dx - normal[i][0];
        int local_y = dy - normal[i][1];
        int local_z = dz - normal[i][2];

        int col = dot(local_x, local_y, local_z, right[i]) + 1;
        int row = dot(local_x, local_y, local_z, down[i]) + 1;

        return new int[]{row, col};
    }

    private int dot(int x, int y, int z, int[] vector) {
        return x * vector[0] + y * vector[1] + z * vector[2];
    }

    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}