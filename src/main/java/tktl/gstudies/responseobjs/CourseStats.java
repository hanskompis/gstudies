package tktl.gstudies.responseobjs;

import java.util.HashMap;

public class CourseStats {

    private String groupIdentifier;
    private int amountStudents;
    private double averageGradeSevenMonths;
    private double averageGradeThirteenMonths;
    private double averageGradeNineteenMonths;
    private double standardDeviationGradesSevenMonths;
    private double standardDeviationGradesThirteenMonths;
    private double standardDeviationGradesNineteenMonths;
    private HashMap<Integer, Integer> creditGainsSevenMonths;
    private HashMap<Integer, Integer> creditGainsThirteenMonths;
    private HashMap<Integer, Integer> creditGainsNineteenMonths;

    public double getStandardDeviationGradesSevenmonths() {
        return standardDeviationGradesSevenMonths;
    }

    public void setStandardDeviationGradesSevenmonths(double standardDeviationGradesSevenmonths) {
        this.standardDeviationGradesSevenMonths = standardDeviationGradesSevenmonths;
    }

    public double getStandardDeviationGradesThirteenmonths() {
        return standardDeviationGradesThirteenMonths;
    }

    public void setStandardDeviationGradesThirteenmonths(double standardDeviationGradesThirteenmonths) {
        this.standardDeviationGradesThirteenMonths = standardDeviationGradesThirteenmonths;
    }

    public double getStandardDeviationGradesNineteenmonths() {
        return standardDeviationGradesNineteenMonths;
    }

    public void setStandardDeviationGradesNineteenmonths(double standardDeviationGradesNineteenmonths) {
        this.standardDeviationGradesNineteenMonths = standardDeviationGradesNineteenmonths;
    }

    public CourseStats(String groupIdentifier) {
        this.groupIdentifier = groupIdentifier;
    }

    public String getGroupIdentifier() {
        return groupIdentifier;
    }

    public void setGroupIdentifier(String groupIdentifier) {
        this.groupIdentifier = groupIdentifier;
    }

    public int getAmountStudents() {
        return amountStudents;
    }

    public void setAmountStudents(int amountStudents) {
        this.amountStudents = amountStudents;
    }

    public double getAverageGradeSevenMonths() {
        return averageGradeSevenMonths;
    }

    public void setAverageGradeSevenMonths(double averageGradeSevenMonths) {
        this.averageGradeSevenMonths = averageGradeSevenMonths;
    }

    public double getAverageGradeThirteenMonths() {
        return averageGradeThirteenMonths;
    }

    public void setAverageGradeThirteenMonths(double averageGradeThirteenMonths) {
        this.averageGradeThirteenMonths = averageGradeThirteenMonths;
    }

    public double getAverageGradeNineteenMonths() {
        return averageGradeNineteenMonths;
    }

    public void setAverageGradeNineteenMonths(double averageGradeNineteenMonths) {
        this.averageGradeNineteenMonths = averageGradeNineteenMonths;
    }

    public HashMap<Integer, Integer> getCreditGainsSevenMonths() {
        return creditGainsSevenMonths;
    }

    public void setCreditGainsSevenMonths(HashMap<Integer, Integer> creditGainsSevenMonths) {
        this.creditGainsSevenMonths = creditGainsSevenMonths;
    }

    public HashMap<Integer, Integer> getCreditGainsThirteenMonths() {
        return creditGainsThirteenMonths;
    }

    public void setCreditGainsThirteenMonths(HashMap<Integer, Integer> creditGainsThirteenMonths) {
        this.creditGainsThirteenMonths = creditGainsThirteenMonths;
    }

    public HashMap<Integer, Integer> getCreditGainsNineteenMonths() {
        return creditGainsNineteenMonths;
    }

    public void setCreditGainsNineteenMonths(HashMap<Integer, Integer> creditGainsNineteenMonths) {
        this.creditGainsNineteenMonths = creditGainsNineteenMonths;
    }

    public void addCreditGainToSevenMonthsCSPassed(double gain) {
        double floored = Math.floor(gain);
        int toAdd = (int) floored;
        if (this.creditGainsSevenMonths == null) {
            this.creditGainsSevenMonths = new HashMap<Integer, Integer>();
        }
        if (this.creditGainsSevenMonths.containsKey(toAdd)) {
            Integer val = this.creditGainsSevenMonths.remove(toAdd);
            this.creditGainsSevenMonths.put(toAdd, ++val);
        } else {
            this.creditGainsSevenMonths.put(toAdd, 1);
        }
    }

    public void addCreditGainToThirteenMonthsCSPassed(double gain) {
        double floored = Math.floor(gain);
        int toAdd = (int) floored;
        if (this.creditGainsThirteenMonths == null) {
            this.creditGainsThirteenMonths = new HashMap<Integer, Integer>();
        }
        if (this.creditGainsThirteenMonths.containsKey(toAdd)) {
            Integer val = this.creditGainsThirteenMonths.remove(toAdd);
            this.creditGainsThirteenMonths.put(toAdd, ++val);
        } else {
            this.creditGainsThirteenMonths.put(toAdd, 1);
        }
    }

    public void addCreditGainToNineteenMonthsCSPassed(double gain) {
        double floored = Math.floor(gain);
        int toAdd = (int) floored;
        if (this.creditGainsNineteenMonths == null) {
            this.creditGainsNineteenMonths = new HashMap<Integer, Integer>();
        }
        if (this.creditGainsNineteenMonths.containsKey(toAdd)) {
            Integer val = this.creditGainsNineteenMonths.remove(toAdd);
            this.creditGainsNineteenMonths.put(toAdd, ++val);
        } else {
            this.creditGainsNineteenMonths.put(toAdd, 1);
        }
    }

    @Override
    public String toString() {
        return "amount of " + this.groupIdentifier + ": " + this.amountStudents + "\n"
                + "distribution of credits 7 mths: " + this.creditGainsSevenMonths.toString() + "\n"
                + "distribution of credits 13 mths: " + this.creditGainsThirteenMonths.toString() + "\n"
                + "distribution of credits 19 mths: " + this.creditGainsNineteenMonths.toString() + "\n"
                + "average grade 7 mths: " + this.averageGradeSevenMonths + "\n"
                + "average grade 13 mths: " + this.averageGradeThirteenMonths + "\n"
                + "average grade 19 mths: " + this.averageGradeNineteenMonths + "\n"
                + "standard dev 7 mths: " + this.standardDeviationGradesSevenMonths + "\n"
                + "standard dev 13 mths: " + this.standardDeviationGradesThirteenMonths + "\n"
                + "standard dev 19 mths: " + this.standardDeviationGradesNineteenMonths + "\n"
                + "************" + "\n";
    }
}
