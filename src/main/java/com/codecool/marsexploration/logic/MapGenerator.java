package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;

import java.util.*;

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

        for (Map.Entry<TerrainElement, Integer[]> element : config.areas().entrySet()) {
            if (element.getKey().hasArea()) {
                for (int i = 0; i < element.getValue().length; i++) {
                    areas.add(generateAreas(element.getKey(), i));
                }
            }
        }

        System.out.println(areas.size());
        //hasArea -> generateAreas()
        //placeAreas()
        //!hasArea -> placeResources()

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

    private void placeAreas(Character[][] terrain, List<Character[][]> areas){
        
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
}
