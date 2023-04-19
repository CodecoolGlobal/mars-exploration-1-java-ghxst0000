package com.codecool.marsexploration;

import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.logic.map_generator.MapGenerator;
import com.codecool.marsexploration.logic.generating_strategy.RandomAreaShaper;
import com.codecool.marsexploration.ui.UI;

import java.util.Random;

public class Application {
    public static void main(String[] args) {
        Random random = new Random();

        UI ui = new UI();
        MapConfiguration config = ui.getParametersForMapConfiguration(random);
        MapGenerator generator = new MapGenerator(config, random, new RandomAreaShaper());
        Character[][] map = generator.generate();
        ui.printArea(map);
    }
}
