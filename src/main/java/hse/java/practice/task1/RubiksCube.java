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
        CubeColor[][] edgeFrontOriginal = edges[4].getParts();
        CubeColor[][] edgeFrontCopy = edgesCopy[4].getParts();

        // Для смежных граней
        CubeColor[][] edgeUpOriginal = edges[0].getParts();
        CubeColor[][] edgeRightOriginal = edges[3].getParts();
        CubeColor[][] edgeDownOriginal = edges[1].getParts();
        CubeColor[][] edgeLeftOriginal = edges[1].getParts();
        CubeColor[][] edgeUpCopy = edgesCopy[0].getParts();
        CubeColor[][] edgeRightCopy = edgesCopy[3].getParts();
        CubeColor[][] edgeDownCopy = edgesCopy[1].getParts();
        CubeColor[][] edgeLeftCopy = edgesCopy[1].getParts();

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
        Edge[] edgesCopy = deepCloneEdges(); // Пусть копия будет хранить копию оригинала, а обновлять будем
        // сразу оригинал, беря значения из копии
        // Для передней грани
        CubeColor[][] edgeBackOriginal = edges[5].getParts();
        CubeColor[][] edgeBackCopy = edgesCopy[5].getParts();

        // Для смежных граней
        CubeColor[][] edgeUpOriginal = edges[0].getParts();
        CubeColor[][] edgeRightOriginal = edges[3].getParts();
        CubeColor[][] edgeDownOriginal = edges[1].getParts();
        CubeColor[][] edgeLeftOriginal = edges[1].getParts();
        CubeColor[][] edgeUpCopy = edgesCopy[0].getParts();
        CubeColor[][] edgeRightCopy = edgesCopy[3].getParts();
        CubeColor[][] edgeDownCopy = edgesCopy[1].getParts();
        CubeColor[][] edgeLeftCopy = edgesCopy[1].getParts();

        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < 3; i++) {
                // Меняем переднюю грань
                edgeBackOriginal[0][i] = edgeBackCopy[i][2];
                edgeBackOriginal[i][2] = edgeBackCopy[2][2-i];
                edgeBackOriginal[2][i] = edgeBackCopy[i][0];
                edgeBackOriginal[i][0] = edgeBackCopy[0][2-i];
                // Меняем смежные грани
                edgeUpOriginal[2][i] = edgeRightCopy[i][0];
                edgeRightOriginal[i][0] = edgeDownCopy[0][2-i];
                edgeDownOriginal[0][i] = edgeRightCopy[2-i][0];
                edgeLeftOriginal[i][2] = edgeUpCopy[2][2-i];
            }
        } else {
            for (int i = 0; i < 3; i++) {
                // Меняем переднюю грань
                edgeBackOriginal[0][i] = edgeBackCopy[2-i][0];
                edgeBackOriginal[i][2] = edgeBackCopy[0][i];
                edgeBackOriginal[2][i] = edgeBackCopy[2-i][2];
                edgeBackOriginal[i][0] = edgeBackCopy[2][i];
                // Меняем смежные грани
                edgeUpOriginal[2][i] = edgeLeftCopy[2-i][2];
                edgeRightOriginal[i][0] = edgeUpCopy[2][i];
                edgeDownOriginal[0][i] = edgeRightCopy[2-i][0];
                edgeLeftOriginal[i][2] = edgeDownCopy[0][i];
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
