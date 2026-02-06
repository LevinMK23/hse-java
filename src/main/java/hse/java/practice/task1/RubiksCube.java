package hse.java.practice.task1;

import java.util.Arrays;

public class RubiksCube implements Cube {

    private static final int EDGES_COUNT = 6;

    private final Edge[] edges = new Edge[EDGES_COUNT];

    private void rotateClockwise(CubeColor[][] grid) {
        CubeColor[][] tmp = new CubeColor[3][3];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                tmp[j][2 - i] = grid[i][j];
            }
        }
        for (int i = 0; i < 3; ++i) {
            System.arraycopy(tmp[i], 0, grid[i], 0, 3);
        }
    }

    private void rotateCounterclockwise(CubeColor[][] grid) {
        CubeColor[][] tmp = new CubeColor[3][3];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                tmp[2 - j][i] = grid[i][j];
            }
        }
        for (int i = 0; i < 3; ++i) {
            System.arraycopy(tmp[i], 0, grid[i], 0, 3);
        }
    }

    public RubiksCube() {
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            edges[i] = new Edge(colors[i]);
        }
    }

    @Override
    public void front(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(edges[4].getParts());
        } else {
            rotateCounterclockwise(edges[4].getParts());
        }

        CubeColor[] upBottom = new CubeColor[3];
        CubeColor[] rightLeft = new CubeColor[3];
        CubeColor[] downTop = new CubeColor[3];
        CubeColor[] leftRight = new CubeColor[3];

        for (int i = 0; i < 3; i++) {
            upBottom[i] = edges[0].getParts()[2][i];
            rightLeft[i] = edges[3].getParts()[i][0];
            downTop[i] = edges[1].getParts()[0][i];
            leftRight[i] = edges[2].getParts()[i][2];
        }

        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[2][i] = leftRight[2 - i];
                edges[2].getParts()[i][2] = downTop[i];
                edges[1].getParts()[0][i] = rightLeft[2 - i];
                edges[3].getParts()[i][0] = upBottom[i];
            }
        } else {
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[2][i] = rightLeft[i];
                edges[3].getParts()[i][0] = downTop[2 - i];
                edges[1].getParts()[0][i] = leftRight[i];
                edges[2].getParts()[i][2] = upBottom[2 - i];
            }
        }
    }

    @Override
    public void up(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(edges[0].getParts());
        } else {
            rotateCounterclockwise(edges[0].getParts());
        }
        CubeColor[] frontTop = new CubeColor[3];
        CubeColor[] rightTop = new CubeColor[3];
        CubeColor[] backTop = new CubeColor[3];
        CubeColor[] leftTop = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            frontTop[i] = edges[4].getParts()[0][i];
            rightTop[i] = edges[3].getParts()[0][i];
            backTop[i] = edges[5].getParts()[0][i];
            leftTop[i] = edges[2].getParts()[0][i];
        }
        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[0][i] = rightTop[i];
                edges[3].getParts()[0][i] = backTop[2 - i];
                edges[5].getParts()[0][i] = leftTop[2 - i];
                edges[2].getParts()[0][i] = frontTop[i];
            }
        } else {
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[0][i] = leftTop[i];
                edges[2].getParts()[0][i] = backTop[2 - i];
                edges[5].getParts()[0][i] = rightTop[i];
                edges[3].getParts()[0][i] = frontTop[i];
            }
        }
    }

    @Override
    public void right(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(edges[3].getParts());
        } else {
            rotateCounterclockwise(edges[3].getParts());
        }

        CubeColor[] up = new CubeColor[3];
        CubeColor[] front = new CubeColor[3];
        CubeColor[] back = new CubeColor[3];
        CubeColor[] down = new CubeColor[3];

        for (int i = 0; i < 3; i++) {
            up[i] = edges[0].getParts()[i][2];
            front[i] = edges[4].getParts()[i][2];
            back[i] = edges[5].getParts()[i][0];
            down[i] = edges[1].getParts()[i][2];
        }

        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[i][2] = back[2 - i];
                edges[4].getParts()[i][2] = up[i];
                edges[1].getParts()[i][2] = front[i];
                edges[5].getParts()[i][0] = down[2 - i];
            }
        } else {
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[i][2] = front[i];
                edges[4].getParts()[i][2] = down[i];
                edges[1].getParts()[i][2] = back[2 - i];
                edges[5].getParts()[i][0] = up[2 - i];
            }
        }
    }

    @Override
    public void left(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(edges[2].getParts());
        } else {
            rotateCounterclockwise(edges[2].getParts());
        }

        CubeColor[] up = new CubeColor[3];
        CubeColor[] front = new CubeColor[3];
        CubeColor[] back = new CubeColor[3];
        CubeColor[] down = new CubeColor[3];

        for (int i = 0; i < 3; i++) {
            up[i] = edges[0].getParts()[i][0];
            front[i] = edges[4].getParts()[i][0];
            back[i] = edges[5].getParts()[i][2];
            down[i] = edges[1].getParts()[i][0];
        }

        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[i][0] = back[2 - i];
                edges[4].getParts()[i][0] = up[i];
                edges[1].getParts()[i][0] = front[i];
                edges[5].getParts()[i][2] = down[2 - i];
            }
        } else {
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[i][0] = front[i];
                edges[4].getParts()[i][0] = down[i];
                edges[1].getParts()[i][0] = back[2 - i];
                edges[5].getParts()[i][2] = up[2 - i];
            }
        }
    }

    @Override
    public void down(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(edges[1].getParts());
        } else {
            rotateCounterclockwise(edges[1].getParts());
        }

        CubeColor[] frontBottom = new CubeColor[3];
        CubeColor[] rightBottom = new CubeColor[3];
        CubeColor[] backBottom = new CubeColor[3];
        CubeColor[] leftBottom = new CubeColor[3];

        for (int i = 0; i < 3; i++) {
            frontBottom[i] = edges[4].getParts()[2][i];
            rightBottom[i] = edges[3].getParts()[2][i];
            backBottom[i] = edges[5].getParts()[2][i];
            leftBottom[i] = edges[2].getParts()[2][i];
        }

        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[2][i] = leftBottom[i];
                edges[2].getParts()[2][i] = backBottom[2 - i];
                edges[5].getParts()[2][i] = rightBottom[2 - i];
                edges[3].getParts()[2][i] = frontBottom[i];
            }
        } else {
            for (int i = 0; i < 3; i++) {
                edges[4].getParts()[2][i] = rightBottom[i];
                edges[3].getParts()[2][i] = backBottom[2 - i];
                edges[5].getParts()[2][i] = leftBottom[2 - i];
                edges[2].getParts()[2][i] = frontBottom[i];
            }
        }
    }

    @Override
    public void back(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(edges[5].getParts());
        } else {
            rotateCounterclockwise(edges[5].getParts());
        }

        CubeColor[] upTop = new CubeColor[3];
        CubeColor[] rightRight = new CubeColor[3];
        CubeColor[] downBottom = new CubeColor[3];
        CubeColor[] leftLeft = new CubeColor[3];

        for (int i = 0; i < 3; i++) {
            upTop[i] = edges[0].getParts()[0][i];
            rightRight[i] = edges[3].getParts()[i][2];
            downBottom[i] = edges[1].getParts()[2][i];
            leftLeft[i] = edges[2].getParts()[i][0];
        }

        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[0][i] = rightRight[2 - i];
                edges[3].getParts()[i][2] = downBottom[i];
                edges[1].getParts()[2][i] = leftLeft[2 - i];
                edges[2].getParts()[i][0] = upTop[i];
            }
        } else {
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[0][i] = leftLeft[i];
                edges[2].getParts()[i][0] = downBottom[2 - i];
                edges[1].getParts()[2][i] = rightRight[i];
                edges[3].getParts()[i][2] = upTop[2 - i];
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

    /**
     * Это я писал для тестов.
     * Думаю, что не пригодится в тестирующей системе.
     */

    // @Override
    // public boolean equals(Object o) {
    // if (this == o)
    // return true;
    // if (o == null || getClass() != o.getClass())
    // return false;
    // RubiksCube that = (RubiksCube) o;
    // return Arrays.equals(edges, that.edges);
    // }

    // @Override
    // public int hashCode() {
    // return Arrays.hashCode(edges);
    // }

}