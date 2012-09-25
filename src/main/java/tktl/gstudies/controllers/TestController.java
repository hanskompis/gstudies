
package tktl.gstudies.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tktl.gstudies.domain.CourseCatcher;
import tktl.gstudies.domain.Query;
import tktl.gstudies.repositories.TestRepository;
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
