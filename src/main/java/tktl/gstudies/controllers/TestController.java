package tktl.gstudies.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tktl.gstudies.exceptions.GstudiesException;

import tktl.gstudies.responseobjs.Query;
import tktl.gstudies.repositories.JDBCRepository;
import tktl.gstudies.responseobjs.CourseAndTimeSpanCatcher;
import tktl.gstudies.responseobjs.CoursePairCatcher;
import tktl.gstudies.responseobjs.CourseStats;
import tktl.gstudies.responseobjs.CourseStatsResponseObj;
import tktl.gstudies.responseobjs.JSONMessage;
import tktl.gstudies.responseobjs.JSONMessageCode;
import tktl.gstudies.services.CoursePairStatsService;
import tktl.gstudies.services.StatisticService;

/**
 * Controller for getting course information. GUI will maybe not be implemented
 * at all.
 *
 * @author hkeijone
 */
@Controller
public class TestController {

    @Autowired
    private JDBCRepository testRepository;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private CoursePairStatsService coursePairStatsService;

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

    @RequestMapping(method = RequestMethod.POST, value = "course",
    produces = "application/json", consumes = "application/json")
    @ResponseBody
    public List<CourseStatsResponseObj> courseinstances(@RequestBody CourseAndTimeSpanCatcher catsc) {
        System.out.println(catsc.getCourseId()+" "+ catsc.getStartYear()+" "+ catsc.getEndYear());
        return statisticService.getAllDataFromCourseBetweenYears(catsc.getCourseId(), catsc.getStartYear(), catsc.getEndYear());
    }

//    @RequestMapping(method = RequestMethod.GET, value = "test", produces = "application/json")
//    @ResponseBody
//    public CourseStats test() {
//        return coursePairStatsService.getCourseStatsForCoursePair("58131", 2010, "582206");
//
//    }

    @RequestMapping(method = RequestMethod.POST, value = "coursepair", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public CourseStats coursepair(@RequestBody CoursePairCatcher cpc) {
        System.out.println(cpc.getFirstCourseId() + " " + cpc.getFirstCourseYear() + " " + cpc.getSecondCourseId() + " " + cpc.getSecondCourseYear());
        return coursePairStatsService.getCourseStatsForCoursePair(cpc.getFirstCourseId(), cpc.getFirstCourseYear(), cpc.getSecondCourseId(), cpc.getSecondCourseYear());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public JSONMessage handleException(Exception exception) {

        System.out.println("handleException");
        System.out.println(exception.toString());
        exception.printStackTrace();
        return new JSONMessage(JSONMessageCode.INTERNAL_ERROR, "Internal error.");
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public JSONMessage handleHttpMessageNotReadableException(IndexOutOfBoundsException exception) {
        return new JSONMessage(JSONMessageCode.INTERNAL_ERROR, "IndexOutOfBound");
    }

    @ExceptionHandler(GstudiesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public JSONMessage handleZeroDatesException(GstudiesException exception) {
        System.out.println(exception.getMessage());
        return new JSONMessage(JSONMessageCode.INTERNAL_ERROR, exception.getMessage());
    }
}
