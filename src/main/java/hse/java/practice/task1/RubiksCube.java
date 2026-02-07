package hse.java.practice.task1;

import java.util.Arrays;

public class RubiksCube implements Cube {

    private static final int EDGES_COUNT = 6;

    private final Edge[] edges = new Edge[EDGES_COUNT];

    public RubiksCube() {
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            edges[i] = new Edge(colors[i]);
        }
    }

    public void rotateEdgeClockwise(int edge_num) {
        CubeColor[][] parts_ = edges[edge_num].getParts();
        CubeColor temp1 = parts_[0][0];
        parts_[0][0] = parts_[2][0];
        parts_[2][0] = parts_[2][2];
        parts_[2][2] = parts_[0][2];
        parts_[0][2] = temp1;
        CubeColor temp2 = parts_[0][1];
        parts_[0][1] = parts_[1][0];
        parts_[1][0] = parts_[2][1];
        parts_[2][1] = parts_[1][2];
        parts_[1][2] = temp2;
        edges[edge_num].setParts(parts_);
    }

    // Считаем, что 0 - верх, 1 - низ, 2 - лево, 3 - право, 4 - передняя, 5 - задняя

    @Override
    public void up(RotateDirection direction) {
        int k = 1;
        if(direction == RotateDirection.COUNTERCLOCKWISE) {
            k = 3;
        }
        for(int i = 0; i<k; i++) {
            this.rotateEdgeClockwise(0);
            CubeColor[] temp = new CubeColor[3];
            CubeColor[][] p2 = edges[2].getParts();
            CubeColor[][] p3= edges[3].getParts();
            CubeColor[][] p4 = edges[4].getParts();
            CubeColor[][] p5 = edges[5].getParts();
            for (int j = 0; j < 3; j++) {
                temp[j] = p2[0][j];
            }
            for (int j = 0; j < 3; j++) {
                p2[0][j] = p4[0][j];
            }
            for (int j = 0; j < 3; j++) {
                p4[0][j] = p3[0][j];
            }
            for (int j = 0; j < 3; j++) {
                p3[0][j] = p5[0][j];
            }
            for (int j = 0; j < 3; j++) {
                p5[0][j] = temp[j];
            }
            edges[2].setParts(p2);
            edges[3].setParts(p3);
            edges[4].setParts(p4);
            edges[5].setParts(p5);
        }
    }

    @Override
    public void down(RotateDirection direction) {
        int k = 1;
        if(direction == RotateDirection.COUNTERCLOCKWISE) {
            k = 3;
        }
        for(int i = 0; i<k; i++) {
            this.rotateEdgeClockwise(1);
            CubeColor[] temp = new CubeColor[3];
            CubeColor[][] p2 = edges[2].getParts();
            CubeColor[][] p3 = edges[3].getParts();
            CubeColor[][] p4 = edges[4].getParts();
            CubeColor[][] p5 = edges[5].getParts();

            for (int j = 0; j < 3; j++) {
                temp[j] = p2[2][j];
            }
            for (int j = 0; j < 3; j++) {
                p2[2][j] = p4[2][j];
            }
            for (int j = 0; j < 3; j++) {
                p4[2][j] = p3[2][j];
            }
            for (int j = 0; j < 3; j++) {
                p3[2][j] = p5[2][j];
            }
            for (int j = 0; j < 3; j++) {
                p5[2][j] = temp[j];
            }

            edges[2].setParts(p2);
            edges[3].setParts(p3);
            edges[4].setParts(p4);
            edges[5].setParts(p5);
        }
    }

    @Override
    public void left(RotateDirection direction) {
        int k = 1;
        if(direction == RotateDirection.COUNTERCLOCKWISE) {
            k = 3;
        }
        for(int i = 0; i<k; i++) {
            this.rotateEdgeClockwise(2);
            CubeColor[] temp = new CubeColor[3];
            CubeColor[][] p0 = edges[0].getParts();
            CubeColor[][] p1 = edges[1].getParts();
            CubeColor[][] p4 = edges[4].getParts();
            CubeColor[][] p5 = edges[5].getParts();

            for (int j = 0; j < 3; j++) {
                temp[j] = p0[j][0];
            }
            for (int j = 0; j < 3; j++) {
                p0[j][0] = p4[j][0];
            }
            for (int j = 0; j < 3; j++) {
                p4[j][0] = p1[j][0];
            }
            for (int j = 0; j < 3; j++) {
                p1[j][0] = p5[2-j][2];
            }
            for (int j = 0; j < 3; j++) {
                p5[2-j][2] = temp[j];
            }

            edges[0].setParts(p0);
            edges[1].setParts(p1);
            edges[4].setParts(p4);
            edges[5].setParts(p5);
        }
    }

    @Override
    public void right(RotateDirection direction) {
        int k = 1;
        if(direction == RotateDirection.COUNTERCLOCKWISE) {
            k = 3;
        }
        for(int i = 0; i<k; i++) {
            this.rotateEdgeClockwise(3);
            CubeColor[] temp = new CubeColor[3];
            CubeColor[][] p0 = edges[0].getParts();
            CubeColor[][] p1 = edges[1].getParts();
            CubeColor[][] p4 = edges[4].getParts();
            CubeColor[][] p5 = edges[5].getParts();

            for (int j = 0; j < 3; j++) {
                temp[j] = p0[j][2];
            }
            for (int j = 0; j < 3; j++) {
                p0[j][2] = p5[2-j][0];
            }
            for (int j = 0; j < 3; j++) {
                p5[2-j][0] = p1[j][2];
            }
            for (int j = 0; j < 3; j++) {
                p1[j][2] = p4[j][2];
            }
            for (int j = 0; j < 3; j++) {
                p4[j][2] = temp[j];
            }

            edges[0].setParts(p0);
            edges[1].setParts(p1);
            edges[4].setParts(p4);
            edges[5].setParts(p5);
        }
    }

    @Override
    public void front(RotateDirection direction) {
        int k = 1;
        if(direction == RotateDirection.COUNTERCLOCKWISE) {
            k = 3;
        }
        for(int i = 0; i<k; i++) {
            this.rotateEdgeClockwise(4);
            CubeColor[] temp = new CubeColor[3];
            CubeColor[][] p0 = edges[0].getParts();
            CubeColor[][] p1 = edges[1].getParts();
            CubeColor[][] p2 = edges[2].getParts();
            CubeColor[][] p3 = edges[3].getParts();

            for (int j = 0; j < 3; j++) {
                temp[j] = p0[2][j];
            }
            for (int j = 0; j < 3; j++) {
                p0[2][j] = p2[2-j][2];
            }
            for (int j = 0; j < 3; j++) {
                p2[2-j][2] = p1[0][2-j];
            }
            for (int j = 0; j < 3; j++) {
                p1[0][2-j] = p3[j][0];
            }
            for (int j = 0; j < 3; j++) {
                p3[j][0] = temp[j];
            }

            edges[0].setParts(p0);
            edges[1].setParts(p1);
            edges[2].setParts(p2);
            edges[3].setParts(p3);
        }
    }

    @Override
    public void back(RotateDirection direction) {
        int k = 1;
        if(direction == RotateDirection.COUNTERCLOCKWISE) {
            k = 3;
        }
        for(int i = 0; i<k; i++) {
            this.rotateEdgeClockwise(5);
            CubeColor[] temp = new CubeColor[3];
            CubeColor[][] p0 = edges[0].getParts();
            CubeColor[][] p1 = edges[1].getParts();
            CubeColor[][] p2 = edges[2].getParts();
            CubeColor[][] p3 = edges[3].getParts();

            for (int j = 0; j < 3; j++) {
                temp[j] = p0[0][j];
            }
            for (int j = 0; j < 3; j++) {
                p0[0][j] = p3[j][2];
            }
            for (int j = 0; j < 3; j++) {
                p3[j][2] = p1[2][2-j];
            }
            for (int j = 0; j < 3; j++) {
                p1[2][2-j] = p2[2-j][0];
            }
            for (int j = 0; j < 3; j++) {
                p2[2-j][0] = temp[2-j];
            }

            edges[0].setParts(p0);
            edges[1].setParts(p1);
            edges[2].setParts(p2);
            edges[3].setParts(p3);
        }
    }

    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}
