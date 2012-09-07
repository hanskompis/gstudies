
package tktl.gstudies.domain;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class Student {
    private String nimi;
    private List<DummyCourse> courses;

    public Student(String nimi, List<DummyCourse> courses) {
        this.nimi = nimi;
        this.courses = courses;
    }

    public Student() {
    }

    public List<DummyCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<DummyCourse> courses) {
        this.courses = courses;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public void addCourse(DummyCourse course){
        if(this.courses == null){
            this.courses = new ArrayList();
        }
        this.courses.add(course);
    }
    
    public String printCourses(){
        StringBuilder sb = new StringBuilder();
        for(DummyCourse c : this.getCourses()){
            sb.append(c.getIndex()+" "+c.name()+"\n");
        }
        return sb.toString();
    }
    @Override
    public String toString(){
        return "nimi: "+this.getNimi()+" kurssit: \n" + this.printCourses();
    }
}
