
package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.Student;

@Service
public class LineServiceImpl implements LineService{
    
    private List<String> lineStrings;
    private List<List<String>> courses;

    @Override
    public void addLineString(String line) {
        if(this.lineStrings==null){
            this.lineStrings = new ArrayList<String>();
        }
    }

    @Override
    public List<String> getLines(List<Student> studs) {
        
        return this.lineStrings;
    }

    @Override
    public void addLineString(int mx, int my, int lx, int ly) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addCourseSet(List<String> courses) {
        if(this.courses == null){
            this.courses = new ArrayList<List<String>>();
        }
        this.courses.add(courses);
    }
    @Override
    public String CoursesToString(){
        return "line courses: \n" + this.courses.toString();
    }

    @Override
    public List<List<String>> getCourses() {
        return courses;
    }

    @Override
    public void setCourses(List<List<String>> courses) {
        this.courses = courses;
    }

    @Override
    public List<String> getLineStrings() {
        return lineStrings;
    }

    @Override
    public void setLineStrings(List<String> lineStrings) {
        this.lineStrings = lineStrings;
    }
    
}
