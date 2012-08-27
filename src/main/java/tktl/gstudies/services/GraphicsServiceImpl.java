package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.*;

@Service
@Qualifier("dummy")
public class GraphicsServiceImpl implements GraphicsService {

    private String[] courseCodes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private int amountStuds = 5;
    private int amountCourses = 5;
    private List<AbstractGraphicalObject> graphObjs;

    @Override
    public List<AbstractGraphicalObject> getDummyData() {
        this.graphObjs = new ArrayList<AbstractGraphicalObject>();
        for (int i = 1; i <= 10; i++) {
            this.graphObjs.add(new Rectangle("rect", 20, (i * 50), 50, 20, 5));
        }
        return graphObjs;
    }

    @Override
    public List<AbstractGraphicalObject> getSumMoreDummyData() {
        this.graphObjs = new ArrayList<AbstractGraphicalObject>();
        List<Student> studs = generateDummyStudents(this.amountStuds, this.amountCourses);
        for (int i = 0; i < this.getMaxCourses(studs); i++) {
            this.graphObjs.addAll(this.getNthSetOfNodes(studs, i, i));
        }
//        this.graphObjs.addAll(this.getNthSetOfRectangles(studs, 0));
        return this.graphObjs;
    }

    private List<Student> generateDummyStudents(int amountStuds, int amountCourses) {
        List<Student> studs = new ArrayList<Student>();
        Random generator = new Random();
        for (int i = 0; i < amountStuds; i++) {
            Student student = new Student("Opp" + Integer.toString(generator.nextInt(1000)), this.generateCourseSet(amountCourses));
            studs.add(student);
            System.out.println(student);
        }
   //     System.out.println("diff: " + this.differentCoursesOnNthSet(2, studs));
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

    public Course getCourse(String courseCode) {
        for (Course c : Course.values()) {
            if (c.getIndex().equals(courseCode)) {
                return c;
            }
        }
        return null;
    }

    private List<Course> generateCourseSet(int amountCourses) {
        List<Course> courses = new ArrayList<Course>();
        this.shuffleCourseCodes();
        for (String p : Arrays.copyOfRange(this.courseCodes, 0, amountCourses)) {
            courses.add(this.getCourse(p));
        }
        return courses;
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

    private List<AbstractGraphicalObject> getNthSetOfNodes(List<Student> studs, int n, int offset) {
        List<AbstractGraphicalObject> rects = new ArrayList();
        List<String> diffCourses = this.differentCoursesOnNthSet(n, studs);
        Collections.sort(diffCourses); //TODO: aiheuttaako enemmän harmia kuin hyötyä?
        for (int i = 1; i <= diffCourses.size(); i++) {
            rects.add(new Rectangle("rect", ((offset + 1) * 100), (i * 50), 50, 20, 5));   
            rects.add(new Text(((offset + 1) * 100)+25, (i * 50)+10, diffCourses.get(i-1)));
        }
        return rects; 
    }

        private List<String> differentCoursesOnNthSet(int n, List<Student> studs) {
        ArrayList<String> diffCourses = new ArrayList<String>();
        for (Student s : studs) {
            if (!diffCourses.contains(s.getCourses().get(n).name())) {
                diffCourses.add(s.getCourses().get(n).name());
            }
        }
        return diffCourses;
    }
}
