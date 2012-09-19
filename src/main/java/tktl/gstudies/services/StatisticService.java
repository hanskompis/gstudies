package tktl.gstudies.services;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.repositories.TestRepository;

@Service
public class StatisticService {
    
    @Autowired
    private TestRepository testRepository;

    public void studentScoreAverageAfterCourse(String tunniste, String kirjpvm) {
        List<Map> studsOnCourse = this.testRepository.studsOnCourse(tunniste, kirjpvm);
        int sum = 0;
        int amountCourses = 0;
        for (Map row : studsOnCourse) {
            // System.out.println(row.toString());
            Integer HLO = (Integer) row.get("HLO");
            // System.out.println(HLO);
            List<Map<String, Object>> grades = this.testRepository.query("SELECT DISTINCT ARVSANARV FROM opiskelija, arvosana, opinto, opinkohd "
                    + "WHERE opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = opinkohd.OPINKOHD AND "
                    + "opinto.OPINTO = arvosana.OPINTO AND opinto.HLO = \'" + HLO + "\' AND "
                    + "opinto.KIRJPVM > \'" + kirjpvm + "\' AND arvosana.SELITE = 'Yleinen asteikko' "
                    + "AND arvosana.ARVSANARV IN (1,2,3,4,5)");
            sum += this.castAsIntAndSumGrades(grades);
            amountCourses += grades.size();
        }
        System.out.println("LKMCourses: " + amountCourses + " sumCourses: " + sum + "KA: " + ((double) sum / amountCourses));
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
