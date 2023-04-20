package com.codecool.marsexploration.logic.filewriter;

import com.codecool.marsexploration.data.MapConfiguration;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriter {


    public void writeFile(Character[][] map,MapConfiguration config) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new java.io.FileWriter("src/main/resources/" + config.fileName()));
            writer.write(convert2DArrayToString(map));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String convert2DArrayToString(Character[][] map) {
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
