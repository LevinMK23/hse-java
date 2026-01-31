package hse.java.practice.task1;

import java.util.Arrays;

public class Edge {

    private CubeColor[][] parts;

    public Edge(CubeColor[][] parts) {
        this.parts = parts;
    }

    public Edge(CubeColor color) {
        this.parts = new CubeColor[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                parts[i][j] = color;
            }
        }
    }

    public Edge() {
        parts = new CubeColor[3][3];
    }

    public CubeColor[][] getParts() {
        return parts;
    }

    public void setParts(CubeColor[][] parts) {
        this.parts = parts;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(parts);
    }
    //TODO:
    public void rotateFace(RotateDirection direction){

        switch (direction){
            case CLOCKWISE:
                rotateFaceClockwise();
                break;
            case COUNTERCLOCKWISE:
                rotateFaceCounterClockwise();
                break;
        }

    }

    private void rotateFaceClockwise(){
        CubeColor[][] transpotedColors = new CubeColor[3][3];

        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            for (int colIndex = 0; colIndex < 3; colIndex++) {
                transpotedColors[rowIndex][colIndex] = parts[colIndex][rowIndex];
            }
        }

        parts = transpotedColors.clone();
    }

    private void rotateFaceCounterClockwise(){
        for (int i = 0; i < 3; i++) {
            rotateFaceClockwise();
        }
    }

    public CubeColor[] getRow(int index) {
        return parts[index];
    }


    public void setRow(int index, CubeColor[] colors){
        parts[index] = colors;
    }

    public CubeColor[] getCol(int index){
        CubeColor[] tmp = new CubeColor[3];

        for (int rowIndex : new int[]{0, 1, 2}){
            tmp[rowIndex] = parts[rowIndex][index];
        }

        return tmp;
    }

    public void setCol(int index, CubeColor[] colors){
        for (int rowIndex : new int[]{0,1,2}) {
            parts[rowIndex][index] = colors[rowIndex];
        }
    }

}

