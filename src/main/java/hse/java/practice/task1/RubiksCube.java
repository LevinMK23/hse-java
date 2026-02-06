package hse.java.practice.task1;

import java.util.Arrays;

/**
 * Необходимо реализовать интерфейс Cube
 * При повороте передней грани, меняются верх низ право и лево
 */
public class RubiksCube implements Cube {

    private static final int EDGES_COUNT = 6;

    private final Edge[] edges = new Edge[EDGES_COUNT];

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

    private void rotateEdgeClockwise(int edgeIndex) {
        CubeColor[][] parts = this.edges[edgeIndex].getParts();

        CubeColor tmp = parts[2][2];
        parts[2][2] = parts[0][2];
        parts[0][2] = parts[0][0];
        parts[0][0] = parts[2][0];
        parts[2][0] = tmp;

        tmp = parts[0][1];
        parts[0][1] = parts[1][0];
        parts[1][0] = parts[2][1];
        parts[2][1] = parts[1][2];
        parts[1][2] = tmp;
    }

    public CubeColor getСolor(EdgePosition position, int NumRow, int NumCol) {
        return edges[position.ordinal()].getParts()[NumRow][NumCol];
    }

    public void setColor(EdgePosition position, int NumRow, int NumCol, CubeColor newColor) {
        edges[position.ordinal()].getParts()[NumRow][NumCol] = newColor;
    }

    public void front(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateEdgeClockwise(4);

            CubeColor[][] upParts = this.edges[0].getParts();
            CubeColor[][] downParts = this.edges[1].getParts();
            CubeColor[][] leftParts = this.edges[2].getParts();
            CubeColor[][] rightParts = this.edges[3].getParts();

            CubeColor[] temp = new CubeColor[3];
            temp[0] = getСolor(EdgePosition.LEFT, 0,2);
            temp[1] = getСolor(EdgePosition.LEFT, 0,1);
            temp[2] = getСolor(EdgePosition.LEFT, 0,0);

            leftParts[0][2] = downParts[0][2];
            leftParts[0][1] = downParts[0][1];
            leftParts[0][0] = downParts[0][0];

            downParts[0][0] = rightParts[0][0];
            downParts[0][1] = rightParts[0][1];
            downParts[0][2] = rightParts[0][2];

            rightParts[0][0] = upParts[2][0];
            rightParts[0][1] = upParts[2][1];
            rightParts[0][2] = upParts[2][2];

            upParts[2][0] = temp[0];
            upParts[2][1] = temp[1];
            upParts[2][2] = temp[2];
        }
        else {
            front(RotateDirection.CLOCKWISE);
            front(RotateDirection.CLOCKWISE);
            front(RotateDirection.CLOCKWISE);
        }

    }

    public void up(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateEdgeClockwise(0);

            CubeColor[][] leftParts = this.edges[2].getParts();
            CubeColor[][] rightParts = this.edges[3].getParts();
            CubeColor[][] frontParts = this.edges[4].getParts();
            CubeColor[][] backParts = this.edges[5].getParts();

            CubeColor[] temp = new CubeColor[3];
            temp[0] = getСolor(EdgePosition.LEFT, 0,2);
            temp[1] = getСolor(EdgePosition.LEFT, 0,1);
            temp[2] = getСolor(EdgePosition.LEFT, 0,0);

            leftParts[0][2] = frontParts[0][2];
            leftParts[0][1] = frontParts[0][1];
            leftParts[0][0] = frontParts[0][0];

            frontParts[0][2] = rightParts[0][2];
            frontParts[0][1] = rightParts[0][1];
            frontParts[0][0] = rightParts[0][0];

            rightParts[0][2] = backParts[2][0];
            rightParts[0][1] = backParts[2][1];
            rightParts[0][0] = backParts[2][2];

            backParts[2][0] = temp[0];
            backParts[2][1] = temp[1];
            backParts[2][2] = temp[2];
        }
        else {
            up(RotateDirection.CLOCKWISE);
            up(RotateDirection.CLOCKWISE);
            up(RotateDirection.CLOCKWISE);
        }
    }

    public void down(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateEdgeClockwise(1);

            CubeColor[][] leftParts = this.edges[2].getParts();
            CubeColor[][] rightParts = this.edges[3].getParts();
            CubeColor[][] frontParts = this.edges[4].getParts();
            CubeColor[][] backParts = this.edges[5].getParts();

            CubeColor[] temp = new CubeColor[3];
            temp[0] = getСolor(EdgePosition.LEFT, 0,2);
            temp[1] = getСolor(EdgePosition.LEFT, 1,2);
            temp[2] = getСolor(EdgePosition.LEFT, 2,2);

            leftParts[2][2] = backParts[0][2];
            leftParts[1][2] = backParts[0][1];
            leftParts[0][2] = backParts[0][0];

            backParts[0][2] = rightParts[0][0];
            backParts[0][1] = rightParts[1][0];
            backParts[0][0] = rightParts[1][0];

            rightParts[0][0] = frontParts[2][0];
            rightParts[1][0] = frontParts[2][1];
            rightParts[2][0] = frontParts[2][2];

            frontParts[2][0] = temp[0];
            frontParts[2][1] = temp[1];
            frontParts[2][2] = temp[2];
        }
        else {
            down(RotateDirection.CLOCKWISE);
            down(RotateDirection.CLOCKWISE);
            down(RotateDirection.CLOCKWISE);
        }
    }

    public void left(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateEdgeClockwise(2);

            CubeColor[][] upParts = this.edges[0].getParts();
            CubeColor[][] downParts = this.edges[1].getParts();
            CubeColor[][] frontParts = this.edges[4].getParts();
            CubeColor[][] backParts = this.edges[5].getParts();

            CubeColor[] temp = new CubeColor[3];
            temp[0] = getСolor(EdgePosition.UP, 0,0);
            temp[1] = getСolor(EdgePosition.UP, 1,0);
            temp[2] = getСolor(EdgePosition.UP, 2,0);

            upParts[0][0] = backParts[0][0];
            upParts[1][0] = backParts[1][0];
            upParts[2][0] = backParts[2][0];

            backParts[0][0] = downParts[0][0];
            backParts[1][0] = downParts[1][0];
            backParts[2][0] = downParts[2][0];

            downParts[0][0] = frontParts[0][0];
            downParts[1][0] = frontParts[1][0];
            downParts[2][0] = frontParts[2][0];

            frontParts[0][0] = temp[0];
            frontParts[1][0] = temp[1];
            frontParts[2][0] = temp[2];
        }
        else {
            left(RotateDirection.CLOCKWISE);
            left(RotateDirection.CLOCKWISE);
            left(RotateDirection.CLOCKWISE);
        }
    }

    public void right(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateEdgeClockwise(3);

            CubeColor[][] upParts = this.edges[0].getParts();
            CubeColor[][] downParts = this.edges[1].getParts();
            CubeColor[][] frontParts = this.edges[4].getParts();
            CubeColor[][] backParts = this.edges[5].getParts();

            CubeColor[] temp = new CubeColor[3];
            temp[0] = getСolor(EdgePosition.DOWN, 2,2);
            temp[1] = getСolor(EdgePosition.DOWN, 1,2);
            temp[2] = getСolor(EdgePosition.DOWN, 0,2);

            downParts[2][2] = backParts[2][2];
            downParts[1][2] = backParts[1][2];
            downParts[0][2] = backParts[0][2];

            backParts[2][2] = upParts[2][1];
            backParts[1][2] = upParts[1][2];
            backParts[0][2] = upParts[0][2];

            upParts[2][1] = frontParts[2][2];
            upParts[1][2] = frontParts[1][2];
            upParts[0][1] = frontParts[0][2];

            frontParts[2][2] = temp[0];
            frontParts[1][2] = temp[1];
            frontParts[0][2] = temp[2];
        }
        else {
            right(RotateDirection.CLOCKWISE);
            right(RotateDirection.CLOCKWISE);
            right(RotateDirection.CLOCKWISE);
        }
    }

    public void back(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateEdgeClockwise(5);

            CubeColor[][] upParts = this.edges[0].getParts();
            CubeColor[][] downParts = this.edges[1].getParts();
            CubeColor[][] leftParts = this.edges[2].getParts();
            CubeColor[][] rightParts = this.edges[3].getParts();

            CubeColor[] temp = new CubeColor[3];
            temp[0] = getСolor(EdgePosition.LEFT, 2,0);
            temp[1] = getСolor(EdgePosition.LEFT, 2,1);
            temp[2] = getСolor(EdgePosition.LEFT, 2,0);

            leftParts[2][0] = upParts[0][2];
            leftParts[2][1] = upParts[0][1];
            leftParts[2][2] = upParts[0][0];

            upParts[0][2] = rightParts[2][0];
            upParts[0][2] = rightParts[2][1];
            upParts[0][0] = rightParts[2][2];

            rightParts[2][0] = downParts[2][0];
            rightParts[2][1] = downParts[2][1];
            rightParts[2][2] = downParts[2][2];

            downParts[2][0] = temp[0];
            downParts[2][1] = temp[1];
            downParts[2][2] = temp[2];
        }
        else {
            back(RotateDirection.CLOCKWISE);
            back(RotateDirection.CLOCKWISE);
            back(RotateDirection.CLOCKWISE);
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
