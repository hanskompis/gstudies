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
import tktl.gstudies.domain.*;
import tktl.gstudies.repositories.TestRepository;

@Service
@Qualifier("real")
public class GraphicsServiceImpl implements GraphicsService {

    @Autowired
    @Qualifier("real")
    private LineService lineService;
    @Autowired
    private TestRepository testRepository;
    private List<AbstractGraphicalObject> graphObjs;
    private int boxWidth = 50;
    private int boxHeight = 20;
    private int shiftBetweenColumns = 150;
  //  private List<Student> studs;

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
        for(int i  = 0; i < studs.size(); i++){
            
        }
        //this.lineService.getSumPathData();
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
               // System.out.println(toAdd);
                //TODO: kurssien sorttauskutsu tähän
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
        if(courses.size()>11){
            System.out.println("LIIAN PITKÄ: " + courses);
        }
        return courses;
    }

    /**
     *Lukee studsia
     * Lisää laatikot ja tekstit listiin ja palauttaa
     *Lisää boxcoordinateolioita coordsiin
     */
    private List<AbstractGraphicalObject> getNthSetOfNodes(List<Student> studs, int n, int offset) { //n kertoo, monesko sarake
        List<AbstractGraphicalObject> objs = new ArrayList();
        List<String> diffCourses = this.differentCoursesOnNthSet(n, studs); //eri kurssit tässä sarakkeessa
        this.lineService.getCoords().add(new ArrayList<BoxCoordinatesForLines>());//lisätään uusi lista boxcoordinateja varten
        for (int i = 1; i <= diffCourses.size(); i++) { 
            objs.add(new Rectangle("rect", ((offset + 1) * this.shiftBetweenColumns), (i * 50), this.boxWidth, this.boxHeight, 5));//lisätään suorakulmio
            objs.add(new Text(((offset + 1) * this.shiftBetweenColumns) + 25, (i * 50) + 10, diffCourses.get(i - 1))); //lisätään teksti
//            this.lineService.getCoords().get(n).add(new BoxCoordinatesForLines(diffCourses.get(i-1), ((offset + 1) * 100), (i * 50)+10 ,((offset + 1) * 150) , (i * 50)+10));
        //lisätään coordsiin sarakkeeseen n uusi boxcoordinateolio
            this.lineService.getCoords().get(n).add(new BoxCoordinatesForLines(diffCourses.get(i - 1), ((offset + 1) * this.shiftBetweenColumns), (i * 50) + 10, ((offset + 1) * this.shiftBetweenColumns) + 50, (i * 50) + 10));
        }
        return objs;
    }

    private List<String> differentCoursesOnNthSet(int n, List<Student> studs) {
        ArrayList<String> diffCourses = new ArrayList<String>();
        for (Student s : studs) {
            
            if ((n<s.getCourses().size())&&(!diffCourses.contains(s.getCourses().get(n).getCourse().name()))) {//KUSEEE!!
                diffCourses.add(s.getCourses().get(n).getCourse().name());
            }
        }
        //lineService.addCourseSet(diffCourses);
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
