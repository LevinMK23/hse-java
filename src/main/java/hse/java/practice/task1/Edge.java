package hse.java.practice.task1;

import java.util.Arrays;

public class Edge {

    private CubeColor[][] parts;

    public Edge(CubeColor[][] parts) {
        this.parts = parts;
    }

    public Edge(CubeColor color) {
        this.parts = new CubeColor[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                parts[i][j] = color;
            }
        }
    }

    public Edge() {
        parts = new CubeColor[3][3];
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

    public void rotateClockwise() {
        CubeColor[][] result_parts = new CubeColor[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result_parts[j][2-i] = parts[i][j];
            }
        }
        parts = result_parts;
    }

    public void setRow(int num, CubeColor[] row) {
        parts[num] = row.clone();
    }

    public void setCol(int num, CubeColor[] col) {
        for (int i = 0; i < 3; i++) {
            parts[i][num] = col[i];
        }
    }

    public CubeColor[] getRow(int num) {
        return parts[num].clone();
    }

    public CubeColor[] getCol(int num) {
        CubeColor[] col = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            col[i] = parts[i][num];
        }
        return col;
    }
}
