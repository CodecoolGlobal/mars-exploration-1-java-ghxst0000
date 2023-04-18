package com.codecool.marsexploration.data;

import java.util.Map;

public record MapConfiguration (
    Integer width,
    Integer height,
    String fileName,
    Map<TerrainElement, int[]> areas
) {}