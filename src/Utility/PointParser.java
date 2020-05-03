package Utility;

import Models.Iris;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PointParser {

    public static ArrayList<Iris> getPointsFromFile() throws IOException  {
        ArrayList<Iris> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(new File("src/Data/iris_training.txt")));
        String line;
        while ((line = br.readLine()) != null) {
            result.add(new Iris(line));
        }
        return result;
    }


}
