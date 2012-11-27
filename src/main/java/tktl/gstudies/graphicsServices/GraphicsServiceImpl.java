
package tktl.gstudies.graphicsServices;

import tktl.gstudies.domainForGraphics.CourseInstance;
import tktl.gstudies.domainForGraphics.Student;
import tktl.gstudies.graphicsServices.LineService;
import tktl.gstudies.graphicsServices.GraphicsService;
import tktl.gstudies.graphicalObjects.BoxCoordinatesForLines;
import tktl.gstudies.graphicalObjects.Rectangle;
import tktl.gstudies.graphicalObjects.AbstractGraphicalObject;
import tktl.gstudies.graphicalObjects.Text;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tktl.gstudies.repositories.JDBCRepository;

/**
 * A service class for obtaining graphical objects for rendering.
 * @author hkeijone
 */
@Service
@Qualifier("real")
public class GraphicsServiceImpl implements GraphicsService {

    @Autowired
    @Qualifier("real")
    private LineService lineService;
    @Autowired
    private JDBCRepository testRepository;
    private List<AbstractGraphicalObject> graphObjs;
    private int boxWidth = 50;
    private int boxHeight = 20;
    private int shiftBetweenColumns = 150;

    @Override
    public List<AbstractGraphicalObject> getGraphicsData() {

        this.graphObjs = new ArrayList<AbstractGraphicalObject>();
        lineService.setCoords(new ArrayList<List<BoxCoordinatesForLines>>());
        List<Student> studs = new ArrayList<Student>(this.getStudsAndCoursesFromData().values());
        //System.out.println(studs);
        this.lineService.setStuds(studs);
        for (int i = 0; i < this.getMaxCourses(studs); i++) {//getMaxCourses hakee suurimman kurssimäärän
            this.graphObjs.addAll(this.getNthSetOfNodes(studs, i, i));
        }
        for (int i = 0; i < studs.size(); i++) {
        }
        return this.graphObjs;
    }

    private HashMap<Integer, Student> getStudsAndCoursesFromData() {
        HashMap<Integer, Student> studs = new HashMap<Integer, Student>();
        List<Map> rows = this.testRepository.fetchData();
        for (Map row : rows) {
            Integer HLO = (Integer) row.get("HLO");
            if (!studs.containsKey(HLO)) {
                Student toAdd = new Student(HLO, this.getCoursesForStud(HLO, rows));
                studs.put(HLO, toAdd);
            }
        }
        return studs;
    }

    private List<CourseInstance> getCoursesForStud(Integer HLO, List<Map> rows) {
        List courses = new ArrayList<CourseInstance>();
        for (Map row : rows) {
            if (((Integer) row.get("HLO")).intValue() == HLO.intValue()) {
                courses.add(new CourseInstance((String) row.get("TUNNISTE"), row.get("SUORPVM").toString()));
            }
        }
        if (courses.size() > 11) {
            System.out.println("LIIAN PITKÄ: " + courses);
        }
        return courses;
    }

    private List<AbstractGraphicalObject> getNthSetOfNodes(List<Student> studs, int n, int offset) {
        List<AbstractGraphicalObject> objs = new ArrayList();
        List<String> diffCourses = this.differentCoursesOnNthSet(n, studs);
        this.lineService.getCoords().add(new ArrayList<BoxCoordinatesForLines>());
        for (int i = 1; i <= diffCourses.size(); i++) {
            objs.add(new Rectangle("rect", ((offset + 1) * this.shiftBetweenColumns), (i * 50), this.boxWidth, this.boxHeight, 5));
            objs.add(new Text(((offset + 1) * this.shiftBetweenColumns) + 25, (i * 50) + 10, diffCourses.get(i - 1)));
            this.lineService.getCoords().get(n).add(new BoxCoordinatesForLines(diffCourses.get(i - 1), ((offset + 1) * this.shiftBetweenColumns), (i * 50) + 10, ((offset + 1) * this.shiftBetweenColumns) + 50, (i * 50) + 10));
        }
        return objs;
    }

    private List<String> differentCoursesOnNthSet(int n, List<Student> studs) {
        ArrayList<String> diffCourses = new ArrayList<String>();
        for (Student s : studs) {

            if ((n < s.getCourses().size()) && (!diffCourses.contains(s.getCourses().get(n).getCourse().name()))) {//KUSEEE!!
                diffCourses.add(s.getCourses().get(n).getCourse().name());
            }
        }
        return diffCourses;
    }

    private int getMaxCourses(List<Student> studs) {
        int max = 0;
        for (int i = 0; i < studs.size(); i++) {
            if (studs.get(i).getCourses().size() > max) {
                max = studs.get(i).getCourses().size();
            }
        }
        return max;
    }
}
