package tktl.gstudies.domainForGraphics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseInstance implements Comparable<CourseInstance> {

    private Course course;
    private Date suorpvm;

    public CourseInstance() {
    }

    public CourseInstance(String tunniste, String suorpvm) {
        this.course = this.getCourseByCourseCode(tunniste);
        this.suorpvm = this.makeDate(suorpvm);
    }

    public Date getSuorpvm() {
        return suorpvm;
    }

    public void setSuorpvm(Date suorpvm) {
        this.suorpvm = suorpvm;
    }

    
    
    public void setSuorpvm(String suorpvm){
        this.suorpvm = this.makeDate(suorpvm);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    public void setCourse(String tunniste){
        this.course = this.getCourseByCourseCode(tunniste);
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

    @Override
    public String toString() {
        if (this.course == null) {
            return "kurssi null";
        } else {
            return this.getCourse().name() + " " + this.suorpvm;
        }
    }

    @Override
    public int compareTo(CourseInstance t) {
        if (this.suorpvm.before(t.getSuorpvm())) {
            return -1;
        } else if (this.suorpvm.after(t.getSuorpvm())) {
            return 1;
        } else {
            return 0;
        }
    }
    public static void main(String[] args) {
            CourseInstance ci = new CourseInstance("58131", "10-10-2010");
            Date d = ci.getSuorpvm();
            System.out.println(d.toString());
            Course c = ci.getCourse();
            System.out.println(c == null);
            System.out.println(ci.course.name());
    }
}
