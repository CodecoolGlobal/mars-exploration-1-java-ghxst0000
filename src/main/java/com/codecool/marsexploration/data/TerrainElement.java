package com.codecool.marsexploration.data;

public enum TerrainElement {
    MOUNTAIN('^', true, null),
    PIT('#', true, null),
    MINERAL('*', false, '^'),
    WATER('~', false, '#');

    private final Character sign;
    private final boolean hasArea;
    private final Character neighbour;

    TerrainElement(Character sign, boolean hasArea, Character neighbour) {
        this.sign = sign;
        this.hasArea = hasArea;
        this.neighbour = neighbour;
    }
    public Character getSign() {
        return sign;
    }

    public Character getNeighbour() {
        return neighbour;
    }

    public boolean hasArea() {
        return hasArea;
    }
}
