package com.codecool.marsexploration.logic.map_generator;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;
import com.codecool.marsexploration.logic.generating_strategy.RandomAreaShaper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MapGeneratorTest {

    @Test
    void validatorTest() {

        ConfigurationValidator configValidator = new ConfigurationValidator();
        Character[][] fullMap = new Character[][]{{'#', '#'}, {'#', '#'}};
        Character[][] emptyMap = new Character[][]{{' ', ' '}, {' ', ' '}};
        Character[][] area = new Character[][]{{'^', ' '}};
        Coordinate origin = new Coordinate(0, 0);
        boolean actual1 = configValidator.isAreaPlacable(fullMap, area, origin);
        boolean actual2 = configValidator.isAreaPlacable(emptyMap, area, origin);
        assertFalse(actual1);
        assertTrue(actual2);

    }

    @ParameterizedTest
    @MethodSource("terrainElementMap")
    void generateTest(int[] mountainArray,
                      int[] pitArray,
                      int[] mineralArray,
                      int[] waterArray,
                      String expected) {
        Random random = new Random();
        Character[][] map;
        int MAP_WIDTH = 25;
        int MAP_HEIGHT = 25;
        String PATH = "test.map";
        Map<TerrainElement, int[]> terrainElements = Map.of(
                TerrainElement.MOUNTAIN, mountainArray,
                TerrainElement.PIT, pitArray,
                TerrainElement.MINERAL, mineralArray,
                TerrainElement.WATER, waterArray);


        MapConfiguration config = new MapConfiguration(MAP_WIDTH, MAP_HEIGHT, PATH, terrainElements);
        MapGenerator generator = new MapGenerator(config, random, new RandomAreaShaper());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        generator.generate();

        assertEquals(expected, outContent.toString());

    }

    public static Stream<Arguments> terrainElementMap() {

        return Stream.of(
                /*tul sok mountain*/ Arguments.arguments(new int[]{100, 200, 1000}, new int[]{1}, new int[]{1}, new int[]{1}, "unable to place area\r\n"),
                /*tul sok pit*/ Arguments.arguments(new int[]{1}, new int[]{100, 200, 1000}, new int[]{1}, new int[]{1}, "unable to place area\r\n"),
                /*tul sok mineral*/ Arguments.arguments(new int[]{1}, new int[]{1}, new int[]{1000}, new int[]{1}, "can't place all resources\r\n"),
                /*tul sok water*/ Arguments.arguments(new int[]{1}, new int[]{1}, new int[]{1}, new int[]{1000}, "can't place all resources\r\n"),
                /*minden jo*/ Arguments.arguments(new int[]{10, 20, 30}, new int[]{10, 20, 30}, new int[]{20}, new int[]{20}, "")
        );
    }

}