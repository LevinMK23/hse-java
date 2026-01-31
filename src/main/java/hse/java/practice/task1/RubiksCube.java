package hse.java.practice.task1;

import java.util.Arrays;

public class RubiksCube implements Cube {
    private final int[] state;
    private static final int EDGES_COUNT = 6;
    private final Edge[] edges = new Edge[EDGES_COUNT];

    /**
     * Создать валидный собранный кубик
     * грани разместить по ордеру в енуме цветов
     * грань 0 -> цвет 0
     * грань 1 -> цвет 1
     * и тд 3
     */
    public RubiksCube() {
        state = new int[49];
        for (int i = 1; i <= 48; i++) {
            state[i] = i;
        }
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            edges[i] = new Edge(colors[i]);
        }
    }

    private void applyCycles(int[][] cycles) {
        for (int[] cycle : cycles) {
            applyCycle(cycle);
        }
    }

    private void applyCycle(int[] cycle) {
        int first = state[cycle[cycle.length - 1]];
        for (int i = cycle.length - 1; i > 0; i--) {
            state[cycle[i]] = state[cycle[i - 1]];
        }
        state[cycle[0]] = first;
    }

    @Override
    public void up(RotateDirection direction) {
        applyCycles(direction == RotateDirection.CLOCKWISE ?
                RotationPermutations.U_CW : RotationPermutations.U_CCW);
    }

    @Override
    public void down(RotateDirection direction) {
        applyCycles(direction == RotateDirection.CLOCKWISE ?
                RotationPermutations.D_CW : RotationPermutations.D_CCW);
    }

    @Override
    public void left(RotateDirection direction) {
        applyCycles(direction == RotateDirection.CLOCKWISE ?
                RotationPermutations.L_CW : RotationPermutations.L_CCW);
    }

    @Override
    public void right(RotateDirection direction) {
        applyCycles(direction == RotateDirection.CLOCKWISE ?
                RotationPermutations.R_CW : RotationPermutations.R_CCW);
    }

//    @Override
//    public void front(RotateDirection direction) {
//        applyCycles(direction == RotateDirection.CLOCKWISE ?
//                RotationPermutations.F_CW : RotationPermutations.F_CCW);
//    }

    @Override
    public void back(RotateDirection direction) {
        applyCycles(direction == RotateDirection.CLOCKWISE ?
                RotationPermutations.B_CW : RotationPermutations.B_CCW);
    }


    public Edge[] getEdges() {
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < EDGES_COUNT; i++) {
            CubeColor[][] parts = new CubeColor[3][3];
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    int pos = x + y * 3 + 1;
                    if (pos == 5) {
                        parts[x][y] = colors[i];
                        continue;
                    }
                    if (pos > 5) {
                        pos--;
                    }
                    parts[x][y] = colors[(state[pos + i * 8]-1)/8];
                }
            }
            edges[i].setParts(parts);
        }
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}