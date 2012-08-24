
package tktl.gstudies.domain;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Student {
    private String nimi;
    private List<Course> courses;

    public Student(String nimi, List<Course> courses) {
        this.nimi = nimi;
        this.courses = courses;
    }

    public Student() {
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public void addCourse(Course course){
        if(this.courses == null){
            this.courses = new ArrayList();
        }
        this.courses.add(course);
    }
}
