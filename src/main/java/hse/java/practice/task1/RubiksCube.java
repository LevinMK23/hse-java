package hse.java.practice.task1;

import java.util.Arrays;

public class RubiksCube implements Cube {

    private static final int SIZE = 3;
    private final Edge[] edges = new Edge[6];

    private static class FaceConnection {
        EdgePosition sourceFace;
        int sourceRow;
        int sourceCol;
        boolean isRow;
        boolean reverse;

        FaceConnection(EdgePosition face, int row, int col, boolean isRow, boolean reverse) {
            this.sourceFace = face;
            this.sourceRow = row;
            this.sourceCol = col;
            this.isRow = isRow;
            this.reverse = reverse;
        }
    }

    public RubiksCube() {
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            edges[i] = new Edge(colors[i]);
        }
    }

    private void rotateFace(EdgePosition facePos, RotateDirection direction) {
        CubeColor[][] matrix = edges[facePos.ordinal()].getParts();
        CubeColor[][] rotated = new CubeColor[SIZE][SIZE];

        if (direction == RotateDirection.CLOCKWISE) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    rotated[i][j] = matrix[SIZE - 1 - j][i];
                }
            }
        } else {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    rotated[i][j] = matrix[j][SIZE - 1 - i];
                }
            }
        }

        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(rotated[i], 0, matrix[i], 0, SIZE);
        }
    }

    private CubeColor[] getSequence(EdgePosition face, int index, boolean isRow, boolean reverse) {
        CubeColor[] result = new CubeColor[SIZE];
        CubeColor[][] parts = edges[face.ordinal()].getParts();

        if (isRow) {
            for (int i = 0; i < SIZE; i++) {
                result[i] = parts[index][i];
            }
        } else {
            for (int i = 0; i < SIZE; i++) {
                result[i] = parts[i][index];
            }
        }

        if (reverse) {
            CubeColor[] reversed = new CubeColor[SIZE];
            for (int i = 0; i < SIZE; i++) {
                reversed[i] = result[SIZE - 1 - i];
            }
            return reversed;
        }
        return result;
    }

    private void setSequence(EdgePosition face, int index, boolean isRow, CubeColor[] data) {
        CubeColor[][] parts = edges[face.ordinal()].getParts();

        if (isRow) {
            System.arraycopy(data, 0, parts[index], 0, SIZE);
        } else {
            for (int i = 0; i < SIZE; i++) {
                parts[i][index] = data[i];
            }
        }
    }

    private void rotateWithTransfers(EdgePosition rotatingFace,
                                     FaceConnection[] transfers,
                                     RotateDirection direction) {
        rotateFace(rotatingFace, direction);

        CubeColor[] temp = getSequence(
                transfers[0].sourceFace,
                transfers[0].isRow ? transfers[0].sourceRow : transfers[0].sourceCol,
                transfers[0].isRow,
                transfers[0].reverse
        );

        for (int i = 0; i < 3; i++) {
            CubeColor[] current = getSequence(
                    transfers[i + 1].sourceFace,
                    transfers[i + 1].isRow ? transfers[i + 1].sourceRow : transfers[i + 1].sourceCol,
                    transfers[i + 1].isRow,
                    transfers[i + 1].reverse
            );

            setSequence(
                    transfers[i].sourceFace,
                    transfers[i].isRow ? transfers[i].sourceRow : transfers[i].sourceCol,
                    transfers[i].isRow,
                    current
            );
        }

        setSequence(
                transfers[3].sourceFace,
                transfers[3].isRow ? transfers[3].sourceRow : transfers[3].sourceCol,
                transfers[3].isRow,
                temp
        );
    }

    @Override
    public void up(RotateDirection direction) {
        FaceConnection[] transfers = {
                new FaceConnection(EdgePosition.BACK, 0, -1, true, false),
                new FaceConnection(EdgePosition.LEFT, 0, -1, true, false),
                new FaceConnection(EdgePosition.FRONT, 0, -1, true, false),
                new FaceConnection(EdgePosition.RIGHT, 0, -1, true, false)
        };
        rotateWithTransfers(EdgePosition.UP, transfers, direction);
    }

    @Override
    public void down(RotateDirection direction) {
        FaceConnection[] transfers = {
                new FaceConnection(EdgePosition.FRONT, 2, -1, true, false),
                new FaceConnection(EdgePosition.LEFT, 2, -1, true, false),
                new FaceConnection(EdgePosition.BACK, 2, -1, true, false),
                new FaceConnection(EdgePosition.RIGHT, 2, -1, true, false)
        };
        rotateWithTransfers(EdgePosition.DOWN, transfers,
                direction == RotateDirection.CLOCKWISE ? RotateDirection.COUNTERCLOCKWISE : RotateDirection.CLOCKWISE);
    }

    @Override
    public void left(RotateDirection direction) {
        FaceConnection[] transfers = {
                new FaceConnection(EdgePosition.UP, -1, 0, false, false),
                new FaceConnection(EdgePosition.BACK, -1, 2, false, true),
                new FaceConnection(EdgePosition.DOWN, -1, 0, false, false),
                new FaceConnection(EdgePosition.FRONT, -1, 0, false, false)
        };
        rotateWithTransfers(EdgePosition.LEFT, transfers, direction);
    }

    @Override
    public void right(RotateDirection direction) {
        FaceConnection[] transfers = {
                new FaceConnection(EdgePosition.UP, -1, 2, false, true),
                new FaceConnection(EdgePosition.FRONT, -1, 2, false, true),
                new FaceConnection(EdgePosition.DOWN, -1, 2, false, true),
                new FaceConnection(EdgePosition.BACK, -1, 0, false, true)
        };
        rotateWithTransfers(EdgePosition.RIGHT, transfers, direction);
    }

    @Override
    public void front(RotateDirection direction) {
        FaceConnection[] transfers = {
                new FaceConnection(EdgePosition.UP, 2, -1, true, false),
                new FaceConnection(EdgePosition.LEFT, -1, 2, false, true),
                new FaceConnection(EdgePosition.DOWN, 0, -1, true, false),
                new FaceConnection(EdgePosition.RIGHT, -1, 0, false, true)
        };
        rotateWithTransfers(EdgePosition.FRONT, transfers, direction);
    }


    @Override
    public void back(RotateDirection direction) {
        FaceConnection[] transfers = {
                new FaceConnection(EdgePosition.UP, 0, -1, true, true),
                new FaceConnection(EdgePosition.RIGHT, -1, 2, false, true),
                new FaceConnection(EdgePosition.DOWN, 2, -1, true, true),
                new FaceConnection(EdgePosition.LEFT, -1, 0, false, true)
        };
        rotateWithTransfers(EdgePosition.BACK, transfers, direction);
    }

    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return Arrays.toString(edges);
    }
}