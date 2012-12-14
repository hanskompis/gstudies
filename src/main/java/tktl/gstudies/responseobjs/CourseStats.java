package tktl.gstudies.responseobjs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * A class to store information on course depending whether students who failed
 * or passed are of interest.
 *
 * @author hkeijone
 */
public class CourseStats {

    private String groupIdentifier;
    private int amountStudents;
    private double averageGradeSevenMonths;
    private double averageGradeThirteenMonths;
    private double averageGradeNineteenMonths;
    private double standardDeviationGradesSevenMonths;
    private double standardDeviationGradesThirteenMonths;
    private double standardDeviationGradesNineteenMonths;
    private int amountCreditsSevenMonths;
    private int amountCreditsThirteenMonths;
    private int amountCreditsNineteenMonths;
    private double averageCreditsSevenMonths;
    private double averageCreditsThirteenMonths;
    private double averageCreditsNineteenMonths;
    private HashMap<Integer, Integer> creditGainsSevenMonths;
    private HashMap<Integer, Integer> creditGainsThirteenMonths;
    private HashMap<Integer, Integer> creditGainsNineteenMonths;
    private int[][] creditGainsSevenMonthsArr;
    private int[][] creditGainsThirteenMonthsArr;
    private int[][] creditGainsNineteenMonthsArr;

    public void addCreditGainToSevenMonthsCSPassed(double gain) {
        double floored = Math.floor(gain);
        int toAdd = (int) floored;
        this.amountCreditsSevenMonths += toAdd;
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
        this.amountCreditsThirteenMonths += toAdd;

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
        this.amountCreditsNineteenMonths += toAdd;
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

//    private int[][] convertHashMapIntoArrays(HashMap toConvert) {
//
//        int arrSize = toConvert.size();
//        int[][] arr = new int[arrSize][];
//        int ind = 0;
//        Iterator i = toConvert.entrySet().iterator();
//        while (i.hasNext()) {
//            int[] toAdd = new int[2];
//            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) i.next();
//            toAdd[0] = entry.getKey();
//            toAdd[1] = entry.getValue();
//            arr[ind] = toAdd;
//            ind++;
//        }
//        return arr;
//    }
    private int findLargestKey(HashMap<Integer, Integer> hmap) {
        int largest = 0;
        for (Map.Entry<Integer, Integer> entry : hmap.entrySet()) {
            Integer key = entry.getKey();
            if (key > largest) {
                largest = key;
            }
        }
        return largest;
    }

    private int findLargestValue(HashMap<Integer, Integer> hmap) {
        int largest = 0;
        for (Map.Entry<Integer, Integer> entry : hmap.entrySet()) {
            Integer value = entry.getValue();
            if (value > largest) {
                largest = value;
            }
        }
        return largest;
    }

    private int[][] convertHashMapIntoArrays(HashMap toConvert) {
        int arrSize = toConvert.size();

        int[][] arr = new int[arrSize][2];
//        for(int i = 0; i < arrSize; i++){
//            for(int j = 0; j < arr[i].length; j++){
//                arr[i][j] = 0;
//            }
//        }
        int ind = 0;
        int currentNum = 0;
        while (ind <= arrSize - 1) {
            if (toConvert.containsKey(currentNum)) {
                int[] toAdd = new int[2];
                toAdd[0] = currentNum;
                toAdd[1] = (Integer) toConvert.remove(currentNum);
                arr[ind] = toAdd;
                ind++;
            }

            currentNum++;
        }
        return arr;
    }

    public void calculateCreditAverages() {
        this.averageCreditsSevenMonths = 1.0 * this.amountCreditsSevenMonths / this.amountStudents;
        this.averageCreditsThirteenMonths = 1.0 * this.amountCreditsThirteenMonths / this.amountStudents;
        this.averageCreditsNineteenMonths = 1.0 * this.amountCreditsNineteenMonths / this.amountStudents;
    }

    public void convertAllHashMaps() {
        this.creditGainsSevenMonthsArr = this.convertHashMapIntoArrays(creditGainsSevenMonths);
        this.creditGainsThirteenMonthsArr = this.convertHashMapIntoArrays(creditGainsThirteenMonths);
        this.creditGainsNineteenMonthsArr = this.convertHashMapIntoArrays(creditGainsNineteenMonths);
    }

    private HashMap<Integer, Integer> getCategorizedMap(HashMap<Integer, Integer> hm) {
        Iterator i = hm.entrySet().iterator();
        HashMap<Integer, Integer> toReturn = new HashMap<Integer, Integer>();
        while (i.hasNext()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) i.next();
            int key = entry.getKey().intValue();
            key = (key / 10) * 10;
            if (toReturn.containsKey(key)) {
                int value = toReturn.remove(key);
                value = value + entry.getValue();
                toReturn.put(key, value);
            } else {
                toReturn.put(key, entry.getValue());
            }
        }
        return toReturn;
    }

    @Override
    public String toString() {
        if (this.amountStudents != 0) {
            return "amount of " + this.groupIdentifier + ": " + this.amountStudents + "\n"
                    + "distribution of credits 7 mths ave:" + this.averageCreditsSevenMonths + " distr" + this.creditGainsSevenMonths.toString() + "\n"
                    + "categorized: " + this.getCategorizedMap(creditGainsSevenMonths).toString() + "\n"
                    + "distribution of credits 13 mths ave: " + this.averageCreditsThirteenMonths + " distr" + this.creditGainsThirteenMonths.toString() + "\n"
                    + "categorized: " + this.getCategorizedMap(creditGainsThirteenMonths).toString() + "\n"
                    + "distribution of credits 19 mths ave: " + this.averageCreditsNineteenMonths + " distr" + this.creditGainsNineteenMonths.toString() + "\n"
                    + "categorized: " + this.getCategorizedMap(creditGainsNineteenMonths).toString() + "\n"
                    + "average grade 7 mths: " + this.averageGradeSevenMonths + "\n"
                    + "average grade 13 mths: " + this.averageGradeThirteenMonths + "\n"
                    + "average grade 19 mths: " + this.averageGradeNineteenMonths + "\n"
                    + "standard dev 7 mths: " + this.standardDeviationGradesSevenMonths + "\n"
                    + "standard dev 13 mths: " + this.standardDeviationGradesThirteenMonths + "\n"
                    + "standard dev 19 mths: " + this.standardDeviationGradesNineteenMonths + "\n"
                    + "************" + "\n";
        } else {
            return "pröööttt";
        }
    }

    public double getAverageCreditsSevenMonths() {
        return averageCreditsSevenMonths;
    }

    public void setAverageCreditsSevenMonths(double averageCreditsSevenMonths) {
        this.averageCreditsSevenMonths = averageCreditsSevenMonths;
    }

    public double getAverageCreditsThirteenMonths() {
        return averageCreditsThirteenMonths;
    }

    public void setAverageCreditsThirteenMonths(double averageCreditsThirteenMonths) {
        this.averageCreditsThirteenMonths = averageCreditsThirteenMonths;
    }

    public double getAverageCreditsNineteenMonths() {
        return averageCreditsNineteenMonths;
    }

    public void setAverageCreditsNineteenMonths(double averageCreditsNineteenMonths) {
        this.averageCreditsNineteenMonths = averageCreditsNineteenMonths;
    }

    public int getAmountCreditsSevenMonths() {
        return amountCreditsSevenMonths;
    }

    public void setAmountCreditsSevenMonths(int amountCreditsSevenMonths) {
        this.amountCreditsSevenMonths = amountCreditsSevenMonths;
    }

    public int getAmountCreditsThirteenMonths() {
        return amountCreditsThirteenMonths;
    }

    public void setAmountCreditsThirteenMonths(int amountCreditsThirteenMonths) {
        this.amountCreditsThirteenMonths = amountCreditsThirteenMonths;
    }

    public int getAmountCreditsNineteenMonths() {
        return amountCreditsNineteenMonths;
    }

    public void setAmountCreditsNineteenMonths(int amountCreditsNineteenMonths) {
        this.amountCreditsNineteenMonths = amountCreditsNineteenMonths;
    }

    public double getStandardDeviationGradesSevenMonths() {
        return standardDeviationGradesSevenMonths;
    }

    public void setStandardDeviationGradesSevenMonths(double standardDeviationGradesSevenMonths) {
        this.standardDeviationGradesSevenMonths = standardDeviationGradesSevenMonths;
    }

    public double getStandardDeviationGradesThirteenMonths() {
        return standardDeviationGradesThirteenMonths;
    }

    public void setStandardDeviationGradesThirteenMonths(double standardDeviationGradesThirteenMonths) {
        this.standardDeviationGradesThirteenMonths = standardDeviationGradesThirteenMonths;
    }

    public double getStandardDeviationGradesNineteenMonths() {
        return standardDeviationGradesNineteenMonths;
    }

    public void setStandardDeviationGradesNineteenMonths(double standardDeviationGradesNineteenMonths) {
        this.standardDeviationGradesNineteenMonths = standardDeviationGradesNineteenMonths;
    }

    public int[][] getCreditGainsSevenMonthsArr() {
        return creditGainsSevenMonthsArr;
    }

    public void setCreditGainsSevenMonthsArr(int[][] creditGainsSevenMonthsArr) {
        this.creditGainsSevenMonthsArr = creditGainsSevenMonthsArr;
    }

    public int[][] getCreditGainsThirteenMonthsArr() {
        return creditGainsThirteenMonthsArr;
    }

    public void setCreditGainsThirteenMonthsArr(int[][] creditGainsThirteenMonthsArr) {
        this.creditGainsThirteenMonthsArr = creditGainsThirteenMonthsArr;
    }

    public int[][] getCreditGainsNineteenMonthsArr() {
        return creditGainsNineteenMonthsArr;
    }

    public void setCreditGainsNineteenMonthsArr(int[][] creditGainsNineteenMonthsArr) {
        this.creditGainsNineteenMonthsArr = creditGainsNineteenMonthsArr;
    }

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
        this.amountCreditsSevenMonths = 0;
        this.amountCreditsThirteenMonths = 0;
        this.amountCreditsNineteenMonths = 0;
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

    @JsonIgnore
    public HashMap<Integer, Integer> getCreditGainsSevenMonths() {
        return creditGainsSevenMonths;
    }

    public void setCreditGainsSevenMonths(HashMap<Integer, Integer> creditGainsSevenMonths) {
        this.creditGainsSevenMonths = creditGainsSevenMonths;
    }

    @JsonIgnore
    public HashMap<Integer, Integer> getCreditGainsThirteenMonths() {
        return creditGainsThirteenMonths;
    }

    public void setCreditGainsThirteenMonths(HashMap<Integer, Integer> creditGainsThirteenMonths) {
        this.creditGainsThirteenMonths = creditGainsThirteenMonths;
    }

    @JsonIgnore
    public HashMap<Integer, Integer> getCreditGainsNineteenMonths() {
        return creditGainsNineteenMonths;
    }

    public void setCreditGainsNineteenMonths(HashMap<Integer, Integer> creditGainsNineteenMonths) {
        this.creditGainsNineteenMonths = creditGainsNineteenMonths;
    }
}
