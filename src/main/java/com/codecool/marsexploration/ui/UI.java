package com.codecool.marsexploration.ui;

import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;
import com.codecool.marsexploration.logic.generating_strategy.GeneratingStrategy;
import com.codecool.marsexploration.logic.generating_strategy.InorderAreaShaper;
import com.codecool.marsexploration.logic.generating_strategy.RandomAreaShaper;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class UI {
    private   <T> T choice(String[] choiceText, T[] choices, String  prefix) {
        int choice = -1;

        for (int i = 0; i < choiceText.length; i++) {
            System.out.println(i + " - " + choiceText[i]);
        }

        while(choice < 0  || choice > choices.length) {
            choice = getInputInt(prefix);
        }

        return choices[choice];
    }

    private   String getInputString(String prefix) {
        System.out.print(prefix);
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    private   int getInputInt(String prefix) {
        System.out.print(prefix);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void printArea(Character[][] area) {
        for (Character[] row : area) {
            for (Character c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println("\n\n");
    }

    public MapConfiguration getParametersForMapConfiguration(Random random) {
        String path = getInputString("Specify an output name: ");

        GeneratingStrategy strategy = choice(
                new String[]{ "Random Area Shaper", "In-order Area Shaper" },
                new GeneratingStrategy[]{ new RandomAreaShaper(), new InorderAreaShaper() },
                "Chose a number: "
        );

        int width = getInputInt("Map width: ");
        int height = getInputInt("Map height: ");

        int waterAmount = getInputInt("Amount of water: ");
        int mineralAmount = getInputInt("Amount of mineral: ");

        int pitCount = getInputInt("Number of pits");
        int minPitSize = getInputInt("Minimum pit size: ");
        int maxPitSize = getInputInt("Maximum pit size: ");

        int pitArray[] = new int[pitCount];

        for (int i = 0; i < pitArray.length; i++) {
            pitArray[i] = random.nextInt(minPitSize, maxPitSize);
        }

        int mountainCount = getInputInt("Number of mountains");
        int minMountainSize = getInputInt("Minimum mountain size: ");
        int maxMountainSize = getInputInt("Minimum mountain size: ");

        int mountainArray[] = new int[mountainCount];

        for (int i = 0; i < mountainArray.length; i++) {
            mountainArray[i] = random.nextInt(minMountainSize, maxMountainSize);
        }

        Map<TerrainElement, int[]> areas= Map.of(
                TerrainElement.WATER,     new int[] { waterAmount },
                TerrainElement.MINERAL,   new int[] { mineralAmount },
                TerrainElement.PIT,       pitArray,
                TerrainElement.MOUNTAIN,  mountainArray
        );
        return new MapConfiguration(width, height, path, areas);
    }
}
