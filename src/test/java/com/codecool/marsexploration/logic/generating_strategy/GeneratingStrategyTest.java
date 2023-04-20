package com.codecool.marsexploration.logic.generating_strategy;

import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;
import com.codecool.marsexploration.logic.map_generator.MapGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GeneratingStrategyTest {
    Random random;
    MapConfiguration config;
    MapGenerator generator;
    Character[][] map;
    int MAP_WIDTH = 25;
    int MAP_HEIGHT = 25;
    String PATH = "test.map";

    Map<TerrainElement, int[]> terrainElements = Map.of(
            TerrainElement.MOUNTAIN, new int[]{10, 20, 30},
            TerrainElement.PIT, new int[]{7, 8, 9},
            TerrainElement.MINERAL, new int[]{2, 3, 4},
            TerrainElement.WATER, new int[]{5, 6, 7});

    @BeforeEach
    public void setupFields() {
        random = new Random();
        config = new MapConfiguration(MAP_WIDTH, MAP_HEIGHT, PATH, terrainElements);
        generator = new MapGenerator(config, random, new RandomAreaShaper());
        map = generator.generate();
    }

    @Test
    void inorderShapeGeneratorTest() {
        InorderAreaShaper inorderAreaShaper = new InorderAreaShaper();
        Character[][] area = inorderAreaShaper.generateAreas(TerrainElement.MOUNTAIN, 0, config, random);
        int expectedNumberOfTerrainElements = 10;
        int expected2DArrayLength = 4;
        int actualNumberOfTerrainElements = 0;
        for (Character[] line : area) {
            for (Character c : line) {
                if (c == TerrainElement.MOUNTAIN.getSign()) {
                    actualNumberOfTerrainElements++;
                }
            }
        }
        assertEquals(expected2DArrayLength, area.length);
        assertEquals(expectedNumberOfTerrainElements, actualNumberOfTerrainElements);
    }

    @Test
    void randomShapeGeneratorTest() {
        RandomAreaShaper randomAreaShaper = new RandomAreaShaper();

        Character[][] result = randomAreaShaper.generateAreas(TerrainElement.MOUNTAIN, 1, config, random);

        int expected2DArrayLength = 6;
        int expectedNumberOfTerrainElements = 20;
        int counter = 0;
        for (Character[] line : result) {
            for (Character c : line) {
                if (c == TerrainElement.MOUNTAIN.getSign()) {
                    counter++;
                }
            }
        }


        assertEquals(expected2DArrayLength, result.length);
        assertEquals(expectedNumberOfTerrainElements, counter);
    }

}