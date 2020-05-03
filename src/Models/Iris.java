package Models;

import java.util.ArrayList;

public class Iris {

    private ArrayList<Double> numAttributes;
    private String subtype;
    private ArrayList<Iris> centroidAssignments;

    public Iris(double a1, double a2, double a3, double a4) {
        this.numAttributes = new ArrayList<>();
        this.numAttributes.add(a1);
        this.numAttributes.add(a2);
        this.numAttributes.add(a3);
        this.numAttributes.add(a4);
        this.centroidAssignments = new ArrayList<Iris>();
    }

    public Iris(String line) {
        this.numAttributes = new ArrayList<>();
        line = line.replace(',', '.');
        String tokens[] = line.split("\t");

        for (int i = 0; i < tokens.length-1; i++) {
            this.numAttributes.add(Double.parseDouble(tokens[i]));
        }
        this.subtype = tokens[tokens.length-1].trim();
        this.centroidAssignments = new ArrayList<Iris>();
    }

    public Double calculateDistanceTo(Iris other) {
        Double result = 0.0;
        for (int i = 0; i < this.numAttributes.size(); i++) {
            result += Math.pow(this.numAttributes.get(i)-other.numAttributes.get(i), 2);
        }
        result = Math.sqrt(result);
        return result;
    }

    public String getSubtype() {
        return subtype;
    }

    public ArrayList<Double> getNumAttributes() {
        return numAttributes;
    }

    public ArrayList<Iris> getCentroidAssignments() {
        return centroidAssignments;
    }

    public String toString() {
        return "["+this.numAttributes.get(0)+", "+this.numAttributes.get(1)+", "+this.numAttributes.get(2)+", "+this.numAttributes.get(3)+"]";
    }

    public void setNumAttributes(ArrayList<Double> numAttributes) {
        this.numAttributes = numAttributes;
    }
}
