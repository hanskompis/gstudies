
package tktl.gstudies.services;

import java.util.List;
import tktl.gstudies.domain.BoxCoordinatesForLines;
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
     public List<List<BoxCoordinatesForLines>> getCoords();
     public void setCoords(List<List<BoxCoordinatesForLines>> coords);
     public List<String> getSumPathData();
     public List<Student> getStuds();
     public void setStuds(List<Student> studs);


}