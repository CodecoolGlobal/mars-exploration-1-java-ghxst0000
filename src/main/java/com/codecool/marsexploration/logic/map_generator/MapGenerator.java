package com.codecool.marsexploration.logic.map_generator;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;
import com.codecool.marsexploration.logic.Utils;
import com.codecool.marsexploration.logic.generating_strategy.GeneratingStrategy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class MapGenerator {
    private MapConfiguration config;
    private Random random;
    private Character[][] map;
    private GeneratingStrategy generatingStrategy;
    private Utils utils;
    private ConfigurationValidator validator;

    public MapGenerator(MapConfiguration config, Random random, GeneratingStrategy generatingStrategy) {
        this.config = config;
        this.random = random;
        this.map = new Character[config.height()][config.width()];
        this.generatingStrategy = generatingStrategy;
        this.utils = new Utils();
        this.validator = new ConfigurationValidator();

    }

    public Character[][] generate() {
        for (Character[] row : map) {
            Arrays.fill(row, ' ');
        }

        //hasArea -> generateAreas()
        List<Character[][]> areas = new ArrayList<>();

        for (Map.Entry<TerrainElement, int[]> element : config.areas().entrySet()) {
            if (element.getKey().hasArea()) {
                for (int i = 0; i < element.getValue().length; i++) {
                    areas.add(generatingStrategy.generateAreas(element.getKey(), i, config, random));
                }
            }
        }

        //placeAreas()
        areas.forEach(area -> placeArea(area));

        //!hasArea -> placeResources()
        for (Map.Entry<TerrainElement, int[]> element : config.areas().entrySet()) {
            if (!element.getKey().hasArea()) {
                for (int i = 0; i < element.getValue().length; i++) {
                    placeResources(element.getKey(), IntStream.of(element.getValue()).sum());
                }
            }
        }

        writeFile(map);
        return map;
    }


    private void placeArea(Character[][] area) {
        boolean placeable = false;
        Integer attempts = 0;
        try {
            while (!placeable && attempts < (config.width() - area.length) * (config.height() - area.length)) {
                Coordinate origin = new Coordinate(
                        random.nextInt(config.height() - area.length),
                        random.nextInt(config.width() - area.length)
                );
                placeable = validator.isAreaPlacable(map, area, origin);
                if (placeable) {
                    for (int i = 0; i < area.length; i++) {
                        for (int j = 0; j < area.length; j++) {
                            map[i + origin.x()][j + origin.y()] = area[i][j];
                        }
                    }
                }
                attempts++;
            }
        } catch (Exception e) {
            System.out.println("unable to place area");
        }
        if (attempts >= (config.width() - area.length) * (config.height() - area.length)) {
            System.out.println("can't fit area");
        }
    }

    private void placeResources(TerrainElement resource, int numberOfResourcePoints) {
        Set<Coordinate> coordinatesNextToNeighbouringElement = new HashSet<Coordinate>();
        for (int i = 0; i < config.height(); i++) {
            for (int j = 0; j < config.width(); j++) {
                if (map[i][j] == ' ') {
                    if ((i - 1 >= 0)) { // left
                        if (map[i - 1][j] == resource.getNeighbour()) {
                            coordinatesNextToNeighbouringElement.add(new Coordinate(i, j));
                        }
                    }
                    if (i + 1 < config.width()) { // right
                        if (map[i + 1][j] == resource.getNeighbour()) {
                            coordinatesNextToNeighbouringElement.add(new Coordinate(i, j));
                        }
                    }
                    if (j + 1 < config.height()) { // up
                        if (map[i][j + 1] == resource.getNeighbour()) {
                            coordinatesNextToNeighbouringElement.add(new Coordinate(i, j));
                        }
                    }
                    if ((j - 1 >= 0)) { // down
                        if (map[i][j - 1] == resource.getNeighbour()) {
                            coordinatesNextToNeighbouringElement.add(new Coordinate(i, j));
                        }
                    }
                }
            }
        }
        List<Coordinate> list = new ArrayList<>(coordinatesNextToNeighbouringElement);
        try {
            for (int i = 0; i < numberOfResourcePoints; i++) {
                Integer randomIndex = random.nextInt(list.size());
                Coordinate randomCoordinate = list.get(randomIndex);
                map[randomCoordinate.x()][randomCoordinate.y()] = resource.getSign();
                list.remove(randomCoordinate);
            }
        } catch (Exception e) {
            System.out.println("can't place all resources");
        }
    }

    private void printArea(Character[][] area) {
        for (Character[] row : area) {
            for (Character c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println("\n\n");
    }


    private void writeFile(Character[][] map){
        BufferedWriter writer= null;
        try {
            writer = new BufferedWriter(new FileWriter("src/main/resources/" + config.fileName()));
            writer.write(utils.convert2DArrayToString(map));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setConfig(MapConfiguration config) {
        this.config = config;
    }

    public void setGeneratingStrategy(GeneratingStrategy generatingStrategy) {
        this.generatingStrategy = generatingStrategy;
    }
}
