package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.AbstractGraphicalObject;
import tktl.gstudies.domain.Course;
import tktl.gstudies.domain.Rectangle;
import tktl.gstudies.domain.Student;

@Service
public class GraphicsServiceImpl implements GraphicsService {

    @Autowired
    private Student student;
    @Autowired
    private Course course;
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
        List<Student> studs = generateDummyStudents(5);
        return this.graphObjs;
    }

    private List<Student> generateDummyStudents(int n) {
        List<Student> studs = new ArrayList<Student>();
        Random generator = new Random();
        for (int i = 0; i < n; i++) {
            student.setNimi(Integer.toString(generator.nextInt(1000)));
            student.setCourses(null);
            studs.add(student);
            System.out.println(studs.get(i).getNimi());
        }
        return studs;
    }

    private List<Course> generateCourseSet(int n) {
        List<Course> courses = new ArrayList<Course>();
        return courses;
    }
}
