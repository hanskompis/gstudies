
package tktl.gstudies.domain;

public class CourseGradeResponse {
    private int amountCourses;
    private double averageGrade;

    public CourseGradeResponse() {
    }

    public CourseGradeResponse(int amountCourses, double averageGrade) {
        this.amountCourses = amountCourses;
        this.averageGrade = averageGrade;
    }

    public int getAmountCourses() {
        return amountCourses;
    }

    public void setAmountCourses(int amountCourses) {
        this.amountCourses = amountCourses;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }
    
    
}
