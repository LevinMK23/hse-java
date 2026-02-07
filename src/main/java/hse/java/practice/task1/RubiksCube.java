package hse.java.practice.task1;

import java.util.Arrays;
import java.util.function.Consumer;


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

    private void rotateSides(SideLine s0, SideLine s1, SideLine s2, SideLine s3)
    {
        CubeColor[] line0 = getLine(s0);
        CubeColor[] line1 = getLine(s1);
        CubeColor[] line2 = getLine(s2);
        CubeColor[] line3 = getLine(s3);

        setLine(s1, line0);
        setLine(s2, line1);
        setLine(s3, line2);
        setLine(s0, line3);
    }

    private CubeColor[] getLine(SideLine s)
    {
        CubeColor[][] matrix = getMatrix(s.pos);
        CubeColor[] res = new CubeColor[3];
        for (int i = 0; i < 3; ++i)
        {
            res[i] = s.isRow ? matrix[s.idx][i] : matrix[i][s.idx];
        }
        if (s.rev)
        {
            CubeColor tmp = res[0];
            res[0] = res[2];
            res[2] = tmp;
        }
        return res;
    }

    private void setLine(SideLine s, CubeColor[] vals)
    {
        CubeColor[][] matrix = getMatrix(s.pos);
        CubeColor[] data = s.rev ? new CubeColor[]{vals[2], vals[1], vals[0]} : vals;

        for (int i = 0; i < 3; ++i)
        {
            if (s.isRow) matrix[s.idx][i] = data[i];
            else matrix[i][s.idx] = data[i];
        }
    }

    @Override
    public void up(RotateDirection direction)
    {
        if (direction == RotateDirection.COUNTERCLOCKWISE)
        {
            rotate3(this::up);
            return;
        }

        rotateFaceMatrix(EdgePosition.UP);
        rotateSides(
                new SideLine(EdgePosition.FRONT, 0, true, false),
                new SideLine(EdgePosition.LEFT, 0, true, false),
                new SideLine(EdgePosition.BACK, 0, true, false),
                new SideLine(EdgePosition.RIGHT, 0, true, false)
        );
    }

    @Override
    public void down(RotateDirection direction)
    {
        if (direction == RotateDirection.COUNTERCLOCKWISE)
        {
            rotate3(this::down);
            return;
        }

        rotateFaceMatrix(EdgePosition.DOWN);

        rotateSides(
                new SideLine(EdgePosition.FRONT, 2, true, false),
                new SideLine(EdgePosition.RIGHT, 2, true, false),
                new SideLine(EdgePosition.BACK, 2, true, false),
                new SideLine(EdgePosition.LEFT, 2, true, false)
        );

    }

    @Override
    public void left(RotateDirection direction)
    {
        if (direction == RotateDirection.COUNTERCLOCKWISE)
        {
            rotate3(this::left);
            return;
        }

        rotateFaceMatrix(EdgePosition.LEFT);

        rotateSides(
                new SideLine(EdgePosition.UP, 0, false, false),
                new SideLine(EdgePosition.FRONT, 0, false, false),
                new SideLine(EdgePosition.DOWN, 0, false, false),
                new SideLine(EdgePosition.BACK, 2, false, true)
        );

    }

    @Override
    public void right(RotateDirection direction)
    {
        if (direction == RotateDirection.COUNTERCLOCKWISE)
        {
            rotate3(this::right);
            return;
        }

        rotateFaceMatrix(EdgePosition.RIGHT);

        rotateSides(
                new SideLine(EdgePosition.UP, 2, false, false),
                new SideLine(EdgePosition.BACK, 0, false, true),
                new SideLine(EdgePosition.DOWN, 2, false, false),
                new SideLine(EdgePosition.FRONT, 2, false, false)
        );
    }

    public void front(RotateDirection direction)
    {
        if (direction == RotateDirection.COUNTERCLOCKWISE)
        {
            rotate3(this::front);
            return;
        }

        rotateFaceMatrix(EdgePosition.FRONT);

        rotateSides(
                new SideLine(EdgePosition.UP, 2, true, false),
                new SideLine(EdgePosition.RIGHT, 0, false, false),
                new SideLine(EdgePosition.DOWN, 0, true, true),
                new SideLine(EdgePosition.LEFT, 2, false, true)
        );

    }

    @Override
    public void back(RotateDirection direction)
    {
        if (direction == RotateDirection.COUNTERCLOCKWISE)
        {
            rotate3(this::back);
            return;
        }

        rotateFaceMatrix(EdgePosition.BACK);

        rotateSides(
                new SideLine(EdgePosition.UP, 0, true, true),
                new SideLine(EdgePosition.LEFT, 0, false, false),
                new SideLine(EdgePosition.DOWN, 2, true, false),
                new SideLine(EdgePosition.RIGHT, 2, false, true)
        );
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

    private void rotate3(Consumer<RotateDirection> f)
    {
        f.accept(RotateDirection.CLOCKWISE);
        f.accept(RotateDirection.CLOCKWISE);
        f.accept(RotateDirection.CLOCKWISE);
    }

    private static class SideLine
    {
        EdgePosition pos;
        int idx;
        boolean isRow;
        boolean rev;

        SideLine(EdgePosition p, int i, boolean row, boolean r)
        {
            pos = p;
            idx = i;
            isRow = row;
            rev = r;
        }
    }


}
