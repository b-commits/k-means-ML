import Models.Iris;
import Utility.PointParser;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Random r = new Random();
        ArrayList<Iris> irises = PointParser.getPointsFromFile();
        ArrayList<Iris> centroids = new ArrayList<>();

        int k;
        Scanner input = new Scanner(System.in);
        k = input.nextInt();

        // INITIALIZE CENTROIDS
        for (int i = 0; i < k; i++) {
            Iris random = irises.get(r.nextInt(irises.size()));
            Iris centroid = new Iris(random.getNumAttributes().get(0), random.getNumAttributes().get(1), random.getNumAttributes().get(2), random.getNumAttributes().get(3));
            centroids.add(centroid);
        }

        // ASSIGN TO CENTROIDS
        assignIris(irises, centroids);

        for (Iris centroid : centroids) {
            System.out.println(centroid.getNumAttributes()+"\t" + centroid.getCentroidAssignments().size());
            System.out.println(centroid.getCentroidAssignments());
        }

        for (int i = 0; i < 5; i++) {
            updateCentroids(centroids);
            assignIris(irises, centroids);
            System.out.println();
        }


        for (Iris centroid : centroids) {
            System.out.println(centroid.getNumAttributes()+"\t" + centroid.getCentroidAssignments().size());
            System.out.println(centroid.getCentroidAssignments());
        }


    }


    public static void assignIris(ArrayList<Iris> irises, ArrayList<Iris> centroids) {
        for (Iris iris : irises) {
            double minDistance = iris.calculateDistanceTo(centroids.get(0));
            Iris closestCentroid = centroids.get(0);
            for (Iris centroid : centroids) {
                if (iris.calculateDistanceTo(centroid) < minDistance) {
                    closestCentroid = centroid;
                }
            }
            if (!centroids.contains(iris)) {
                closestCentroid.getCentroidAssignments().add(iris);
            }
        }
    }

    public static void updateCentroids(ArrayList<Iris> centroids) {
        for (Iris centroid : centroids) {
            double mean1 = 0, mean2 = 0, mean3 = 0, mean4 = 0;
            for (Iris iris : centroid.getCentroidAssignments()) {
                mean1 += iris.getNumAttributes().get(0);
                mean2 += iris.getNumAttributes().get(1);
                mean3 += iris.getNumAttributes().get(2);
                mean4 += iris.getNumAttributes().get(3);
            }
            mean1 /= centroid.getCentroidAssignments().size();
            mean2 /= centroid.getCentroidAssignments().size();
            mean3 /= centroid.getCentroidAssignments().size();
            mean4 /= centroid.getCentroidAssignments().size();
            ArrayList<Double> newCooridinates = new ArrayList<>();
            newCooridinates.add(mean1);
            newCooridinates.add(mean2);
            newCooridinates.add(mean3);
            newCooridinates.add(mean4);
            centroid.setNumAttributes(newCooridinates);
            centroid.getCentroidAssignments().clear();
        }
    }



}
