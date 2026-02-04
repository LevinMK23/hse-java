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

    private CubeColor[][] clone2d(CubeColor[][] original){
        CubeColor[][] copy = new CubeColor[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, 3);
        }
        return copy;
    }

    private void rotateSideClockWise(int side){
        CubeColor[][] edgeClone = clone2d(edges[side].getParts());
        edges[side].getParts()[0][0]=edgeClone[2][0];
        edges[side].getParts()[0][1]=edgeClone[1][0];
        edges[side].getParts()[0][2]=edgeClone[0][0];
        edges[side].getParts()[1][0]=edgeClone[2][1];
        edges[side].getParts()[1][2]=edgeClone[0][1];
        edges[side].getParts()[2][0]=edgeClone[2][2];
        edges[side].getParts()[2][1]=edgeClone[1][2];
        edges[side].getParts()[2][2]=edgeClone[0][2];
    }

    private void rotateSideCounterClockWise(int side){
        rotateSideClockWise(side);
        rotateSideClockWise(side);
        rotateSideClockWise(side);
    }

    public void front(RotateDirection direction) {
        CubeColor[][] edge2Clone = clone2d(edges[2].getParts());
        CubeColor[][] edge0Clone = clone2d(edges[0].getParts());
        CubeColor[][] edge3Clone = clone2d(edges[3].getParts());
        CubeColor[][] edge1Clone = clone2d(edges[1].getParts());
        if (direction==RotateDirection.CLOCKWISE){
            rotateSideClockWise(4);
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[2][i] = edge2Clone[2 - i][2];
                edges[2].getParts()[2 - i][2] = edge1Clone[0][2 - i];
                edges[1].getParts()[0][2 - i] = edge3Clone[i][0];
                edges[3].getParts()[i][0] = edge0Clone[2][i];
            }
        }
        else{
            rotateSideCounterClockWise(4);
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[2][i] = edge3Clone[i][0];
                edges[2].getParts()[2 - i][2] = edge0Clone[2][i];
                edges[1].getParts()[0][2 - i] = edge2Clone[2-i][2];
                edges[3].getParts()[i][0] = edge1Clone[0][2-i];
            }
        }
    }

    void up(RotateDirection direction){
        CubeColor[][] edge2Clone = clone2d(edges[2].getParts());
        CubeColor[][] edge4Clone = clone2d(edges[4].getParts());
        CubeColor[][] edge3Clone = clone2d(edges[3].getParts());
        CubeColor[][] edge5Clone = clone2d(edges[5].getParts());
        if (direction==RotateDirection.CLOCKWISE){
            rotateSideClockWise(0);
            for (int i = 0; i < 3; i++) {
                edges[5].getParts()[0][i] = edge2Clone[0][i];
                edges[2].getParts()[0][i] = edge4Clone[0][i];
                edges[3].getParts()[0][i] = edge5Clone[0][i];
                edges[4].getParts()[0][i] = edge3Clone[0][i];
            }
        }
        else{
            rotateSideCounterClockWise(0);
            for (int i = 0; i < 3; i++) {
                edges[5].getParts()[0][i] = edge3Clone[0][i];
                edges[2].getParts()[0][i] = edge5Clone[0][i];
                edges[3].getParts()[0][i] = edge4Clone[0][i];
                edges[4].getParts()[0][i] = edge2Clone[0][i];
            }
        }
    }

    void down(RotateDirection direction){
        CubeColor[][] edge2Clone = clone2d(edges[2].getParts());
        CubeColor[][] edge4Clone = clone2d(edges[4].getParts());
        CubeColor[][] edge3Clone = clone2d(edges[3].getParts());
        CubeColor[][] edge5Clone = clone2d(edges[5].getParts());
        if (direction==RotateDirection.CLOCKWISE){
            rotateSideClockWise(1);
            for (int i = 0; i < 3; i++) {
                edges[5].getParts()[2][i] = edge3Clone[2][i];
                edges[2].getParts()[2][i] = edge5Clone[2][i];
                edges[3].getParts()[2][i] = edge4Clone[2][i];
                edges[4].getParts()[2][i] = edge2Clone[2][i];
            }
        }
        else{
            rotateSideCounterClockWise(1);
            for (int i = 0; i < 3; i++) {
                edges[5].getParts()[2][i] = edge2Clone[2][i];
                edges[2].getParts()[2][i] = edge4Clone[2][i];
                edges[3].getParts()[2][i] = edge5Clone[2][i];
                edges[4].getParts()[2][i] = edge3Clone[2][i];
            }
        }
    }

    void left(RotateDirection direction){
        CubeColor[][] edge0Clone = clone2d(edges[0].getParts());
        CubeColor[][] edge4Clone = clone2d(edges[4].getParts());
        CubeColor[][] edge1Clone = clone2d(edges[1].getParts());
        CubeColor[][] edge5Clone = clone2d(edges[5].getParts());
        if (direction==RotateDirection.CLOCKWISE){
            rotateSideClockWise(2);
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[i][0] = edge5Clone[2-i][2];
                edges[4].getParts()[i][0] = edge0Clone[i][0];
                edges[1].getParts()[i][0] = edge4Clone[i][0];
                edges[5].getParts()[2-i][2] = edge1Clone[i][0];
            }
        }
        else{
            rotateSideCounterClockWise(2);
            for (int i = 0; i < 3; i++) {
                edges[5].getParts()[2-i][2] = edge0Clone[i][0];
                edges[0].getParts()[i][0] = edge4Clone[i][0];
                edges[4].getParts()[i][0] = edge1Clone[i][0];
                edges[1].getParts()[2-i][0] = edge5Clone[i][2];
            }
        }
    }

    void right(RotateDirection direction){
        CubeColor[][] edge0Clone = clone2d(edges[0].getParts());
        CubeColor[][] edge4Clone = clone2d(edges[4].getParts());
        CubeColor[][] edge1Clone = clone2d(edges[1].getParts());
        CubeColor[][] edge5Clone = clone2d(edges[5].getParts());
        if (direction==RotateDirection.CLOCKWISE){
            rotateSideClockWise(3);
            for (int i = 0; i < 3; i++) {
                edges[5].getParts()[2-i][0] = edge0Clone[i][2];
                edges[0].getParts()[i][2] = edge4Clone[i][2];
                edges[4].getParts()[i][2] = edge1Clone[i][2];
                edges[1].getParts()[2-i][2] = edge5Clone[i][0];
            }
        }
        else{
            rotateSideCounterClockWise(3);
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[i][2] = edge5Clone[2-i][0];
                edges[4].getParts()[i][2] = edge0Clone[i][2];
                edges[1].getParts()[i][2] = edge4Clone[i][2];
                edges[5].getParts()[2-i][0] = edge1Clone[i][2];
            }
        }
    }

    void back(RotateDirection direction){
        CubeColor[][] edge2Clone = clone2d(edges[2].getParts());
        CubeColor[][] edge0Clone = clone2d(edges[0].getParts());
        CubeColor[][] edge3Clone = clone2d(edges[3].getParts());
        CubeColor[][] edge1Clone = clone2d(edges[1].getParts());
        if (direction==RotateDirection.CLOCKWISE){
            rotateSideClockWise(5);
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[0][i] = edge3Clone[i][2];
                edges[2].getParts()[i][0] = edge0Clone[0][2-i];
                edges[1].getParts()[2][i] = edge2Clone[i][0];
                edges[3].getParts()[2-i][2] = edge1Clone[2][i];
            }
        }
        else{
            rotateSideCounterClockWise(5);
            for (int i = 0; i < 3; i++) {
                edges[0].getParts()[0][i] = edge2Clone[2-i][0];
                edges[2].getParts()[i][0] = edge1Clone[2][i];
                edges[1].getParts()[2][i] = edge3Clone[2-i][2];
                edges[3].getParts()[i][2] = edge0Clone[0][i];
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
