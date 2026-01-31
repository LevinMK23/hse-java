package hse.java.practice.task1;

import java.util.HashMap;
import java.util.Map;

public class RotationPermutations {
    static final int[][] U_CW = {
            {33, 35, 40, 38},
            {34, 37, 39, 36},
            {25, 17, 9, 1},
            {26, 18, 10, 2},
            {27, 19, 11, 3}
    };

    static final int[][] U_CCW = reverseCycles(U_CW);

    static final int[][] D_CW = {
            {41, 43, 48, 46},
            {42, 45, 47, 44},
            {6, 14, 22, 30},
            {7, 15, 23, 31},
            {8, 16, 24, 32}
    };

    static final int[][] D_CCW = reverseCycles(D_CW);

    static final int[][] L_CW = {
            {1, 3, 8, 6},
            {2, 5, 7, 4},
            {33, 9, 41, 32},
            {36, 12, 44, 29},
            {38, 14, 46, 27}
    };

    static final int[][] L_CCW = reverseCycles(L_CW);

    static final int[][] R_CW = {
            {17, 19, 24, 22},
            {18, 21, 23, 20},
            {48, 16, 40, 25},
            {45, 13, 37, 28},
            {43, 11, 35, 30}
    };

    static final int[][] R_CCW = reverseCycles(R_CW);

    static final int[][] F_CW = {
            {9, 11, 16, 14},
            {10, 13, 15, 12},
            {38, 17, 43, 8},
            {39, 20, 42, 5},
            {40, 22, 41, 3}
    };

    static final int[][] F_CCW = reverseCycles(F_CW);

    static final int[][] B_CCW = {
            {25, 27, 32, 30},
            {26, 29, 31, 28},
            {19, 33, 6, 48},
            {21, 34, 4, 47},
            {24, 35, 1, 46}
    };

    static final int[][] B_CW = reverseCycles(B_CCW);

    private static int[][] reverseCycles(int[][] cycles) {
        int[][] reversed = new int[cycles.length][];
        for (int i = 0; i < cycles.length; i++) {
            int[] cycle = cycles[i];
            int[] revCycle = new int[cycle.length];
            revCycle[0] = cycle[0];
            for (int j = 1; j < cycle.length; j++) {
                revCycle[j] = cycle[cycle.length - j];
            }
            reversed[i] = revCycle;
        }
        return reversed;
    }

    /**
     * Эта штука нужна, так как в кубике рубика на вики грани расположены по-другому и я не сильно хочу разбираться в том, где какие перестановки
     */
    static final Map<Integer, Integer> fromWikiToHw = new HashMap<>() {{
        put(0, 2);
        put(1, 4);
        put(2, 3);
        put(3, 5);
        put(4, 0);
        put(5, 1);
    }};
}
