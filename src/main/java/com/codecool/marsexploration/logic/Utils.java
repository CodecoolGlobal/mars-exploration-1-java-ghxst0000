package com.codecool.marsexploration.logic;

public class Utils {
    public String convert2DArrayToString(Character[][] map) {
        StringBuilder sb = new StringBuilder();
        for (Character[] row : map) {
            for (Character c : row) {
                sb.append(c);
            }
            sb.append('\n');
        }
        sb.deleteCharAt(sb.toString().length() - 1);
        return sb.toString();
    }
}
