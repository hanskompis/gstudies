package tktl.gstudies.domainForGraphics;

import java.util.Collections;
import java.util.List;

/**
 *A class to store student information in order to calculate graph data. 
 * @author hkeijone
 */
public class Student {

    private Integer HLO;
    private List<CourseInstance> courses;

    public Student() {
    }

    public Student(Integer HLO, List<CourseInstance> courses) {
        this.HLO = HLO;
        this.courses = courses;
        Collections.sort(courses);
    }

    public List<CourseInstance> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseInstance> courses) {
        this.courses = courses;
    }

    public Integer getHLO() {
        return HLO;
    }

    public void setHLO(Integer HLO) {
        this.HLO = HLO;
    }
    
    @Override
    public String toString(){
        return Integer.toString(HLO)+" "+this.courses;
    }
}
