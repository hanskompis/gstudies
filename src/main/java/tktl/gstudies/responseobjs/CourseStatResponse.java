
package tktl.gstudies.responseobjs;

public class CourseStatResponse {
    private String courseName;
    private double averageGradeSixMonths;
    private double averageGradetwelvemonths;
    private int studsEnrolled;
    private int passedCourse;
    private int failedCourse;

    public CourseStatResponse(String courseName, double averageGradeSixMonths, double averageGradetwelvemonths, int passedCourse, int failedCourse) {
        this.courseName = courseName;
        this.averageGradeSixMonths = averageGradeSixMonths;
        this.averageGradetwelvemonths = averageGradetwelvemonths;
        this.passedCourse = passedCourse;
        this.failedCourse = failedCourse;
    }

    public CourseStatResponse() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getAverageGradeSixMonths() {
        return averageGradeSixMonths;
    }

    public void setAverageGradeSixMonths(double averageGradeSixMonths) {
        this.averageGradeSixMonths = averageGradeSixMonths;
    }

    public double getAverageGradetwelvemonths() {
        return averageGradetwelvemonths;
    }

    public void setAverageGradetwelvemonths(double averageGradetwelvemonths) {
        this.averageGradetwelvemonths = averageGradetwelvemonths;
    }

    public int getPassedCourse() {
        return passedCourse;
    }

    public void setPassedCourse(int passedCourse) {
        this.passedCourse = passedCourse;
    }

    public int getFailedCourse() {
        return failedCourse;
    }

    public void setFailedCourse(int failedCourse) {
        this.failedCourse = failedCourse;
    }

    public int getStudsEnrolled() {
        return studsEnrolled;
    }

    public void setStudsEnrolled(int studsEnrolled) {
        this.studsEnrolled = studsEnrolled;
    }
    
    
    
}
