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

    public CourseGradeResponse studentScoreAverageAfterCourse(String tunniste, String kirjpvm) {
        List<Map> studsOnCourse = this.testRepository.studsOnCourse(tunniste, kirjpvm);
        int sum = 0;
        int amountCourses = 0;
        for (Map row : studsOnCourse) {
            Integer HLO = (Integer) row.get("HLO");
            List<Map<String, Object>> grades = this.testRepository.query("SELECT DISTINCT ARVSANARV FROM opiskelija, arvosana, opinto, opinkohd "
                    + "WHERE opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = opinkohd.OPINKOHD AND "
                    + "opinto.OPINTO = arvosana.OPINTO AND opinto.HLO = \'" + HLO + "\' AND "
                    + "opinto.KIRJPVM > \'" + kirjpvm + "\' AND arvosana.SELITE = 'Yleinen asteikko' "
                    + "AND arvosana.ARVSANARV IN (1,2,3,4,5)");
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
    
    public List getCoursesForInspection(){
        return this.testRepository.query("SELECT NIMI, TUNNISTE FROM opinkohd WHERE "
                + "TUNNISTE IN ('58131','581325','582103','582104', '581305',"
                + " '581328', '58160','582203','582102','57049','581324','581326','582101','581329')");
    }
    
    public List getCourseInstances(String tunniste){
        return  this.testRepository.query("SELECT DISTINCT  opettaja.NIMLYH, "
                + "opinto.SUORPVM FROM opinto, opinkohd, opettaja "
                + "WHERE opettaja.OPINTO = opinto.OPINTO AND "
                + "opinto.OPINKOHD = opinkohd.OPINKOHD AND "
                + "opinkohd.TUNNISTE = \'"+tunniste+"\' ORDER BY opinto.SUORPVM DESC");
    }
}
