
package tktl.gstudies.services;

import java.util.List;
import tktl.gstudies.domain.Student;

public interface LineService {
    public void addLineString(String line);
    public void addLineString(int mx, int my, int lx, int ly);
    public List<String> getLines(List<Student> studs);
    public void addCourseSet(List<String> courses);
    public String CoursesToString();
    public void setLineStrings(List<String> lineStrings);
     public void setCourses(List<List<String>> courses);
     public List<String> getLineStrings();
     public List<List<String>> getCourses();


}
