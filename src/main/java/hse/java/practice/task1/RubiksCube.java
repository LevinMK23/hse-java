package hse.java.practice.task1;

import java.util.Arrays;


/**
 * Необходимо реализовать интерфейс Cube
 * При повороте передней грани, меняются верх низ право и лево
 */
public class RubiksCube implements Cube
{

    private static final int EDGES_COUNT = 6;

    private final Edge[] edges = new Edge[EDGES_COUNT];

    /**
     * Создать валидный собранный кубик
     * грани разместить по ордеру в енуме цветов
     * грань 0 -> цвет 0
     * грань 1 -> цвет 1
     * ...
     */
    public RubiksCube()
    {
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++)
        {
            edges[i] = new Edge(colors[i]);
        }
    }

    @Override
    public void up(RotateDirection direction)
    {
        if (direction == RotateDirection.COUNTERCLOCKWISE)
        {
            up(RotateDirection.CLOCKWISE);
            up(RotateDirection.CLOCKWISE);
            up(RotateDirection.CLOCKWISE);
            return;
        }

        rotateFaceMatrix(EdgePosition.UP);

        CubeColor[][] front = getMatrix(EdgePosition.FRONT);
        CubeColor[][] left = getMatrix(EdgePosition.LEFT);
        CubeColor[][] back = getMatrix(EdgePosition.BACK);
        CubeColor[][] right = getMatrix(EdgePosition.RIGHT);

        CubeColor[] tmp = new CubeColor[3];

        for (int i = 0; i < 3; ++i) tmp[i] = front[0][i];

        for (int i = 0; i < 3; ++i) front[0][i] = right[0][i];

        for (int i = 0; i < 3; ++i) right[0][i] = back[0][i];

        for (int i = 0; i < 3; ++i) back[0][i] = left[0][i];

        for (int i = 0; i < 3; ++i) left[0][i] = tmp[i];
    }

    @Override
    public void down(RotateDirection direction)
    {
        if (direction == RotateDirection.COUNTERCLOCKWISE)
        {
            down(RotateDirection.CLOCKWISE);
            down(RotateDirection.CLOCKWISE);
            down(RotateDirection.CLOCKWISE);
            return;
        }

        rotateFaceMatrix(EdgePosition.DOWN);

        CubeColor[][] front = getMatrix(EdgePosition.FRONT);
        CubeColor[][] right = getMatrix(EdgePosition.RIGHT);
        CubeColor[][] back = getMatrix(EdgePosition.BACK);
        CubeColor[][] left = getMatrix(EdgePosition.LEFT);

        CubeColor[] tmp = new CubeColor[3];

        for (int i = 0; i < 3; ++i) tmp[i] = front[2][i];

        for (int i = 0; i < 3; ++i) front[2][i] = left[2][i];

        for (int i = 0; i < 3; ++i) left[2][i] = back[2][i];

        for (int i = 0; i < 3; ++i) back[2][i] = right[2][i];

        for (int i = 0; i < 3; ++i) right[2][i] = tmp[i];

    }

    @Override
    public void left(RotateDirection direction)
    {
        if (direction == RotateDirection.COUNTERCLOCKWISE)
        {
            left(RotateDirection.CLOCKWISE);
            left(RotateDirection.CLOCKWISE);
            left(RotateDirection.CLOCKWISE);
            return;
        }

        rotateFaceMatrix(EdgePosition.LEFT);

        CubeColor[][] up = getMatrix(EdgePosition.UP);
        CubeColor[][] front = getMatrix(EdgePosition.FRONT);
        CubeColor[][] down = getMatrix(EdgePosition.DOWN);
        CubeColor[][] back = getMatrix(EdgePosition.BACK);

        CubeColor[] tmp = new CubeColor[3];

        for (int i = 0; i < 3; ++i) tmp[i] = up[i][0];

        for (int i = 0; i < 3; i++) up[i][0] = back[2 - i][2];

        for (int i = 0; i < 3; i++) back[i][2] = down[2 - i][0];

        for (int i = 0; i < 3; i++) down[i][0] = front[i][0];

        for (int i = 0; i < 3; i++) front[i][0] = tmp[i];

    }

    @Override
    public void right(RotateDirection direction) {
        if (direction == RotateDirection.COUNTERCLOCKWISE) {
            right(RotateDirection.CLOCKWISE);
            right(RotateDirection.CLOCKWISE);
            right(RotateDirection.CLOCKWISE);
            return;
        }

        rotateFaceMatrix(EdgePosition.RIGHT);


        CubeColor[][] up = getMatrix(EdgePosition.UP);
        CubeColor[][] back = getMatrix(EdgePosition.BACK);
        CubeColor[][] down = getMatrix(EdgePosition.DOWN);
        CubeColor[][] front = getMatrix(EdgePosition.FRONT);

        CubeColor[] temp = new CubeColor[3];

        for (int i = 0; i < 3; i++) temp[i] = up[i][2];

        for (int i = 0; i < 3; i++) up[i][2] = front[i][2];

        for (int i = 0; i < 3; i++) front[i][2] = down[i][2];

        for (int i = 0; i < 3; i++) down[i][2] = back[2 - i][0];

        for (int i = 0; i < 3; i++) back[i][0] = temp[2 - i];
    }

    public void front(RotateDirection direction)
    {
        if (direction == RotateDirection.COUNTERCLOCKWISE)
        {
            front(RotateDirection.CLOCKWISE);
            front(RotateDirection.CLOCKWISE);
            front(RotateDirection.CLOCKWISE);
            return;
        }

        rotateFaceMatrix(EdgePosition.FRONT);

        CubeColor[][] up = getMatrix(EdgePosition.UP);
        CubeColor[][] right = getMatrix(EdgePosition.RIGHT);
        CubeColor[][] down = getMatrix(EdgePosition.DOWN);
        CubeColor[][] left = getMatrix(EdgePosition.LEFT);


        CubeColor[] tmp = new CubeColor[3];

        for (int i = 0; i < 3; ++i) tmp[i] = up[2][i];

        for (int i = 0; i < 3; ++i) up[2][i] = left[2 - i][2];

        for (int i = 0; i < 3; ++i) left[i][2] = down[0][i];

        for (int i = 0; i < 3; ++i) down[0][i] = right[2 - i][0];

        for (int i = 0; i < 3; ++i) right[i][0] = tmp[i];

    }

    @Override
    public void back(RotateDirection direction)
    {
        if (direction == RotateDirection.COUNTERCLOCKWISE)
        {
            back(RotateDirection.CLOCKWISE);
            back(RotateDirection.CLOCKWISE);
            back(RotateDirection.CLOCKWISE);
            return;
        }

        rotateFaceMatrix(EdgePosition.BACK);

        CubeColor[][] up = getMatrix(EdgePosition.UP);
        CubeColor[][] left = getMatrix(EdgePosition.LEFT);
        CubeColor[][] down = getMatrix(EdgePosition.DOWN);
        CubeColor[][] right = getMatrix(EdgePosition.RIGHT);

        CubeColor[] tmp = new CubeColor[3];

        for (int i = 0; i < 3; ++i) tmp[i] = up[0][i];

        for (int i = 0; i < 3; ++i) up[0][i] = right[i][2];

        for (int i = 0; i < 3; ++i) right[i][2] = down[2][2 - i];

        for (int i = 0; i < 3; ++i) down[2][i] = left[i][0];

        for (int i = 0; i < 3; ++i) left[i][0] = tmp[2 - i];
    }

    public Edge[] getEdges()
    {
        return edges;
    }

    @Override
    public String toString()
    {
        return Arrays.toString(edges);
    }


    private CubeColor[][] getMatrix(EdgePosition pos)
    {
        return edges[pos.ordinal()].getParts();
    }

    private void rotateFaceMatrix(EdgePosition pos)
    {
        CubeColor[][] matrix = getMatrix(pos);

        transpose(matrix);
        reverseRows(matrix);

    }


    private void transpose(CubeColor[][] matrix)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int j = i + 1; j < 3; j++)
            {
                CubeColor tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    }

    private void reverseRows(CubeColor[][] matrix)
    {
        for (int i = 0; i < 3; i++)
        {
            CubeColor tmp = matrix[i][0];
            matrix[i][0] = matrix[i][2];
            matrix[i][2] = tmp;
        }
    }
}
