package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;

import java.util.*;

public class MapGenerator {
    private MapConfiguration config;
    private Random random;

    public MapGenerator(MapConfiguration config, Random random) {
        this.config = config;
        this.random = random;
    }

    public Character[][] generate() {
        Character[][] terrain = new Character[config.height()][config.width()];
        for (Character[] row : terrain) {
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

        return terrain;
    }

    private Character[][] generateAreas(TerrainElement symbol, Integer index) {
        int numberOfSymbols = config.areas().get(symbol)[index];
        Integer size = (int) Math.ceil(Math.sqrt(numberOfSymbols));
        Character[][] area = new Character[size][size];
        for(Character[] row : area) {
            Arrays.fill(row,' ');
        }

        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                counter++;
                if(counter == numberOfSymbols) {
                    printArea(area);
                    return area;
                }
                area[i][j] = symbol.getSign();
            }
        }

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
