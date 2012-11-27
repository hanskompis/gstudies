package tktl.gstudies.responseobjs;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class for storing data of course-instance under inspection. 
 * For rendering purposes also. 
 *
 * @author hkeijone
 */
public class CourseStatsResponseObj {

    private int[] CSCourseGrades;
    private int[] OtherCourseGrades;
    private List<CourseStats> courseStatsObjs;
    private int CSPassed;
    private int CSFailed;
    private int OtherPassed;
    private int OtherFailed;
    private int amountCSStuds;
    private int amountOtherStuds;
    private int amountAllStudents;
    private double allPercentagePassed;
    private double allPercentageFailed;
    private double CSPercentagePassed;
    private double CSPercentageFailed;
    private double OtherPercentagePassed;
    private double OtherPercentageFailed;

    public void addCourseStatsObj(CourseStats cs) {
        if (this.courseStatsObjs == null) {
            this.courseStatsObjs = new ArrayList<CourseStats>();
        }
        this.courseStatsObjs.add(cs);
    }

    private void countStudents() {

        for (CourseStats cs : courseStatsObjs) {
            if (cs.getGroupIdentifier().equals("CSPassed")) {
                this.CSPassed = cs.getAmountStudents();
            } else if (cs.getGroupIdentifier().equals("CSFailed")) {
                this.CSFailed = cs.getAmountStudents();
            } else {
                System.out.println("countstuds failed!!!");
            }
        }
        this.amountCSStuds = this.CSFailed + this.CSPassed;
        this.amountOtherStuds = this.OtherFailed + this.OtherPassed;
        this.amountAllStudents = this.amountCSStuds + this.amountOtherStuds;
    }

    public void countPercentages() {
        this.countStudents();
        this.CSPercentagePassed = (100F * this.CSPassed) / this.amountCSStuds;
        this.CSPercentageFailed = (100F * this.CSFailed) / this.amountCSStuds;
    }

    @Override
    public String toString() {
        return "studs: " + this.amountAllStudents + "\n"
                + "CS studs passed: " + this.CSPassed + "\n"
                + "PERSEntage of CS studs: " + this.CSPercentagePassed + "\n"
                + "CS studs failed: " + this.CSFailed + "\n"
                + "PERSEntage of CS studs: " + this.CSPercentageFailed + "\n"
                + "CS grade distribution: " + this.CSCourseGrades[0] + " " + this.CSCourseGrades[1] + " " + this.CSCourseGrades[2] + " " + this.CSCourseGrades[3] + " " + this.CSCourseGrades[4] + "\n"
                + this.courseStatsObjs.get(0) + this.courseStatsObjs.get(1) + this.courseStatsObjs.get(2);
    }

    public int[] getCSCourseGrades() {
        return CSCourseGrades;
    }

    public void setCSCourseGrades(int[] CSCourseGrades) {
        this.CSCourseGrades = CSCourseGrades;
    }

    public int[] getOtherCourseGrades() {
        return OtherCourseGrades;
    }

    public void setOtherCourseGrades(int[] OtherCourseGrades) {
        this.OtherCourseGrades = OtherCourseGrades;
    }

    public List<CourseStats> getCourseStatsObjs() {
        return courseStatsObjs;
    }

    public void setCourseStatsObjs(List<CourseStats> courseStatsObjs) {
        this.courseStatsObjs = courseStatsObjs;
    }

    public int getCSPassed() {
        return CSPassed;
    }

    public void setCSPassed(int CSPassed) {
        this.CSPassed = CSPassed;
    }

    public int getCSFailed() {
        return CSFailed;
    }

    public void setCSFailed(int CSFailed) {
        this.CSFailed = CSFailed;
    }

    public int getOtherPassed() {
        return OtherPassed;
    }

    public void setOtherPassed(int OtherPassed) {
        this.OtherPassed = OtherPassed;
    }

    public int getOtherFailed() {
        return OtherFailed;
    }

    public void setOtherFailed(int OtherFailed) {
        this.OtherFailed = OtherFailed;
    }

    public int getAmountCSStuds() {
        return amountCSStuds;
    }

    public void setAmountCSStuds(int amountCSStuds) {
        this.amountCSStuds = amountCSStuds;
    }

    public int getAmountOtherStuds() {
        return amountOtherStuds;
    }

    public void setAmountOtherStuds(int amountOtherStuds) {
        this.amountOtherStuds = amountOtherStuds;
    }

    public int getAmountAllStudents() {
        return amountAllStudents;
    }

    public void setAmountAllStudents(int amountAllStudents) {
        this.amountAllStudents = amountAllStudents;
    }

    public double getAllPercentagePassed() {
        return allPercentagePassed;
    }

    public void setAllPercentagePassed(double allPercentagePassed) {
        this.allPercentagePassed = allPercentagePassed;
    }

    public double getAllPercentageFailed() {
        return allPercentageFailed;
    }

    public void setAllPercentageFailed(double allPercentageFailed) {
        this.allPercentageFailed = allPercentageFailed;
    }

    public double getCSPercentagePassed() {
        return CSPercentagePassed;
    }

    public void setCSPercentagePassed(double CSPercentagePassed) {
        this.CSPercentagePassed = CSPercentagePassed;
    }

    public double getCSPercentageFailed() {
        return CSPercentageFailed;
    }

    public void setCSPercentageFailed(double CSPercentageFailed) {
        this.CSPercentageFailed = CSPercentageFailed;
    }

    public double getOtherPercentagePassed() {
        return OtherPercentagePassed;
    }

    public void setOtherPercentagePassed(double OtherPercentagePassed) {
        this.OtherPercentagePassed = OtherPercentagePassed;
    }

    public double getOtherPercentageFailed() {
        return OtherPercentageFailed;
    }

    public void setOtherPercentageFailed(double OtherPercentageFailed) {
        this.OtherPercentageFailed = OtherPercentageFailed;
    }
}
