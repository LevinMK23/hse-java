package hse.java.practice.task1;

import java.util.Arrays;

/**
 * Необходимо реализовать интерфейс Cube
 * При повороте передней грани, меняются верх низ право и лево
 */
public class RubiksCube {

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
    
    public Edge[] deepCloneEdges() {
        Edge[] edgesClone = new Edge[EDGES_COUNT];
        for (int i = 0; i < EDGES_COUNT; i++) {
            edgesClone[i] = new Edge(CubeColor.BLUE);
        }
        for (int i = 0; i < 6; i++) {
            CubeColor[][] orig = edges[i].getParts();
            CubeColor[][] edge = new CubeColor[3][3];
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    edge[j][k] = orig[j][k];
                }
            }
            edgesClone[i].setParts(edge);
        }
        return edgesClone;
    }
    // Представим грань curr перед собой. Тогда up/right/down/left это номера граней,
    // находящихся в соответствующей стороне от curr
    public void rotateClockwise(int curr, int up, int right, int down, int left) {
        Edge[] edgesCopy = deepCloneEdges(); // Пусть копия будет хранить копию оригинала, а обновлять будем
                                             // сразу оригинал, беря значения из копии

        CubeColor[][] edgeFrontOriginal = edges[curr].getParts(); // Front, потому что мы curr представляем перед нами
        CubeColor[][] edgeFrontCopy = edgesCopy[curr].getParts();

        CubeColor[][] edgeUpOriginal = edges[up].getParts();
        CubeColor[][] edgeUpCopy = edgesCopy[up].getParts();
        CubeColor[][] edgeRightOriginal = edges[right].getParts();
        CubeColor[][] edgeRightCopy = edgesCopy[right].getParts();
        CubeColor[][] edgeDownOriginal = edges[down].getParts();
        CubeColor[][] edgeDownCopy = edgesCopy[down].getParts();
        CubeColor[][] edgeLeftOriginal = edges[left].getParts();
        CubeColor[][] edgeLeftCopy = edgesCopy[left].getParts();

        for (int i = 0; i < 3; i++) {
            // Меняем переднюю грань
            edgeFrontOriginal[0][i] = edgeFrontCopy[2-i][0];
            edgeFrontOriginal[i][2] = edgeFrontCopy[0][i];
            edgeFrontOriginal[2][i] = edgeFrontCopy[2-i][2];
            edgeFrontOriginal[i][0] = edgeFrontCopy[2][i];
            // Меняем смежные грани
            edgeUpOriginal[2][i] = edgeLeftCopy[2-i][2];
            edgeRightOriginal[i][0] = edgeUpCopy[2][i];
            edgeDownOriginal[0][i] = edgeRightCopy[2-i][0];
            edgeLeftOriginal[i][2] = edgeDownCopy[0][i];
        }

    }

    public void front(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(4, 0, 3, 1, 2); // заполняем относительно того, какие грани по бокам
        } else {
            for (int i = 0; i < 3; i++) {
                rotateClockwise(4, 0, 3, 1, 2);
            }
        }
    }

    public void back(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(5, 0, 2, 1, 3);
        } else {
            for (int i = 0; i < 3; i++) {
                rotateClockwise(5, 0, 2, 1, 3);
            }
        }
    }

    public void up(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(0, 5, 3, 4, 2);
        } else {
            for (int i = 0; i < 3; i++) {
                rotateClockwise(0, 5, 3, 4, 2);
            }
        }
    }

    public void down(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(1, 4, 3, 5, 2);
        } else {
            for (int i = 0; i < 3; i++) {
                rotateClockwise(1, 4, 3, 5, 2);
            }
        }
    }

    public void right(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(3, 0, 5, 1, 4);
        } else {
            for (int i = 0; i < 3; i++) {
                rotateClockwise(3, 0, 5, 1, 4);
            }
        }
    }

    public void left(RotateDirection direction) {
        if (direction == RotateDirection.CLOCKWISE) {
            rotateClockwise(2, 0, 4, 1, 5);
        } else {
            for (int i = 0; i < 3; i++) {
                rotateClockwise(2, 0, 4, 1, 5);
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
}
