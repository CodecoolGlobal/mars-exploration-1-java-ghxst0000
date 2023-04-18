package com.codecool.marsexploration;

import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;
import com.codecool.marsexploration.logic.MapGenerator;

import java.util.Map;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        Random random = new Random();


        String path="src/main/resources/test.map";
        int width=25;
        int height=25;
        Map<TerrainElement,Integer[]> areas= Map.of(
                TerrainElement.WATER,new Integer[]{4,5,6},
                TerrainElement.PIT,new Integer[]{7,8,9},
                TerrainElement.MINERAL,new Integer[]{5,8,10},
                TerrainElement.MOUNTAIN,new Integer[]{10,20,30}
                
        );

        MapConfiguration map=new MapConfiguration(width,height,path,areas);

        MapGenerator generator=new MapGenerator(map,random);
        
        generator.generate();


    }
}
