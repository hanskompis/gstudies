package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.Arrays;
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
    
    private String[] courseCodes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Autowired
    private Student student;
//    @Autowired
//    private Course course;
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
        List<Student> studs = generateDummyStudents(10,7);
        return this.graphObjs;
    }

    private List<Student> generateDummyStudents(int amountStuds, int amountCourses) {
        List<Student> studs = new ArrayList<Student>();
        Random generator = new Random();
        for (int i = 0; i < amountStuds; i++) {
            student.setNimi("Opp"+Integer.toString(generator.nextInt(1000)));
            student.setCourses(this.generateCourseSet(amountCourses));
            studs.add(student);
            System.out.println(student);           
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
    
    public Course getCourse(String courseCode){
        for(Course c : Course.values()){
            if(c.getIndex().equals(courseCode)){
                    return c;
            }
        }
        return null;
    }
    

    private List<Course> generateCourseSet(int amountCourses) {
        List<Course> courses = new ArrayList<Course>();
        this.shuffleCourseCodes();
        for(String p : Arrays.copyOfRange(this.courseCodes, 0,amountCourses)){
//            System.out.println(p);
            courses.add(this.getCourse(p));
            
        }
        return courses;
    }
}
