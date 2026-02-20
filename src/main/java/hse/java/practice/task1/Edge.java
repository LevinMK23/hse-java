package hse.java.practice.task1;

import java.util.Arrays;

public class Edge {

    private static final int ROW_LENGTH = 3;
    private CubeColor[][] parts;

    public Edge(CubeColor[][] parts) {
        this.parts = parts;
    }

    public Edge(CubeColor color) {
        this.parts = new CubeColor[ROW_LENGTH][ROW_LENGTH];
        for (int i = 0; i < ROW_LENGTH; i++) {
            for (int j = 0; j < ROW_LENGTH; j++) {
                parts[i][j] = color;
            }
        }
    }

    public Edge() {
        parts = new CubeColor[ROW_LENGTH][ROW_LENGTH];
    }

    public CubeColor[][] getParts() {
        return parts;
    }

    public void setParts(CubeColor[][] parts) {
        this.parts = parts;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(parts);
    }
}
