package com.codecool.marsexploration.logic.generating_strategy;

import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;

import java.util.*;

public class RandomAreaShaper extends GeneratingStrategy {
    @Override
    public Character[][] generateAreas(TerrainElement symbol, Integer index, MapConfiguration config, Random random) {
        int numberOfSymbols = config.areas().get(symbol)[index];
        Integer size = (int) Math.ceil(Math.sqrt(numberOfSymbols)) + 1;
        Character[][] area = new Character[size][size];
        for (Character[] row : area) {
            Arrays.fill(row, symbol.getSign());
        }


        Set<Coordinate> randomChoices = new HashSet<Coordinate>();
        while (randomChoices.size() < Math.pow(size, 2) - numberOfSymbols) {
            randomChoices.add(new Coordinate(random.nextInt(size), random.nextInt(size)));
        }

        for (Coordinate coord : randomChoices) {
            area[coord.x()][coord.y()] = ' ';
        }

        return area;
    }
}
