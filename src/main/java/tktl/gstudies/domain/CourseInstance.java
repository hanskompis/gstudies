package tktl.gstudies.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseInstance {

    private Course course;
    private Date SUORPVM;

    public CourseInstance() {
    }

    public CourseInstance(String TUNNISTE, String SUORPVM) {
        this.course = this.getCourseByCourseCode(TUNNISTE);
        this.SUORPVM = this.makeDate(SUORPVM);
    }

    public Date getSUORPVM() {
        return SUORPVM;
    }

    public void setSUORPVM(Date SUORPVM) {
        this.SUORPVM = SUORPVM;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    private Course getCourseByCourseCode(String courseCode) {
        for (Course c : Course.values()) {
            if (c.sameCourse(courseCode)) {
                return c;
            }
        }
        return null;
    }

    private Date makeDate(String dateString) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date toReturn = new Date();
        try {
            toReturn = df.parse(dateString);
            return toReturn;
        } catch (ParseException e) {
            System.out.println("KUSI!");
        }
        return toReturn;
    }

    public int compareTo(Date toCompare) {
        if (this.SUORPVM.before(SUORPVM)) {
            return -1;
        } else if (this.SUORPVM.after(SUORPVM)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        if (this.course == null) {
            return "kurssi null";
        } else {
            return this.getCourse().name() + " " + this.SUORPVM;
        }
    }
}
