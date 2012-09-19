
package tktl.gstudies.repositories;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tktl.gstudies.domain.CourseInstance;
import tktl.gstudies.domain.Student;


@Repository
public class TestRepository {

    private final String queryForStuds = "SELECT DISTINCT opiskelija.HLO FROM opiskelija,"
            + " opinto, opinkohd, opinstat WHERE opinto.OPINSTAT = opinstat.KOODI "
            + "AND opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = opinkohd.OPINKOHD "
            + "AND opinkohd.TUNNISTE IN ('58131','581325','582103','582104', '581305', "
            + "'581328', '58160','582203','582102','57049','581324','581326','582101','581329') "
            + "AND opinstat.KOODI IN (4,7,9)";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

//    public int count() {
//        return this.jdbcTemplate.queryForInt("SELECT COUNT (*) opiskelija.HLO "
//                + "from opiskelija, opinto, opinkohd WHERE opinto.HLO = opiskelija.HLO "
//                + "AND opinto.OPINKOHD = opinkohd.OPINKOHD AND "
//                + "opinkohd.TUNNISTE = '58131' AND opinto.KIRJPVM = '2009-05-19'");
//    }
    
    public List studsOnCourse(String tunniste, String kirjpvm){
        return this.jdbcTemplate.queryForList("SELECT DISTINCT opiskelija.HLO "
                + "from opiskelija, opinto, opinkohd WHERE "
                + "opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = "
                + "opinkohd.OPINKOHD AND opinkohd.TUNNISTE = \'" + tunniste + "\' AND opinto.KIRJPVM = \'" + kirjpvm + "\'");
    }

    public List list() {
        return jdbcTemplate.queryForList("SELECT DISTINCT opinkohd.NIMI FROM opinkohd, opinto, opettaja "
                + "WHERE opinkohd.OPINKOHD = opinto.OPINKOHD AND opettaja.OPINTO = opinto.OPINTO AND opettaja.NIMLYH LIKE 'Luukkainen M%'");
        //return jdbcTemplate.queryForList("SELECT OPINTO FROM opettaja, opinto WHERE opettaja.OPINTO = opinto NIMLYH LIKE 'Arffman%'");
    }

    public List query(String query) {
        return jdbcTemplate.queryForList(query);
    }

    public List fetchStudents() {
        return this.jdbcTemplate.query(this.queryForStuds, new BeanPropertyRowMapper(Student.class));
    }

    public List fetchCourses(Integer HLO) {
        String query = "SELECT DISTINCT opinkohd.TUNNISTE, opinto.SUORPVM from opinkohd, opinto, opiskelija,opinstat "
                + "WHERE opinto.OPINSTAT = opinstat.KOODI AND opinto.HLO = opiskelija.HLO "
                + "AND opinto.OPINKOHD = opinkohd.OPINKOHD AND opiskelija.HLO =" + HLO
                + "AND opinkohd.TUNNISTE IN ('58131','581325','582103','582104', '581305', "
                + "'581328', '58160','582203','582102','57049','581324','581326','582101'"
                + ",'581329') AND opinstat.KOODI IN (4,7) ORDER BY opinto.SUORPVM ASC";
        return this.jdbcTemplate.queryForList(query);
    }

    public List fetchData() {
        String query = "SELECT DISTINCT opiskelija.HLO, opinkohd.TUNNISTE, opinto.SUORPVM from "
                + "opinkohd, opinto, opiskelija,opinstat WHERE opinto.OPINSTAT = opinstat.KOODI "
                + "AND opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = opinkohd.OPINKOHD "
                + "AND opinkohd.TUNNISTE IN ('58131','581325','582103','582104', '581305', '581328', "
                + "'58160','582203','582102','57049','581324','581326','582101','581329') AND opinstat.KOODI IN (4)";
        return this.jdbcTemplate.queryForList(query);
    }
    
    public void studentScoreAverageAfterCourse(String tunniste, String kirjpvm){
        List<Map> studsOnCourse = this.studsOnCourse(tunniste, kirjpvm);
        int sum = 0;
        int amountCourses = 0;
        for(Map row : studsOnCourse){
           // System.out.println(row.toString());
            Integer HLO = (Integer) row.get("HLO");
           // System.out.println(HLO);
            List<Map<String,Object>> grades = this.jdbcTemplate.queryForList("SELECT DISTINCT ARVSANARV FROM opiskelija, arvosana, opinto, opinkohd "
                    + "WHERE opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = opinkohd.OPINKOHD AND "
                    + "opinto.OPINTO = arvosana.OPINTO AND opinto.HLO = \'"+HLO+"\' AND "
                    + "opinto.KIRJPVM > \'"+kirjpvm+"\' AND arvosana.SELITE = 'Yleinen asteikko' "
                    + "AND arvosana.ARVSANARV IN (1,2,3,4,5)");
          sum += this.castAsIntAndSumGrades(grades);
            amountCourses += grades.size();
          //  System.out.println(grades.toString());
        }
        System.out.println("LKMCourses: "+amountCourses+" sumCourses: "+sum+"KA: " + ((double)sum/amountCourses));
        
        
    }
    
    private int castAsIntAndSumGrades(List<Map<String,Object>> grades){
        int sum = 0;
        for(Map m : grades){
            Integer i = Integer.parseInt((String)m.get("ARVSANARV"));
            sum += i.intValue();
        }
        return sum;
        
    }
    
    public static void main(String[] args) {
        TestRepository tr = new TestRepository();
       // tr.studentScoreAverageAfterCourse("58131", "2009-05-19");
        tr.studsOnCourse("58131", "2009-05-19");
    }
}
