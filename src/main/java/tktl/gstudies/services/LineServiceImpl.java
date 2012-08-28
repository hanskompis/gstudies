package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.BoxCoordinatesForLines;
import tktl.gstudies.domain.Course;
import tktl.gstudies.domain.Student;

@Service
public class LineServiceImpl implements LineService {

    private List<String> lineStrings;
    private List<List<String>> courses;// TODO: Tarvitaanko täällä ollenkaan?
    private List<List<BoxCoordinatesForLines>> coords;
    private List<Student> studs;

    @Override
    public void addLineString(String line) {
        if (this.lineStrings == null) {
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
        if (this.courses == null) {
            this.courses = new ArrayList<List<String>>();
        }
        this.courses.add(courses);
    }

    @Override
    public String CoursesToString() {
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

    @Override
    public List<List<BoxCoordinatesForLines>> getCoords() {
        return coords;
    }

    @Override
    public void setCoords(List<List<BoxCoordinatesForLines>> coords) {
        this.coords = coords;
    }

    @Override
    public List<Student> getStuds() {
        return studs;
    }

    @Override
    public void setStuds(List<Student> studs) {
        this.studs = studs;
    }

    @Override
    public List<String> getSumPathData() {
        this.lineStrings = new ArrayList<String>();
        for (int i = 0; i < this.studs.size(); i++) {
            List<Course> currentCourseSet = this.studs.get(i).getCourses();
            for (int j = 0; j < currentCourseSet.size(); j++) {
                if (currentCourseSet.get(j + 1) != null) {
                    this.lineStrings.add("M"+this.getCoordinatesForCourse(j, currentCourseSet.get(j).name(), false)+
                            "L"+this.getCoordinatesForCourse((j+1),currentCourseSet.get(j+1).name() , true));
                }
            }

        }
        return this.lineStrings;
    }

    private String getCoordinatesForCourse(int paragraph, String courseName, boolean left) {
        StringBuilder sb = new StringBuilder();
        List<BoxCoordinatesForLines> coordsList = this.coords.get(paragraph);
        for (int i = 0; i < coordsList.size(); i++) {
            if (coordsList.get(i).getCourseName().equals(courseName)) {
                if (left) {
                    String x = Integer.toString(coordsList.get(i).getLeftX());
                    sb.append(x);
                    sb.append(",");
                    String y = Integer.toString(coordsList.get(i).getLeftY());
                    sb.append(y);
                } else {
                    String x = Integer.toString(coordsList.get(i).getRightX());
                    sb.append(x);
                    sb.append(",");
                    String y = Integer.toString(coordsList.get(i).getRightY());
                    sb.append(y);
                }
            }
        }

        return sb.toString();
    }
}
