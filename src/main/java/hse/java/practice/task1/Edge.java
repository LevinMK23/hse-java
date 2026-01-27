package hse.java.practice.task1;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Edge {

    private final CubeColor[][] parts;

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

    @Override
    public String toString() {
        return Arrays.deepToString(parts);
    }
}
