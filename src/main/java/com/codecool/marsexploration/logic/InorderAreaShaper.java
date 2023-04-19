package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;

import java.util.Arrays;
import java.util.Random;

public class InorderAreaShaper extends GeneratingStrategy{
    @Override
    public Character[][] generateAreas(TerrainElement symbol, Integer index, MapConfiguration config, Random random) {
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
                    return area;
                }
                area[i][j] = symbol.getSign();
            }
        }

        return area;
    }
}
