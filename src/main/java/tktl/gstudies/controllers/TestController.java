package tktl.gstudies.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tktl.gstudies.importClasses.ImportService;
import tktl.gstudies.responseobjs.CourseCatcher;
import tktl.gstudies.responseobjs.Query;
import tktl.gstudies.repositories.JDBCRepository;
import tktl.gstudies.responseobjs.CourseStatsResponseObj;
import tktl.gstudies.services.StatisticService;
import tktl.gstudies.services.StatisticServiceImpl;
/**
 * Controller for getting course information. GUI will maybe not be implemented at all.
 * @author hkeijone
 */
@Controller
public class TestController {

    @Autowired
    private JDBCRepository testRepository;
    @Autowired
    private StatisticService statisticService;


    @RequestMapping(method = RequestMethod.POST, value = "query", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List query(@RequestBody Query q) {
        return this.testRepository.query(q.getQueryString());
    }

    @RequestMapping(method = RequestMethod.GET, value = "coursesforinspection", produces = "application/json")
    @ResponseBody
    public List coursesForInspection() {
        return this.testRepository.getSelectedCoursesForInspection();
    }

    @RequestMapping(method = RequestMethod.POST, value = "courseinstances",
    produces = "application/json", consumes = "application/json")
    @ResponseBody
    public List courseinstances(@RequestBody CourseCatcher c) {
        return this.testRepository.getCourseInstances(c.getTunniste());
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "test", produces = "application/json")
    @ResponseBody
    public CourseStatsResponseObj test() {
        return statisticService.getData("2010-12-16", "582206");
    }
}
