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

    @Override
    public void front(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            front(RotateDirection.CLOCKWISE);
            front(RotateDirection.CLOCKWISE);
            front(RotateDirection.CLOCKWISE);
            return;
        }
        
        rotateEdge(edges[4]);
        
        CubeColor[] temp = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = edges[0].getParts()[2][i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[0].getParts()[2][i] = edges[2].getParts()[i][2];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[2].getParts()[i][2] = edges[1].getParts()[0][2 - i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[1].getParts()[0][i] = edges[3].getParts()[i][0];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[3].getParts()[i][0] = temp[2 - i];
        }
    }
    
    @Override
    public void up(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            up(RotateDirection.CLOCKWISE);
            up(RotateDirection.CLOCKWISE);
            up(RotateDirection.CLOCKWISE);
            return;
        }
        
        rotateEdge(edges[0]);
        
        CubeColor[] temp = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = edges[4].getParts()[0][i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[4].getParts()[0][i] = edges[3].getParts()[0][i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[3].getParts()[0][i] = edges[5].getParts()[0][i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[5].getParts()[0][i] = edges[2].getParts()[0][i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[2].getParts()[0][i] = temp[i];
        }
    }
    
    @Override
    public void down(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            down(RotateDirection.CLOCKWISE);
            down(RotateDirection.CLOCKWISE);
            down(RotateDirection.CLOCKWISE);
            return;
        }
        
        rotateEdge(edges[1]);
        
        CubeColor[] temp = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = edges[4].getParts()[2][i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[4].getParts()[2][i] = edges[2].getParts()[2][i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[2].getParts()[2][i] = edges[5].getParts()[2][i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[5].getParts()[2][i] = edges[3].getParts()[2][i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[3].getParts()[2][i] = temp[i];
        }
    }
    
    @Override
    public void left(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            left(RotateDirection.CLOCKWISE);
            left(RotateDirection.CLOCKWISE);
            left(RotateDirection.CLOCKWISE);
            return;
        }
        
        rotateEdge(edges[2]);
        
        CubeColor[] temp = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = edges[0].getParts()[i][0];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[0].getParts()[i][0] = edges[5].getParts()[2 - i][2];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[5].getParts()[i][2] = edges[1].getParts()[2 - i][0];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[1].getParts()[i][0] = edges[4].getParts()[i][0];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[4].getParts()[i][0] = temp[i];
        }
    }
    
    @Override
    public void right(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            right(RotateDirection.CLOCKWISE);
            right(RotateDirection.CLOCKWISE);
            right(RotateDirection.CLOCKWISE);
            return;
        }
        
        rotateEdge(edges[3]);
        
        CubeColor[] temp = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = edges[0].getParts()[i][2];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[0].getParts()[i][2] = edges[4].getParts()[i][2];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[4].getParts()[i][2] = edges[1].getParts()[i][2];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[1].getParts()[i][2] = edges[5].getParts()[2 - i][0];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[5].getParts()[i][0] = temp[2 - i];
        }
    }
    
    @Override
    public void back(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            back(RotateDirection.CLOCKWISE);
            back(RotateDirection.CLOCKWISE);
            back(RotateDirection.CLOCKWISE);
            return;
        }
        
        rotateEdge(edges[5]);
        
        CubeColor[] temp = new CubeColor[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = edges[0].getParts()[0][i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[0].getParts()[0][i] = edges[3].getParts()[i][2];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[3].getParts()[i][2] = edges[1].getParts()[2][2 - i];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[1].getParts()[2][i] = edges[2].getParts()[i][0];
        }
        
        for (int i = 0; i < 3; i++) {
            edges[2].getParts()[i][0] = temp[2 - i];
        }
    }
    
    private void rotateEdge(Edge edge) {
        CubeColor[][] parts = edge.getParts();
        CubeColor[][] rotated = new CubeColor[3][3];
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotated[j][2 - i] = parts[i][j];
            }
        }
        
        edge.setParts(rotated);
    }

    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}
