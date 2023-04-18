package com.codecool.marsexploration.data;

public enum TerrainElement {
    MOUNTAIN('^', true),
    PIT('#', true),
    MINERAL('*', false),
    WATER('~', false);

    private final char sign;
    private final boolean hasArea;

    TerrainElement(char sign, boolean hasArea) {
        this.sign = sign;
        this.hasArea = hasArea;
    }
    public char getSign() {
        return sign;
    }

    public boolean hasArea() {
        return hasArea;
    }
}
