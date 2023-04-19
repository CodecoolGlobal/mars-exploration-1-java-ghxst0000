package com.codecool.marsexploration;

import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.logic.MapGenerator;
import com.codecool.marsexploration.logic.RandomAreaShaper;
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
