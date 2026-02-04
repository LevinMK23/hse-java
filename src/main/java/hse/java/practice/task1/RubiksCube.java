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

    public void front(RotateDirection direction) {
        Edge[] edgesCopy = deepCloneEdges(); // Пусть копия будет хранить копию оригинала, а обновлять будем
                                             // сразу оригинал, беря значения из копии
        // Для передней грани
        CubeColor[][] edgeFrontOriginal = edges[EdgePosition.FRONT.ordinal()].getParts();
        CubeColor[][] edgeFrontCopy = edgesCopy[EdgePosition.FRONT.ordinal()].getParts();

        // Для смежных граней
        CubeColor[][] edgeUpOriginal = edges[EdgePosition.UP.ordinal()].getParts();
        CubeColor[][] edgeRightOriginal = edges[EdgePosition.RIGHT.ordinal()].getParts();
        CubeColor[][] edgeDownOriginal = edges[EdgePosition.DOWN.ordinal()].getParts();
        CubeColor[][] edgeLeftOriginal = edges[EdgePosition.LEFT.ordinal()].getParts();
        CubeColor[][] edgeUpCopy = edgesCopy[EdgePosition.UP.ordinal()].getParts();
        CubeColor[][] edgeRightCopy = edgesCopy[EdgePosition.RIGHT.ordinal()].getParts();
        CubeColor[][] edgeDownCopy = edgesCopy[EdgePosition.DOWN.ordinal()].getParts();
        CubeColor[][] edgeLeftCopy = edgesCopy[EdgePosition.LEFT.ordinal()].getParts();

        if (direction == RotateDirection.CLOCKWISE) {
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
        } else {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    // Меняем переднюю грань
                    edgeFrontOriginal[0][i] = edgeFrontCopy[2 - i][0];
                    edgeFrontOriginal[i][2] = edgeFrontCopy[0][i];
                    edgeFrontOriginal[2][i] = edgeFrontCopy[2 - i][2];
                    edgeFrontOriginal[i][0] = edgeFrontCopy[2][i];
                    // Меняем смежные грани
                    edgeUpOriginal[2][i] = edgeLeftCopy[2 - i][2];
                    edgeRightOriginal[i][0] = edgeUpCopy[2][i];
                    edgeDownOriginal[0][i] = edgeRightCopy[2 - i][0];
                    edgeLeftOriginal[i][2] = edgeDownCopy[0][i];
                }
            }
        }
    }

    public void back(RotateDirection direction) {
        Edge[] edgesCopy = deepCloneEdges();

        CubeColor[][] edgeBackOriginal = edges[EdgePosition.BACK.ordinal()].getParts();
        CubeColor[][] edgeBackCopy = edgesCopy[EdgePosition.BACK.ordinal()].getParts();

        CubeColor[][] edgeUpOriginal = edges[EdgePosition.UP.ordinal()].getParts();
        CubeColor[][] edgeRightOriginal = edges[EdgePosition.RIGHT.ordinal()].getParts();
        CubeColor[][] edgeDownOriginal = edges[EdgePosition.DOWN.ordinal()].getParts();
        CubeColor[][] edgeLeftOriginal = edges[EdgePosition.LEFT.ordinal()].getParts();
        CubeColor[][] edgeUpCopy = edgesCopy[EdgePosition.UP.ordinal()].getParts();
        CubeColor[][] edgeRightCopy = edgesCopy[EdgePosition.RIGHT.ordinal()].getParts();
        CubeColor[][] edgeDownCopy = edgesCopy[EdgePosition.DOWN.ordinal()].getParts();
        CubeColor[][] edgeLeftCopy = edgesCopy[EdgePosition.LEFT.ordinal()].getParts();

        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                edgeBackOriginal[0][i] = edgeBackCopy[2-i][0];
                edgeBackOriginal[i][2] = edgeBackCopy[0][i];
                edgeBackOriginal[2][i] = edgeBackCopy[2-i][2];
                edgeBackOriginal[i][0] = edgeBackCopy[2][i];

                edgeUpOriginal[0][i] = edgeRightCopy[i][2];
                edgeRightOriginal[i][2] = edgeDownCopy[2][2-i];
                edgeDownOriginal[2][i] = edgeLeftCopy[i][0];
                edgeLeftOriginal[i][0] = edgeUpCopy[0][2-i];
            }
        } else {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    edgeBackOriginal[0][i] = edgeBackCopy[2-i][0];
                    edgeBackOriginal[i][2] = edgeBackCopy[0][i];
                    edgeBackOriginal[2][i] = edgeBackCopy[2-i][2];
                    edgeBackOriginal[i][0] = edgeBackCopy[2][i];

                    edgeUpOriginal[0][i] = edgeRightCopy[i][2];
                    edgeRightOriginal[i][2] = edgeDownCopy[0][2-i];
                    edgeDownOriginal[0][i] = edgeLeftCopy[i][0];
                    edgeLeftOriginal[i][0] = edgeUpCopy[0][2-i];
                }
            }
        }
    }

    public void up(RotateDirection direction) {
        Edge[] edgesCopy = deepCloneEdges();

        CubeColor[][] edgeUpOriginal = edges[EdgePosition.UP.ordinal()].getParts();
        CubeColor[][] edgeUpCopy = edgesCopy[EdgePosition.UP.ordinal()].getParts();

        CubeColor[][] edgeFrontOriginal = edges[EdgePosition.FRONT.ordinal()].getParts();
        CubeColor[][] edgeBackOriginal = edges[EdgePosition.BACK.ordinal()].getParts();
        CubeColor[][] edgeRightOriginal = edges[EdgePosition.RIGHT.ordinal()].getParts();
        CubeColor[][] edgeLeftOriginal = edges[EdgePosition.LEFT.ordinal()].getParts();
        CubeColor[][] edgeFrontCopy = edgesCopy[EdgePosition.FRONT.ordinal()].getParts();
        CubeColor[][] edgeBackCopy = edgesCopy[EdgePosition.BACK.ordinal()].getParts();
        CubeColor[][] edgeRightCopy = edgesCopy[EdgePosition.RIGHT.ordinal()].getParts();
        CubeColor[][] edgeLeftCopy = edgesCopy[EdgePosition.LEFT.ordinal()].getParts();

        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                edgeUpOriginal[0][i] = edgeUpCopy[2-i][0];
                edgeUpOriginal[i][2] = edgeUpCopy[0][i];
                edgeUpOriginal[2][i] = edgeUpCopy[2-i][2];
                edgeUpOriginal[i][0] = edgeUpCopy[2][i];

                edgeBackOriginal[0][i] = edgeLeftCopy[0][i];
                edgeLeftOriginal[0][i] = edgeFrontCopy[0][i];
                edgeFrontOriginal[0][i] = edgeRightCopy[0][i];
                edgeRightOriginal[0][i] = edgeBackCopy[0][i];
            }
        } else {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    edgeUpOriginal[0][i] = edgeUpCopy[2-i][0];
                    edgeUpOriginal[i][2] = edgeUpCopy[0][i];
                    edgeUpOriginal[2][i] = edgeUpCopy[2-i][2];
                    edgeUpOriginal[i][0] = edgeUpCopy[2][i];

                    edgeBackOriginal[0][i] = edgeLeftCopy[0][i];
                    edgeLeftOriginal[0][i] = edgeFrontCopy[0][i];
                    edgeFrontOriginal[0][i] = edgeRightCopy[0][i];
                    edgeRightOriginal[0][i] = edgeBackCopy[0][i];
                }
            }
        }
    }

    public void down(RotateDirection direction) {
        Edge[] edgesCopy = deepCloneEdges();

        CubeColor[][] edgeDownOriginal = edges[EdgePosition.DOWN.ordinal()].getParts();
        CubeColor[][] edgeDownCopy = edgesCopy[EdgePosition.DOWN.ordinal()].getParts();

        CubeColor[][] edgeFrontOriginal = edges[EdgePosition.FRONT.ordinal()].getParts();
        CubeColor[][] edgeBackOriginal = edges[EdgePosition.BACK.ordinal()].getParts();
        CubeColor[][] edgeRightOriginal = edges[EdgePosition.RIGHT.ordinal()].getParts();
        CubeColor[][] edgeLeftOriginal = edges[EdgePosition.LEFT.ordinal()].getParts();
        CubeColor[][] edgeFrontCopy = edgesCopy[EdgePosition.FRONT.ordinal()].getParts();
        CubeColor[][] edgeBackCopy = edgesCopy[EdgePosition.BACK.ordinal()].getParts();
        CubeColor[][] edgeRightCopy = edgesCopy[EdgePosition.RIGHT.ordinal()].getParts();
        CubeColor[][] edgeLeftCopy = edgesCopy[EdgePosition.LEFT.ordinal()].getParts();

        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                edgeDownOriginal[0][i] = edgeDownCopy[2-i][0];
                edgeDownOriginal[i][2] = edgeDownCopy[0][i];
                edgeDownOriginal[2][i] = edgeDownCopy[2-i][2];
                edgeDownOriginal[i][0] = edgeDownCopy[2][i];

                edgeBackOriginal[2][i] = edgeRightCopy[2][i];
                edgeLeftOriginal[2][i] = edgeBackCopy[2][i];
                edgeFrontOriginal[2][i] = edgeLeftCopy[2][i];
                edgeRightOriginal[2][i] = edgeFrontCopy[2][i];
            }
        } else {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    edgeDownOriginal[0][i] = edgeDownCopy[2-i][0];
                    edgeDownOriginal[i][2] = edgeDownCopy[0][i];
                    edgeDownOriginal[2][i] = edgeDownCopy[2-i][2];
                    edgeDownOriginal[i][0] = edgeDownCopy[2][i];

                    edgeBackOriginal[2][i] = edgeRightCopy[2][i];
                    edgeLeftOriginal[2][i] = edgeBackCopy[2][i];
                    edgeFrontOriginal[2][i] = edgeLeftCopy[2][i];
                    edgeRightOriginal[2][i] = edgeFrontCopy[2][i];
                }
            }
        }
    }

    public void right(RotateDirection direction) {
        Edge[] edgesCopy = deepCloneEdges();

        CubeColor[][] edgeRightOriginal = edges[EdgePosition.RIGHT.ordinal()].getParts();
        CubeColor[][] edgeRightCopy = edgesCopy[EdgePosition.RIGHT.ordinal()].getParts();

        CubeColor[][] edgeFrontOriginal = edges[EdgePosition.FRONT.ordinal()].getParts();
        CubeColor[][] edgeBackOriginal = edges[EdgePosition.BACK.ordinal()].getParts();
        CubeColor[][] edgeDownOriginal = edges[EdgePosition.DOWN.ordinal()].getParts();
        CubeColor[][] edgeUpOriginal = edges[EdgePosition.UP.ordinal()].getParts();
        CubeColor[][] edgeFrontCopy = edgesCopy[EdgePosition.FRONT.ordinal()].getParts();
        CubeColor[][] edgeBackCopy = edgesCopy[EdgePosition.BACK.ordinal()].getParts();
        CubeColor[][] edgeDownCopy = edgesCopy[EdgePosition.DOWN.ordinal()].getParts();
        CubeColor[][] edgeUpCopy = edgesCopy[EdgePosition.UP.ordinal()].getParts();

        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                edgeRightOriginal[0][i] = edgeRightCopy[2-i][0];
                edgeRightOriginal[i][2] = edgeRightCopy[0][i];
                edgeRightOriginal[2][i] = edgeRightCopy[2-i][2];
                edgeRightOriginal[i][0] = edgeRightCopy[2][i];

                edgeUpOriginal[i][2] = edgeFrontCopy[i][2];
                edgeFrontOriginal[i][2] = edgeDownCopy[i][2];
                edgeDownOriginal[i][2] = edgeBackCopy[2-i][0];
                edgeBackOriginal[i][0] = edgeUpCopy[2-i][2];
            }
        } else {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    edgeRightOriginal[0][i] = edgeRightCopy[2 - i][0];
                    edgeRightOriginal[i][2] = edgeRightCopy[0][i];
                    edgeRightOriginal[2][i] = edgeRightCopy[2 - i][2];
                    edgeRightOriginal[i][0] = edgeRightCopy[2][i];

                    edgeUpOriginal[i][2] = edgeFrontCopy[i][2];
                    edgeFrontOriginal[i][2] = edgeDownCopy[i][2];
                    edgeDownOriginal[i][2] = edgeBackCopy[2 - i][0];
                    edgeBackOriginal[i][0] = edgeUpCopy[2 - i][2];
                }
            }
        }
    }

    public void left(RotateDirection direction) {
        Edge[] edgesCopy = deepCloneEdges();

        CubeColor[][] edgeLeftOriginal = edges[EdgePosition.LEFT.ordinal()].getParts();
        CubeColor[][] edgeLeftCopy = edgesCopy[EdgePosition.LEFT.ordinal()].getParts();

        CubeColor[][] edgeFrontOriginal = edges[EdgePosition.FRONT.ordinal()].getParts();
        CubeColor[][] edgeBackOriginal = edges[EdgePosition.BACK.ordinal()].getParts();
        CubeColor[][] edgeDownOriginal = edges[EdgePosition.DOWN.ordinal()].getParts();
        CubeColor[][] edgeUpOriginal = edges[EdgePosition.UP.ordinal()].getParts();
        CubeColor[][] edgeFrontCopy = edgesCopy[EdgePosition.FRONT.ordinal()].getParts();
        CubeColor[][] edgeBackCopy = edgesCopy[EdgePosition.BACK.ordinal()].getParts();
        CubeColor[][] edgeDownCopy = edgesCopy[EdgePosition.DOWN.ordinal()].getParts();
        CubeColor[][] edgeUpCopy = edgesCopy[EdgePosition.UP.ordinal()].getParts();

        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                edgeLeftOriginal[0][i] = edgeLeftCopy[2-i][0];
                edgeLeftOriginal[i][2] = edgeLeftCopy[0][i];
                edgeLeftOriginal[2][i] = edgeLeftCopy[2-i][2];
                edgeLeftOriginal[i][0] = edgeLeftCopy[2][i];

                edgeUpOriginal[i][0] = edgeBackCopy[2-i][2];
                edgeFrontOriginal[i][0] = edgeUpCopy[i][0];
                edgeDownOriginal[i][0] = edgeFrontCopy[i][0];
                edgeBackOriginal[i][2] = edgeDownCopy[2-i][0];
            }
        } else {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    edgeLeftOriginal[0][i] = edgeLeftCopy[2-i][0];
                    edgeLeftOriginal[i][2] = edgeLeftCopy[0][i];
                    edgeLeftOriginal[2][i] = edgeLeftCopy[2-i][2];
                    edgeLeftOriginal[i][0] = edgeLeftCopy[2][i];

                    edgeUpOriginal[i][0] = edgeBackCopy[2-i][2];
                    edgeFrontOriginal[i][0] = edgeUpCopy[i][0];
                    edgeDownOriginal[i][0] = edgeFrontCopy[i][0];
                    edgeBackOriginal[i][2] = edgeDownCopy[2-i][0];
                }
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
