/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tktl.gstudies.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tktl.gstudies.domain.CourseInstance;
import tktl.gstudies.domain.CourseCatcher;
import tktl.gstudies.domain.CourseGradeResponse;
import tktl.gstudies.domain.Query;
import tktl.gstudies.repositories.TestRepository;
import tktl.gstudies.services.GraphicsServiceImpl;
import tktl.gstudies.services.StatisticService;

/**
 *
 * @author avihavai
 */
@Controller
public class TestController {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private StatisticService statisticService;

    @RequestMapping("/test")
    @ResponseBody
    public List process() {
        return this.testRepository.fetchData();
    }

    @RequestMapping("/test2")
    @ResponseBody
    public String process2() {

        return "" + testRepository.list();
    }

    @RequestMapping("/test3")
    @ResponseBody
    public List process3() {

        return this.statisticService.getCoursesForInspection();
    }

    @RequestMapping(method = RequestMethod.POST, value = "query", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List query(@RequestBody Query q) {
        List toReturn = this.testRepository.query(q.getQueryString());
        return toReturn;
//        return this.testRepository.query(q.getQueryString());
    }

//    @RequestMapping(method = RequestMethod.POST, value = "studsoncourse", consumes = "application/json", produces = "application/json")
//    @ResponseBody
//    public List studsoncourse(@RequestBody CourseCatcher c) {
//        return this.testRepository.studsOnCourse(c.getTunniste(), c.getSuorpvm());
//    }

    @RequestMapping(method = RequestMethod.GET, value = "coursesforinspection", produces = "application/json")
    @ResponseBody
    public List coursesForInspection() {
        return this.statisticService.getCoursesForInspection();
    }

    @RequestMapping(method = RequestMethod.POST, value = "courseinstances", 
            produces = "application/json",  consumes = "application/json")
    @ResponseBody
    public List courseinstances(@RequestBody CourseCatcher c) {
        return this.statisticService.getCourseInstances(c.getTunniste());
    }
}
