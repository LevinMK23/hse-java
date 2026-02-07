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

    private void swap(int r1, int c1, int r2, int c2) {
        CubeColor temp = parts[r2][c2];
        parts[r2][c2] = parts[r1][c1];
        parts[r1][c1] = temp;
    }

    private void transpose() {
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 3; j++) {
                swap(i, j, j, i);
            }
        }
    }

    private void swapHorizontally() {
        for (int i = 0; i < 3; i++) {
            swap(0, i, 2, i);
        }
    }

    private void swapVertically() {
        for (int i = 0; i < 3; i++) {
            swap(i, 0, i, 2);
        }
    }

    public void rotate(RotateDirection direction) {
        transpose();
        if (direction == RotateDirection.CLOCKWISE) {
            swapHorizontally();
        } else {
            swapVertically();
        }
    }

    public CubeColor[] getRow(int i) {
        return parts[i].clone();
    }

    public CubeColor[] getColumn(int i) {
        CubeColor[] result = new CubeColor[3];
        for (int j = 0; j < 3; j++) {
            result[j] = parts[j][i];
        }
        return result;
    }

    public void setRow(int i, CubeColor[] newRow) {
        for (int j = 0; j < 3; j++) {
            parts[i][j] = newRow[j];
        }
    }

    public void setColumn(int i, CubeColor[] newColumn) {
        for (int j = 0; j < 3; j++) {
            parts[j][i] = newColumn[j];
        }
    }

    @Override
    public String toString() {
        return Arrays.deepToString(parts);
    }
}
