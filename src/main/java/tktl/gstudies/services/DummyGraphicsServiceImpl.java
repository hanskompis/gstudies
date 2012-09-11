package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.*;

@Service
@Qualifier("dummy")
public class DummyGraphicsServiceImpl implements GraphicsService {

    @Autowired
    @Qualifier("dummy")
    private LineService lineService;
    
    private String[] courseCodes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private int amountStuds = 5;
    private int amountCourses = 3;
    private List<AbstractGraphicalObject> graphObjs;
//    private List<List<BoxCoordinatesForLines>> coords;


    @Override
    public List<AbstractGraphicalObject> getGraphicsData() {
        this.graphObjs = new ArrayList<AbstractGraphicalObject>();
        lineService.setCoords(new ArrayList<List<BoxCoordinatesForLines>>());
        List<DummyStudent> studs = generateDummyStudents(this.amountStuds, this.amountCourses);
        this.lineService.setStuds(studs);
        this.lineService.setCourses(null);
        for (int i = 0; i < this.getMaxCourses(studs); i++) {
            this.graphObjs.addAll(this.getNthSetOfNodes(studs, i, i));
        }
        this.lineService.getSumPathData();
        return this.graphObjs;
    }

    private List<DummyStudent> generateDummyStudents(int amountStuds, int amountCourses) {
        List<DummyStudent> studs = new ArrayList<DummyStudent>();
        Random generator = new Random();
        for (int i = 0; i < amountStuds; i++) {
            DummyStudent student = new DummyStudent("Opp" + Integer.toString(generator.nextInt(1000)), this.generateCourseSet(amountCourses));
            studs.add(student);
            //System.out.println(student);
        }
        return studs;
    }

    public void shuffleCourseCodes() {
        for (int i = this.courseCodes.length; i > 1; i--) {
            String temp = this.courseCodes[i - 1];
            int randIx = (int) (Math.random() * i);
            this.courseCodes[i - 1] = this.courseCodes[randIx];
            this.courseCodes[randIx] = temp;
        }
    }

    public DummyCourse getCourse(String courseCode) {
        for (DummyCourse c : DummyCourse.values()) {
            if (c.getIndex().equals(courseCode)) {
                return c;
            }
        }
        return null;
    }

    private List<DummyCourse> generateCourseSet(int amountCourses) {
        List<DummyCourse> courses = new ArrayList<DummyCourse>();
        this.shuffleCourseCodes();
        for (String p : Arrays.copyOfRange(this.courseCodes, 0, amountCourses)) {
            courses.add(this.getCourse(p));
        }
        return courses;
    }

    private int getMaxCourses(List<DummyStudent> studs) {
        int max = 0;
        for (int i = 0; i < studs.size(); i++) {
            if (studs.get(i).getCourses().size() > max) {
                max = studs.get(i).getCourses().size();
            }
        }
        return max;
    }

    private List<AbstractGraphicalObject> getNthSetOfNodes(List<DummyStudent> studs, int n, int offset) {
        List<AbstractGraphicalObject> objs = new ArrayList();
        List<String> diffCourses = this.differentCoursesOnNthSet(n, studs);
        this.lineService.getCoords().add(new ArrayList<BoxCoordinatesForLines>());
        for (int i = 1; i <= diffCourses.size(); i++) {
            objs.add(new Rectangle("rect", ((offset + 1) * 100), (i * 50), 50, 20, 5));
            objs.add(new Text(((offset + 1) * 100) + 25, (i * 50) + 10, diffCourses.get(i - 1)));
//            this.lineService.getCoords().get(n).add(new BoxCoordinatesForLines(diffCourses.get(i-1), ((offset + 1) * 100), (i * 50)+10 ,((offset + 1) * 150) , (i * 50)+10));
            this.lineService.getCoords().get(n).add(new BoxCoordinatesForLines(diffCourses.get(i - 1), ((offset + 1) * 100), (i * 50) + 10, ((offset + 1) * 100)+50, (i * 50) + 10));

        }
        return objs;
    }

    private List<String> differentCoursesOnNthSet(int n, List<DummyStudent> studs) {
        ArrayList<String> diffCourses = new ArrayList<String>();
        for (DummyStudent s : studs) {
            if (!diffCourses.contains(s.getCourses().get(n).name())) {
                diffCourses.add(s.getCourses().get(n).name());
            }
        }
        lineService.addCourseSet(diffCourses);
        return diffCourses;
    }
}
