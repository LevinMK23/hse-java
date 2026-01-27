package hse.java.practice.task1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CubeSimpleTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

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

    private CubeColor[][][] readStateFromFile(String fileName) {
        String resourcePath = "hse/java/practice/task1/" + fileName;
        try (InputStream is = CubeSimpleTest.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return MAPPER.readValue(json, CubeColor[][][].class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read/parse state file: " + fileName, e);
        }
    }
}
