package com.codecool.marsexploration;

import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;
import com.codecool.marsexploration.logic.generating_strategy.InorderAreaShaper;
import com.codecool.marsexploration.logic.map_generator.MapGenerator;
import com.codecool.marsexploration.logic.generating_strategy.RandomAreaShaper;

import java.util.Map;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        Random random = new Random();

        String path1="test1.map";
        String path2="test2.map";

        int width=25;
        int height=25;

        Map<TerrainElement, int[]> areas= Map.of(
                TerrainElement.WATER,new int []{4,5,60},
                TerrainElement.PIT,new int[]{7,8,9},
                TerrainElement.MINERAL,new int[]{5,8,10},
                TerrainElement.MOUNTAIN, new int[]{10,20,30}
                
        );

        MapConfiguration map1Configuration = new MapConfiguration(width,height,path1,areas);
        MapConfiguration map2Configuration = new MapConfiguration(width,height,path2,areas);


        MapGenerator generator = new MapGenerator(map1Configuration,random, new RandomAreaShaper());
        System.out.println("Map 1");
        generator.generate();

        System.out.println("-".repeat(40));
        System.out.println("Map 2");
        generator.setConfig(map2Configuration);
        generator.setGeneratingStrategy(new InorderAreaShaper());
        generator.generate();
    }
}
