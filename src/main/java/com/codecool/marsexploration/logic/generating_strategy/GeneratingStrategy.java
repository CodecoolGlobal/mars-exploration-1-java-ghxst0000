package com.codecool.marsexploration.logic.generating_strategy;

import com.codecool.marsexploration.data.MapConfiguration;
import com.codecool.marsexploration.data.TerrainElement;

import java.util.Random;

public abstract class GeneratingStrategy {
    public abstract Character[][] generateAreas(TerrainElement symbol, Integer index, MapConfiguration config, Random random);
}
