package hse.java.practice.task1;

import java.util.Arrays;

/**
 * Необходимо реализовать интерфейс Cube
 * При повороте передней грани, меняются верх низ право и лево
 */
public class RubiksCube implements Cube{

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
    public void up(RotateDirection direction) {
        switch (direction){
            case CLOCKWISE:
                rotateUpClockwise();
                break;
            case COUNTERCLOCKWISE:
                rotateUpCounterClockwise();
                break;
        }
    }

    public void rotateUpClockwise(){
        edges[0].rotateFace(RotateDirection.CLOCKWISE);

        CubeColor[] rightToFront = edges[3].getRow(0);

        CubeColor[] backToRight = edges[5].getRow(0);
        edges[3].setRow(0,backToRight);

        CubeColor[] lefToBack = edges[2].getRow(0);
        edges[5].setRow(0, lefToBack);

        CubeColor[] frontToLeft = edges[4].getRow(0);
        edges[2].setRow(0, frontToLeft);

        edges[4].setRow(0, rightToFront);

    }

    public void rotateUpCounterClockwise(){
        rotateUpClockwise();
        rotateUpClockwise();
        rotateUpClockwise();
    }

    @Override
    public void down(RotateDirection direction) {
        switch (direction){
            case CLOCKWISE:
                rotateDownClockwise();
                break;
            case COUNTERCLOCKWISE:
                rotateDownCounterClockwise();
                break;
        }
    }

    public void rotateDownClockwise(){
        edges[1].rotateFace(RotateDirection.CLOCKWISE);

        CubeColor[] rightToBack = edges[3].getRow(2);

        CubeColor[] frontToRight = edges[4].getRow(2);
        edges[3].setRow(2, frontToRight);

        CubeColor[] leftToFront = edges[2].getRow(2);
        edges[4].setRow(2, leftToFront);

        CubeColor[] backToLeft = edges[5].getRow(2);
        edges[2].setRow(2, backToLeft);

        edges[5].setRow(2, rightToBack);

    }

    public void rotateDownCounterClockwise(){
        rotateDownClockwise();
        rotateDownClockwise();
        rotateDownClockwise();
    }

    @Override
    public void left(RotateDirection direction) {
        switch (direction){
            case CLOCKWISE:
                rotateLeftClockwise();
                break;
            case COUNTERCLOCKWISE:
                rotateLeftCounterClockwise();
                break;
        }
    }

    public void rotateLeftClockwise(){
        edges[2].rotateFace(RotateDirection.CLOCKWISE);

        CubeColor[] backToUp = edges[5].getCol(2);


        CubeColor[] downToBack = edges[1].getCol(0);
        downToBack = reverseArr(downToBack);
        edges[5].setCol(2, downToBack);

        CubeColor[] frontToDown = edges[4].getCol(0);
        edges[1].setCol(0, frontToDown);

        CubeColor[] upToFront = edges[0].getCol(0);
        edges[4].setCol(0, upToFront);

        backToUp = reverseArr(backToUp);
        edges[0].setCol(0, backToUp);

    }

    public void rotateLeftCounterClockwise(){
        rotateLeftClockwise();
        rotateLeftClockwise();
        rotateLeftClockwise();
    }

    @Override
    public void right(RotateDirection direction) {
        switch (direction){
            case CLOCKWISE:
                rotateRightClockwise();
                break;
            case COUNTERCLOCKWISE:
                rotateRightCounterClockwise();
                break;
        }
    }

    public void rotateRightClockwise(){
        edges[3].rotateFace(RotateDirection.CLOCKWISE);

        CubeColor[] upToBack = edges[0].getCol(2);

        CubeColor[] frontToUp = edges[4].getCol(2);
        edges[0].setCol(2,frontToUp);

        CubeColor[] downToFront = edges[1].getCol(2);
        edges[4].setCol(2, downToFront);

        CubeColor[] backToDown = edges[5].getCol(0);
        backToDown = reverseArr(backToDown);
        edges[1].setCol(2, backToDown);

        upToBack = reverseArr(upToBack);
        edges[5].setCol(0, upToBack);
    }

    public void rotateRightCounterClockwise(){
        rotateRightClockwise();
        rotateRightClockwise();
        rotateRightClockwise();
    }
    
    @Override
    public void front(RotateDirection direction) {

        switch (direction){
            case CLOCKWISE:
                rotateFrontClockwise();
                break;
            case COUNTERCLOCKWISE:
                rotateFrontCounterClockwise();
                break;
        }
    }

    @Override
    public void back(RotateDirection direction) {
        switch (direction){
            case CLOCKWISE:
                rotateBackClockwise();
                break;
            case COUNTERCLOCKWISE:
                rotateBackCounterClockwise();
                break;
        }
    }

    public void rotateBackClockwise(){
    }

    public void rotateBackCounterClockwise(){
        rotateBackClockwise();
        rotateBackClockwise();
        rotateBackClockwise();
    }

    public Edge[] getEdges() {
        return edges;
    }
    
    public void rotateFrontClockwise(){
        edges[4].rotateFace(RotateDirection.CLOCKWISE);

        CubeColor[] upToRight = edges[0].getRow(2);

        CubeColor[] leftToUp = edges[2].getCol(2);
        leftToUp = reverseArr(leftToUp);
        edges[0].setRow(2, leftToUp);

        CubeColor[] downToLeft = edges[1].getRow(0);
        edges[2].setCol(2,downToLeft);

        CubeColor[] rightToDown = edges[3].getCol(0);
        rightToDown = reverseArr(rightToDown);
        edges[1].setRow(0,rightToDown);

        edges[3].setCol(0, upToRight);

    }

    public void rotateFrontCounterClockwise(){
        rotateFrontClockwise();
        rotateFrontClockwise();
        rotateFrontClockwise();
    }

    private CubeColor[] reverseArr(CubeColor[] arr){
        CubeColor[] tmp = arr.clone();
        int len = arr.length;
        for (int i = 0; i < len; i++){
            tmp[i] = arr[len - 1 - i];
        }
        return tmp;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}
