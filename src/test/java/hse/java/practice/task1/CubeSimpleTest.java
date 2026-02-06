package hse.java.practice.task1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class CubeSimpleTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private CubeColor[][][] readStateFromFile(String filename) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("hse/java/practice/task1/" + filename)) {
            return MAPPER.readValue(is, CubeColor[][][].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void printTest() {
        RubiksCube cube = new RubiksCube();
        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            Edge edge = cube.getEdges()[i];
            CubeColor[][] edgeColors = edge.getParts();
            for (CubeColor[] row : edgeColors) {
                for (CubeColor color : row) {
                    Assertions.assertEquals(color, colors[i]);
                }
            }
        }
    }

    @Test
    void frontClockwise() {
        RubiksCube cube = new RubiksCube();
        cube.front(RotateDirection.CLOCKWISE);

        CubeColor[][][] state = readStateFromFile("frontClockwieseState.json");

        CubeColor[][][] actuallyState = Arrays.stream(cube.getEdges())
                .map(Edge::getParts)
                .toArray(CubeColor[][][]::new);

        Assertions.assertArrayEquals(state, actuallyState);
    }

    @Test
    void frontCounterclockwise() {
        RubiksCube cube = new RubiksCube();
        cube.front(RotateDirection.COUNTERCLOCKWISE);
        cube.front(RotateDirection.COUNTERCLOCKWISE);
        cube.front(RotateDirection.COUNTERCLOCKWISE);
        cube.front(RotateDirection.COUNTERCLOCKWISE);

        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            Edge edge = cube.getEdges()[i];
            CubeColor[][] edgeColors = edge.getParts();
            for (CubeColor[] row : edgeColors) {
                for (CubeColor color : row) {
                    Assertions.assertEquals(colors[i], color);
                }
            }
        }
    }

    @Test
    void upClockwise() {
        RubiksCube cube = new RubiksCube();
        cube.up(RotateDirection.CLOCKWISE);

        CubeColor[][][] state = readStateFromFile("upClockwiseState.json");

        CubeColor[][][] actuallyState = Arrays.stream(cube.getEdges())
                .map(Edge::getParts)
                .toArray(CubeColor[][][]::new);

        Assertions.assertArrayEquals(state, actuallyState);
    }

    @Test
    void upCounterclockwise() {
        RubiksCube cube = new RubiksCube();
        cube.up(RotateDirection.COUNTERCLOCKWISE);
        cube.up(RotateDirection.COUNTERCLOCKWISE);
        cube.up(RotateDirection.COUNTERCLOCKWISE);
        cube.up(RotateDirection.COUNTERCLOCKWISE);

        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            Edge edge = cube.getEdges()[i];
            CubeColor[][] edgeColors = edge.getParts();
            for (CubeColor[] row : edgeColors) {
                for (CubeColor color : row) {
                    Assertions.assertEquals(colors[i], color);
                }
            }
        }
    }

    @Test
    void downClockwise() {
        RubiksCube cube = new RubiksCube();
        cube.down(RotateDirection.CLOCKWISE);

        CubeColor[][][] state = readStateFromFile("downClockwiseState.json");

        CubeColor[][][] actuallyState = Arrays.stream(cube.getEdges())
                .map(Edge::getParts)
                .toArray(CubeColor[][][]::new);

        Assertions.assertArrayEquals(state, actuallyState);
    }

    @Test
    void downCounterclockwise() {
        RubiksCube cube = new RubiksCube();
        cube.down(RotateDirection.COUNTERCLOCKWISE);
        cube.down(RotateDirection.COUNTERCLOCKWISE);
        cube.down(RotateDirection.COUNTERCLOCKWISE);
        cube.down(RotateDirection.COUNTERCLOCKWISE);

        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            Edge edge = cube.getEdges()[i];
            CubeColor[][] edgeColors = edge.getParts();
            for (CubeColor[] row : edgeColors) {
                for (CubeColor color : row) {
                    Assertions.assertEquals(colors[i], color);
                }
            }
        }
    }

    @Test
    void leftClockwise() {
        RubiksCube cube = new RubiksCube();
        cube.left(RotateDirection.CLOCKWISE);

        CubeColor[][][] state = readStateFromFile("leftClockwiseState.json");

        CubeColor[][][] actuallyState = Arrays.stream(cube.getEdges())
                .map(Edge::getParts)
                .toArray(CubeColor[][][]::new);

        Assertions.assertArrayEquals(state, actuallyState);
    }

    @Test
    void leftCounterclockwise() {
        RubiksCube cube = new RubiksCube();
        cube.left(RotateDirection.COUNTERCLOCKWISE);
        cube.left(RotateDirection.COUNTERCLOCKWISE);
        cube.left(RotateDirection.COUNTERCLOCKWISE);
        cube.left(RotateDirection.COUNTERCLOCKWISE);

        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            Edge edge = cube.getEdges()[i];
            CubeColor[][] edgeColors = edge.getParts();
            for (CubeColor[] row : edgeColors) {
                for (CubeColor color : row) {
                    Assertions.assertEquals(colors[i], color);
                }
            }
        }
    }

    @Test
    void rightClockwise() {
        RubiksCube cube = new RubiksCube();
        cube.right(RotateDirection.CLOCKWISE);

        CubeColor[][][] state = readStateFromFile("rightClockwiseState.json");

        CubeColor[][][] actuallyState = Arrays.stream(cube.getEdges())
                .map(Edge::getParts)
                .toArray(CubeColor[][][]::new);

        Assertions.assertArrayEquals(state, actuallyState);
    }

    @Test
    void rightCounterclockwise() {
        RubiksCube cube = new RubiksCube();
        cube.right(RotateDirection.COUNTERCLOCKWISE);
        cube.right(RotateDirection.COUNTERCLOCKWISE);
        cube.right(RotateDirection.COUNTERCLOCKWISE);
        cube.right(RotateDirection.COUNTERCLOCKWISE);

        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            Edge edge = cube.getEdges()[i];
            CubeColor[][] edgeColors = edge.getParts();
            for (CubeColor[] row : edgeColors) {
                for (CubeColor color : row) {
                    Assertions.assertEquals(colors[i], color);
                }
            }
        }
    }

    @Test
    void backClockwise() {
        RubiksCube cube = new RubiksCube();
        cube.back(RotateDirection.CLOCKWISE);

        CubeColor[][][] state = readStateFromFile("backClockwiseState.json");

        CubeColor[][][] actuallyState = Arrays.stream(cube.getEdges())
                .map(Edge::getParts)
                .toArray(CubeColor[][][]::new);

        Assertions.assertArrayEquals(state, actuallyState);
    }

    @Test
    void backCounterclockwise() {
        RubiksCube cube = new RubiksCube();
        cube.back(RotateDirection.COUNTERCLOCKWISE);
        cube.back(RotateDirection.COUNTERCLOCKWISE);
        cube.back(RotateDirection.COUNTERCLOCKWISE);
        cube.back(RotateDirection.COUNTERCLOCKWISE);

        CubeColor[] colors = CubeColor.values();
        for (int i = 0; i < 6; i++) {
            Edge edge = cube.getEdges()[i];
            CubeColor[][] edgeColors = edge.getParts();
            for (CubeColor[] row : edgeColors) {
                for (CubeColor color : row) {
                    Assertions.assertEquals(colors[i], color);
                }
            }
        }
    }

    @Test
    void combinedRotations() {
        RubiksCube cube = new RubiksCube();
        cube.front(RotateDirection.CLOCKWISE);
        cube.right(RotateDirection.CLOCKWISE);
        cube.up(RotateDirection.CLOCKWISE);
    }
}
