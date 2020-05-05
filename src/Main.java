import Models.Iris;
import Utility.IrisParser;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Random;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<Iris> irises = IrisParser.getPointsFromFile();
        ArrayList<Iris> centroids = new ArrayList<>();
        int k;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter k:");
        k = input.nextInt();


        System.out.println("==========INITIAL ASSIGNMENT================");
        intializeCentroids(irises, centroids, k);
        assignIrisToCentroids(irises, centroids);
        printInfo(centroids);
        System.out.println("============================================");

        while (updateCentroidsCoordinates(centroids)) {
            clearAssignments(centroids);
            assignIrisToCentroids(irises, centroids);
            printInfo(centroids);
            System.out.println("============================================");
        }

        System.out.println("===================RESULT===================");
        clearAssignments(centroids);
        assignIrisToCentroids(irises, centroids);
        printInfo(centroids);

    }

    public static void intializeCentroids(ArrayList<Iris> irises, ArrayList<Iris> centroids, int k) {
        Random r = new Random();
        for (int i = 0; i < k; i++) {
            Iris random = irises.get(r.nextInt(irises.size()));
            Iris centroid = new Iris(random.getNumAttributes().get(0), random.getNumAttributes().get(1), random.getNumAttributes().get(2), random.getNumAttributes().get(3));
            centroids.add(centroid);
        }

    }

    public static void assignIrisToCentroids(ArrayList<Iris> irises, ArrayList<Iris> centroids) {
        for (Iris iris : irises) {
            double minDistance = iris.calculateDistanceTo(centroids.get(0));
            Iris closestCentroid = centroids.get(0);
            for (Iris centroid : centroids) {
                if (iris.calculateDistanceTo(centroid) < minDistance) {
                    minDistance = iris.calculateDistanceTo(centroid);
                    closestCentroid = centroid;
                }
            }
            closestCentroid.getCentroidAssignments().add(iris);
        }
    }

    public static boolean updateCentroidsCoordinates(ArrayList<Iris> centroids) {
        int numOfReps = 0;
        for (Iris centroid : centroids) {
            double mean1 = 0;
            double mean2 = 0;
            double mean3 = 0;
            double mean4 = 0;
            for (Iris i : centroid.getCentroidAssignments()) {
                mean1 += i.getNumAttributes().get(0);
                mean2 += i.getNumAttributes().get(1);
                mean3 += i.getNumAttributes().get(2);
                mean4 += i.getNumAttributes().get(3);
            }
            mean1 = mean1 / centroid.getCentroidAssignments().size();
            mean2 = mean2 / centroid.getCentroidAssignments().size();
            mean3 = mean3 / centroid.getCentroidAssignments().size();
            mean4 = mean4 / centroid.getCentroidAssignments().size();
            if (centroid.getNumAttributes().get(0) == mean1 && centroid.getNumAttributes().get(1) == mean2 &&
                    centroid.getNumAttributes().get(2) == mean3 && centroid.getNumAttributes().get(3) == mean4) {
                numOfReps++;
            }
            if (numOfReps == centroids.size()) {
                return false;
            }
            ArrayList<Double> newNumAttributes = new ArrayList<>();
            newNumAttributes.add(mean1);
            newNumAttributes.add(mean2);
            newNumAttributes.add(mean3);
            newNumAttributes.add(mean4);
            centroid.setNumAttributes(newNumAttributes);
        }
        return true;
    }

    public static void clearAssignments(ArrayList<Iris> centroids) {
        for (Iris centroid : centroids) {
            centroid.getCentroidAssignments().clear();
        }
    }

    public static void printInfo(ArrayList<Iris> centroids) {
        for (Iris centroid : centroids) {
            System.out.println("Centroid: "+centroid.getNumAttributes()+"\t" + centroid.getCentroidAssignments().size());
            System.out.println(centroid.getCentroidAssignments());
            for (Iris i : centroid.getCentroidAssignments()) {
                System.out.print(i.getSubtype()+", ");
            }
            System.out.println();
            System.out.println("Entropy: "+calculateEntropy(centroid));
            System.out.println();
        }
    }

    public static double calculateEntropy(Iris centroid) {
        if (centroid.getCentroidAssignments().size() > 0) {
            int size = centroid.getCentroidAssignments().size();
            double entropy = 0;
            double setosaCount = 0;
            double virginicaCount = 0;
            double versicolorCount = 0;
            for (Iris iris : centroid.getCentroidAssignments()) {
                if (iris.getSubtype().equals("Iris-setosa")) setosaCount++;
                if (iris.getSubtype().equals("Iris-versicolor")) versicolorCount++;
                if (iris.getSubtype().equals("Iris-virginica")) virginicaCount++;
            }
            if (setosaCount > 0) entropy += ((setosaCount / size) * IrisParser.log2(setosaCount / size));
            if (versicolorCount > 0) entropy += ((versicolorCount / size) * IrisParser.log2(versicolorCount / size));
            if (virginicaCount > 0) entropy += ((virginicaCount / size) * IrisParser.log2(virginicaCount / size));

            return entropy*(-1);
        }
        return 0;
    }

    public static void distanceFromCentroid(ArrayList<Iris> centroids) {
        for (Iris centroid : centroids) {
            int sumOfDistances = 0;
            System.out.println("Centroid: "+centroid.getNumAttributes());
            for (Iris iris : centroid.getCentroidAssignments()) {
                sumOfDistances += iris.calculateDistanceTo(centroid);
            }
            System.out.println(sumOfDistances);
        }
    }

}
