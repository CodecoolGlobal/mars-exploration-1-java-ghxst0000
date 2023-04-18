package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class MapGenerator {
    private MapConfiguration config;
    private Random random;
    private Character[][] map;

    public MapGenerator(MapConfiguration config, Random random) {
        this.config = config;
        this.random = random;
        this.map = new Character[config.height()][config.width()];

    }

    public Character[][] generate() {
        for (Character[] row : map) {
            Arrays.fill(row, ' ');
        }

        List<Character[][]> areas = new ArrayList<>();

        for (Map.Entry<TerrainElement, int[]> element : config.areas().entrySet()) {
            if (element.getKey().hasArea()) {
                for (int i = 0; i < element.getValue().length; i++) {
                    areas.add(generateAreas(element.getKey(), i));
                }
            }
        }

        //hasArea -> generateAreas()
        for (Map.Entry<TerrainElement, int[]> element : config.areas().entrySet()) {
            if (!element.getKey().hasArea()) {
                for (int i = 0; i < element.getValue().length; i++) {
                    placeResources(element.getKey(), IntStream.of(element.getValue()).sum());
                }
            }
        }

        areas.forEach(area->placeArea(area));
        //placeAreas()
        //!hasArea -> placeResources()
        printArea(map);
        writeFile(map);
        return map;
    }

    private Character[][] generateAreas(TerrainElement symbol, Integer index) {
        int numberOfSymbols = config.areas().get(symbol)[index];
        Integer size = (int) Math.ceil(Math.sqrt(numberOfSymbols)) + 1;
        Character[][] area = new Character[size][size];
        for(Character[] row : area) {
            Arrays.fill(row, symbol.getSign());
        }


        Set<Coordinate> randomChoices = new HashSet<Coordinate>();
        while (randomChoices.size() < Math.pow(size, 2) - numberOfSymbols) {
            randomChoices.add(new Coordinate(random.nextInt(size), random.nextInt(size)));
        }

        for (Coordinate coord : randomChoices) {
            area[coord.x()][coord.y()] = ' ';
        }

        printArea(area);
        return area;
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
                placeable = validatePosition(area, origin);
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
            System.out.println("cant fit area");
        }
    }

    private void placeResources(TerrainElement resource, int numberOfResourcePoints){
        Set<Coordinate> coordinatesNextToNeighbouringElement = new HashSet<>();
        for(int i = 0; i < config.height() - 1; i++){
            for(int j = 0; i < config.width() -1; j++){
                if ((i - 1 > 0)) { // left
                    if (map[i - 1][j] == resource.getNeighbour()) {
                        coordinatesNextToNeighbouringElement.add(new Coordinate(i, j));
                    }
                } else if(i + 1 < config.height()) {// right
                    System.out.println(i + " " + config.width());
                    if (map[i + 1][j] == resource.getNeighbour()) {
                        coordinatesNextToNeighbouringElement.add(new Coordinate(i, j));
                    }
                } else if(j + 1 < config.width()) {
                    if(map[i][j + 1] == resource.getNeighbour()) { // up
                        coordinatesNextToNeighbouringElement.add(new Coordinate(i, j));
                    }
                } else if((j - 1 > 0 )) {
                    if(map[i][j - 1] == resource.getNeighbour()){// down
                    coordinatesNextToNeighbouringElement.add(new Coordinate(i, j));
                }}
            }
        }
        List<Coordinate> list=new ArrayList<>(coordinatesNextToNeighbouringElement);
        try {
            for(int i = 0; i < numberOfResourcePoints; i++){
                Integer randomIndex = random.nextInt(list.size());
                Coordinate randomCoordinate = list.get(randomIndex);
                map[randomCoordinate.x()][randomCoordinate.y()] = resource.getSign();
                list.remove(randomIndex);
            }
        } catch (Exception e){
            System.out.println("can't place all resources");
        }
    }

    private boolean validatePosition(Character[][] area, Coordinate origin) {
        for (int i = origin.x(); i <  origin.x() + area.length; i++) {
            for (int j = origin.y(); j <  origin.y() + area.length; j++) {
                if (map[i][j] != ' ') {
                    return false;
                }
            }
        }
        return true;
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
            writer.write(convert2DArrayToString(map));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String convert2DArrayToString(Character[][] map){
        StringBuilder sb = new StringBuilder();
        for (Character[] row: map) {
            for (Character c : row) {
                sb.append(c);
            }
            sb.append('\n');
        }
        sb.deleteCharAt(sb.toString().length() - 1);
        return sb.toString();
    }
}
