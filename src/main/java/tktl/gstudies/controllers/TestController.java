
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
import tktl.gstudies.services.StatisticService;

/**
 *
 * @author avihavai
 */
@Controller
public class TestController {

    @Autowired
    private JDBCRepository testRepository;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private ImportService importService;

    @RequestMapping("/test")
    @ResponseBody
    public void process() {
        this.importService.importStudentObjects();
    }

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
}
