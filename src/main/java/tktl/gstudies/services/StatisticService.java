package tktl.gstudies.services;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.repositories.JDBCRepository;
import tktl.gstudies.responseobjs.CourseStatResponse;

@Service
public class StatisticService {
    
    @Autowired
    private JDBCRepository testRepository;

    public CourseStatResponse CourseStats(String tunniste, String kirjpvm) {
        List<Map> studsOnCourse = this.testRepository.studsEnrolledOnCourse(tunniste, kirjpvm);
        int studsEnrolled = studsOnCourse.size(); 
        double gradeAverageSixmonths = this.getAverageGrade(studsOnCourse, 6, kirjpvm);
        double gradeAverageTwelveMonths = this.getAverageGrade(studsOnCourse, 12, kirjpvm);
        //TODO: simplify motherfucker!
        CourseStatResponse resp = new CourseStatResponse();
        resp.setAverageGradeSixMonths(gradeAverageSixmonths);
        resp.setAverageGradetwelvemonths(gradeAverageTwelveMonths);
        resp.setStudsEnrolled(studsEnrolled);
        return resp;
    }
    
    private int castAsIntAndSumGrades(List<Map<String, Object>> grades) {
        int sum = 0;
        for (Map m : grades) {
            Integer i = Integer.parseInt((String) m.get("ARVSANARV"));
            sum += i.intValue();
        }
        return sum;
    }
    
    private double getAverageGrade(List<Map> studsOnCourse, int timeSpanInMonths, String kirjpvm) {
        int sum = 0;
        int amountCourses = 0;
        for (Map row : studsOnCourse) {
            Integer HLO = (Integer) row.get("HLO");
            List<Map<String, Object>> grades = this.testRepository.getGradesForStudentNMonthsSpan(HLO, kirjpvm, timeSpanInMonths);
//            List<Map<String, Object>> grades = this.testRepository.getGradesForStudentAfterDate(HLO, kirjpvm);
            sum += this.castAsIntAndSumGrades(grades);
            amountCourses += grades.size();
        }
        double ret = (double) sum / amountCourses;
        System.out.println(sum + " " + amountCourses);
        return (double) sum / amountCourses;
    }
}
