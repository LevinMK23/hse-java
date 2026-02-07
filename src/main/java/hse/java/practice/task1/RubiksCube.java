package hse.java.practice.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        /**
     * Ордер в енуме соответствует позиции грани куба
     * в массиве граней
     * edges[0] -> UP -> GREEN
     * edges[1] -> DOWN -> RED
     * edges[2] -> LEFT -> BLUE
     * edges[3] -> RIGHT -> WHITE
     * edges[4] -> FRONT -> YELLOW
     * edges[5] -> BACK -> ORANGE
     * ...
     */
    public RubiksCube() {
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            edges[i] = new Edge(colors[i]);
        }
    }

    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }

    private CubeColor[] getColumn(Edge edge, int colIdx) {
        CubeColor[] column = new CubeColor[3];
        CubeColor[][] parts = edge.getParts();
        for (int i = 0; i < 3; i++) {
            column[i] = parts[i][colIdx];
        }
        return column;
    }

    private void setColumn(Edge edge, int colIdx, CubeColor[] values) {
        CubeColor[][] parts = edge.getParts();
        for (int i = 0; i < 3; i++) {
            parts[i][colIdx] = values[i];
        }
    }

    private CubeColor[] reverseArray(CubeColor[] arr) {
        CubeColor[] copy = new CubeColor[3];
        copy[0] = arr[2];
        copy[1] = arr[1];
        copy[2] = arr[0];
        return copy;
    }

    // clockwise left: 4 <- 0 <- 5 <- 1
    // counter left: 4 <- 1 <- 5 <- 0
    // clockwise right = counter left
    // counter right = clockwise right
    private void rotateSideEdges(RotateDirection direction, int primary_col) {
        int side_col = primary_col ^ 2;

        ArrayList<Integer> cycle = new ArrayList<>(Arrays.asList(4, 0, 5, 1));

        boolean cond = (direction == RotateDirection.CLOCKWISE && primary_col == 2)
                        || (direction == RotateDirection.COUNTERCLOCKWISE && primary_col == 0);

        if (cond) {
            Collections.swap(cycle, 1, 3);
        }

        CubeColor[] temp = getColumn(edges[cycle.get(0)], primary_col);

        for (int i = 0; i < 3; i++) {
            int currentFace = cycle.get(i);
            int nextFace = cycle.get(i + 1);

            int nextColIdx = (nextFace == 5) ? side_col : primary_col;
            CubeColor[] nextColData = getColumn(edges[nextFace], nextColIdx);

            if (currentFace == 5 || nextFace == 5) {
                nextColData = reverseArray(nextColData);
            }

            int currentColIdx = (currentFace == 5) ? side_col : primary_col;
            setColumn(edges[currentFace], currentColIdx, nextColData);
        }

        setColumn(edges[cycle.get(3)], primary_col, temp);
    }

    private void rotateSidesFrontBack(RotateDirection direction, int column) {
        int curr_row = (direction == RotateDirection.CLOCKWISE) ? 2 : 0;
        int curr_col = column;
        boolean is_column = true;

        ArrayList<Integer> cycle = new ArrayList<>(Arrays.asList(3, 0, 2, 1));
        CubeColor[] temp = getColumn(edges[cycle.get(0)], column);

        boolean cond = (direction == RotateDirection.CLOCKWISE && column == 2)
                        || (direction == RotateDirection.COUNTERCLOCKWISE && column == 0);

        boolean need_rev = cond;

        if (cond) {
            Collections.swap(cycle, 1, 3);
        }
        
        for (int i = 0; i < 3; i++) {
            int currentFace = cycle.get(i);
            int nextFace = cycle.get(i + 1);
            
            if (is_column) {
                CubeColor[] sourceRow = edges[nextFace].getParts()[curr_row];
                setColumn(edges[currentFace], curr_col, sourceRow);
                curr_col ^= 2;
            } else {
                CubeColor[] sourceCol = getColumn(edges[nextFace], curr_col);
                if (need_rev) {
                    sourceCol = reverseArray(sourceCol);
                }
                System.arraycopy(sourceCol, 0, edges[currentFace].getParts()[curr_row], 0, 3);
                curr_row ^= 2;
            }

            is_column ^= true;
            need_rev ^= true;
        }

        if (need_rev) {
            temp = reverseArray(temp);
        }
        System.arraycopy(temp, 0, edges[cycle.get(3)].getParts()[curr_row], 0, 3);
    }

    private void rotateEdge(Edge edge, RotateDirection direction) {
        CubeColor[][] temp = new CubeColor[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (direction == RotateDirection.CLOCKWISE) {
                    temp[i][j] = edge.getParts()[j][2 - i];
                } else {
                    temp[i][j] = edge.getParts()[2 - j][i];
                }
            }
        }

        edge.setParts(temp);
    }

    private void rotateSidesUpDown(RotateDirection direction, int row) {
        CubeColor[] temp = new CubeColor[3];
        System.arraycopy(edges[4].getParts()[row], 0, temp, 0, 3);
        ArrayList<Integer> cycle = new ArrayList<>(Arrays.asList(4, 2, 5, 3));
        
        boolean cond = (direction == RotateDirection.CLOCKWISE && row == 0)
            || (direction == RotateDirection.COUNTERCLOCKWISE && row == 2);
    
        if (cond) {
            Collections.swap(cycle, 1, 3);
        }

        for (int i = 1; i < 4; i++) {
            CubeColor[] sourceRow = edges[cycle.get(i)].getParts()[row];
            CubeColor[] destRow = edges[cycle.get(i - 1)].getParts()[row];
            System.arraycopy(sourceRow, 0, destRow, 0, 3);
        }

        CubeColor[] lastDestRow = edges[cycle.get(3)].getParts()[row];
        System.arraycopy(temp, 0, lastDestRow, 0, 3);
    }

    @Override
    public void up(RotateDirection direction) { 
        rotateEdge(edges[0], direction);

        rotateSidesUpDown(direction, 0);
    }

    @Override
    public void down(RotateDirection direction) {
        rotateEdge(edges[1], direction);

        rotateSidesUpDown(direction, 2);
    }

    @Override
    public void left(RotateDirection direction) {
        rotateEdge(edges[2], direction);

        rotateSideEdges(direction, 0);
    }

    @Override
    public void right(RotateDirection direction) {
        rotateEdge(edges[3], direction);

        rotateSideEdges(direction, 2);
    }

    @Override
    public void front(RotateDirection direction) {
        rotateEdge(edges[4], direction);

        rotateSidesFrontBack(direction, 2);
    }

    @Override
    public void back(RotateDirection direction) {
        rotateEdge(edges[5], direction);

        rotateSidesFrontBack(direction, 0);
    }
}
