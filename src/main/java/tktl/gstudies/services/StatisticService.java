package tktl.gstudies.services;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.CourseGradeResponse;
import tktl.gstudies.repositories.TestRepository;

@Service
public class StatisticService {
    
    @Autowired
    private TestRepository testRepository;

    //useless method at this point
    public CourseGradeResponse studentScoreAverageAfterCourse(String tunniste, String kirjpvm) {
        List<Map> studsOnCourse = this.testRepository.studsEnrolledOnCourse(tunniste, kirjpvm);
        int sum = 0;
        int amountCourses = 0;
        for (Map row : studsOnCourse) {
            Integer HLO = (Integer) row.get("HLO");
            List<Map<String, Object>> grades = this.testRepository.getGradesForStudentAfterDate(HLO, kirjpvm);
            sum += this.castAsIntAndSumGrades(grades);
            amountCourses += grades.size();
        }
        return new CourseGradeResponse(amountCourses, ((double) sum / amountCourses));       
    }

    private int castAsIntAndSumGrades(List<Map<String, Object>> grades) {
        int sum = 0;
        for (Map m : grades) {
            Integer i = Integer.parseInt((String) m.get("ARVSANARV"));
            sum += i.intValue();
        }
        return sum;
    }
    

    

}
