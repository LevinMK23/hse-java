package hse.java.practice.task1;

import java.util.Arrays;

public class Edge {

    private CubeColor[][] parts;

    public Edge(CubeColor[][] parts) {
        this.parts = parts;
    } // конструктор для создания сразу готового кубика рубика

    public Edge(CubeColor color) {
        this.parts = new CubeColor[3][3]; // создаем новую грань с цветом 3x3
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                parts[i][j] = color;
            }
        }
    }

    static public CubeColor getEdgeColor(Edge edge ) {
        return edge.parts[0][0];
    }

    public Edge() {
        parts = new CubeColor[3][3];
    }

    public CubeColor[][] getParts() {
        return parts;
    }

    public CubeColor[] getPartsLine(int i ) {
        return parts[i];
    }
    public  CubeColor[] getPartsRow(int i) {
        return new CubeColor[] {
                parts[0][i] , parts[1][i] , parts[2][i] ,
        };
    }



    public void setParts(CubeColor[][] parts) {
        this.parts = parts;
    }

    public CubeColor[] setPartsLine1(CubeColor[] partsLine) {
        CubeColor[] returnedCubeColor = this.parts[0];
        this.parts[0] = partsLine;
        return returnedCubeColor;
    }

    public void setPartsLine(CubeColor[] partsLine, int line) {
        this.parts[line] = partsLine;
    }
    public void setPartsRow(CubeColor[] partsRow , int row) {
        this.parts[0][row] = partsRow[0];
        this.parts[1][row] = partsRow[1];
        this.parts[2][row] = partsRow[2];
    }





    @Override
    public String toString() {
        return Arrays.deepToString(parts);
    }
}
