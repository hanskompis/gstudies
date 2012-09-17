/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.*;

/**
 *
 * @author hkeijone
 */
@Service
@Qualifier("real")
public class LineServiceImpl implements LineService {

    private HashMap<String, Line> lines;
    private List<List<BoxCoordinatesForLines>> coords;
    private List<Student> studs;

    @Override
    public void addLineString(int mx, int my, int lx, int ly) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public List<Line> getSumPathData() {
        if (this.lines != null) {
            this.lines.clear();
        } else {
            this.lines = new HashMap<String, Line>();
        }
        for (int i = 0; i < this.studs.size(); i++) { //iteroidaan studentit
            List<CourseInstance> currentCourseSet = this.studs.get(i).getCourses();
            for (int j = 0; j < currentCourseSet.size(); j++) {//iteroidaan studentin courset
                if ((j + 1) < currentCourseSet.size()) {
                    int leftX = this.getCoordinatesForCourse(j, currentCourseSet.get(j).getCourse().name(), false).get(0); //TODO: tee fiksummin while refactoring
                    int leftY = this.getCoordinatesForCourse(j, currentCourseSet.get(j).getCourse().name(), false).get(1);
                    int rightX = this.getCoordinatesForCourse((j + 1), currentCourseSet.get(j + 1).getCourse().name(), true).get(0);
                    int rightY = this.getCoordinatesForCourse((j + 1), currentCourseSet.get(j + 1).getCourse().name(), true).get(1);
//                    this.lines.add(new Line(leftX, leftY, rightX, rightY));
                    Line toAdd = new Line(leftX, leftY, rightX, rightY);
                    if (!this.lines.containsKey(toAdd.getPathString())) {
                        this.lines.put(toAdd.getPathString(), toAdd);
                    } else {
                        Line l = this.lines.get(toAdd.getPathString());
                        l.setWeight(l.getWeight() + 1);
                        l.getWeightText().setText(Integer.toString(l.getWeight()));
                    }
                }
            }
        }
        ArrayList<Line> lesReturnables = new ArrayList<Line>();
        lesReturnables.addAll(this.lines.values());
        return lesReturnables;
    }

    @Override
    public List<Student> getStuds() {
        return studs;       
    }

    @Override
    public void setStuds(List studs) {
        this.studs = studs;
    }

    private List<Integer> getCoordinatesForCourse(int paragraph, String courseName, boolean left) {
        List<BoxCoordinatesForLines> coordsList = this.coords.get(paragraph);
        List<Integer> coordsToReturn = new ArrayList<Integer>();
        for (int i = 0; i < coordsList.size(); i++) {
            if (coordsList.get(i).getCourseName().equals(courseName)) {
                if (left) {
                    coordsToReturn.add(coordsList.get(i).getLeftX());
                    coordsToReturn.add(coordsList.get(i).getLeftY());

                } else {
                    coordsToReturn.add(coordsList.get(i).getRightX());
                    coordsToReturn.add(coordsList.get(i).getRightY());
                }
            }
        }
        return coordsToReturn;
    }
}
