/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.AbstractGraphicalObject;
import tktl.gstudies.domain.BoxCoordinatesForLines;
import tktl.gstudies.domain.Course;
import tktl.gstudies.domain.CourseInstance;
import tktl.gstudies.domain.Student;
import tktl.gstudies.repositories.TestRepository;

@Service
@Qualifier("real")
public class GraphicsServiceImpl implements GraphicsService {

    @Autowired
    @Qualifier("dummy")
    private LineService lineService;
    @Autowired
    private TestRepository testRepository;
    private List<AbstractGraphicalObject> graphObjs;
    private List<Student> studs;

    @Override
    public List<AbstractGraphicalObject> getGraphicsData() {

        this.graphObjs = new ArrayList<AbstractGraphicalObject>();
        lineService.setCoords(new ArrayList<List<BoxCoordinatesForLines>>());
        this.getStudsAndCoursesFromData();
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
                System.out.println(toAdd);
                //TODO: kurssien sorttauskutsu tähän
            }
        }
        return studs;
    }

    private List<CourseInstance> getCoursesForStud(Integer HLO, List<Map> rows) {
        List courses = new ArrayList<CourseInstance>();
        for (Map row : rows) {
            if (((Integer) row.get("HLO")).intValue() == HLO.intValue()) {
               courses.add(new CourseInstance((String)row.get("TUNNISTE"), row.get("SUORPVM").toString()));
            }
        }
        return courses;
    }
    

}
