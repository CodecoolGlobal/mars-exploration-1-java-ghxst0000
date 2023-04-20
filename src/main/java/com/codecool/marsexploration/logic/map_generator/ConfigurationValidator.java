package com.codecool.marsexploration.logic.map_generator;

import com.codecool.marsexploration.data.Coordinate;

class ConfigurationValidator {

    public boolean isAreaPlacable(Character[][] map, Character[][] area, Coordinate origin) {
        for (int i = origin.x(); i < origin.x() + area.length; i++) {
            for (int j = origin.y(); j < origin.y() + area.length; j++) {
                if (map[i][j] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
