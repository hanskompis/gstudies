
package tktl.gstudies.graphicsServices;

import java.util.HashMap;
import java.util.List;
import tktl.gstudies.graphicalObjects.BoxCoordinatesForLines;
import tktl.gstudies.graphicalObjects.Line;
import tktl.gstudies.domainForGraphics.DummyStudent;

public interface LineService {
  //  public void addLineString(String line);
    public void addLineString(int mx, int my, int lx, int ly);
  //  public List<String> getLines(List studs);
  //  public void addCourseSet(List<String> courses);
//    public String CoursesToString();
   // public void setLineStrings(List<String> lineStrings);
    // public void setCourses(List<List<String>> courses);
    // public List<String> getLineStrings();
   //  public List<List<String>> getCourses();
     public List<List<BoxCoordinatesForLines>> getCoords();
     public void setCoords(List<List<BoxCoordinatesForLines>> coords);
     public List<Line> getSumPathData();
     public List getStuds();
     public void setStuds(List studs);
}