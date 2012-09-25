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

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     *
     * @param tunniste of course
     * @param kirjpvm of course
     * @return List of students (HLO) regardless of whether they passed or not
     */
    public List studsEnrolledOnCourse(String tunniste, String kirjpvm) {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT opiskelija.HLO "
                + "from opiskelija, opinto, opinkohd WHERE "
                + "opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = "
                + "opinkohd.OPINKOHD AND opinkohd.TUNNISTE = \'" + tunniste + "\' AND opinto.KIRJPVM = \'" + kirjpvm + "\'");
    }

    public List query(String query) {
        return jdbcTemplate.queryForList(query);
    }

    /**
     *
     * @param HLO id of student
     * @return list(date, nameOfCourse) of courses student has passed on first
     * try
     */
    public List fetchSelectedCoursesOfStudent(Integer HLO) {
        String query = "SELECT DISTINCT opinkohd.TUNNISTE, opinto.SUORPVM from opinkohd, opinto, opiskelija,opinstat "
                + "WHERE opinto.OPINSTAT = opinstat.KOODI AND opinto.HLO = opiskelija.HLO "
                + "AND opinto.OPINKOHD = opinkohd.OPINKOHD AND opiskelija.HLO =" + HLO
                + "AND opinkohd.TUNNISTE IN ('58131','581325','582103','582104', '581305', "
                + "'581328', '58160','582203','582102','57049','581324','581326','582101'"
                + ",'581329') AND opinstat.KOODI IN (4) ORDER BY opinto.SUORPVM ASC";
        return this.jdbcTemplate.queryForList(query);
    }

    /**
     * @return data for graph
     */
    public List fetchData() {
        String query = "SELECT DISTINCT opiskelija.HLO, opinkohd.TUNNISTE, opinto.SUORPVM from "
                + "opinkohd, opinto, opiskelija,opinstat WHERE opinto.OPINSTAT = opinstat.KOODI "
                + "AND opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = opinkohd.OPINKOHD "
                + "AND opinkohd.TUNNISTE IN ('58131','581325','582103','582104', '581305', '581328', "
                + "'58160','582203','582102','57049','581324','581326','582101','581329') AND opinstat.KOODI IN (4)";
        return this.jdbcTemplate.queryForList(query);
    }

    /**
     *
     * @param tunniste of course
     * @return list of course instances
     */
    public List getCourseInstances(String tunniste) {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT  opettaja.NIMLYH, "
                + "opinto.SUORPVM, opinkohd.TUNNISTE FROM opinto, opinkohd, opettaja "
                + "WHERE opettaja.OPINTO = opinto.OPINTO AND "
                + "opinto.OPINKOHD = opinkohd.OPINKOHD AND "
                + "opinkohd.TUNNISTE = \'" + tunniste + "\' ORDER BY opinto.SUORPVM DESC");
    }

    public List getSelectedCoursesForInspection() {
        return this.jdbcTemplate.queryForList("SELECT NIMI, TUNNISTE FROM opinkohd WHERE "
                + "TUNNISTE IN ('58131','581325','582103','582104', '581305',"
                + " '581328', '58160','582203','582102','57049','581324','581326','582101','581329')");
    }
    
    /**
     * 
     * @param HLO student under inspection
     * @param dateString date as string from which the grades of courses are inspected
     * @return 
     */
    public List getGradesForStudentAfterDate(Integer HLO, String dateString){
        return this.jdbcTemplate.queryForList("SELECT DISTINCT ARVSANARV FROM opiskelija, arvosana, opinto, opinkohd "
                    + "WHERE opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = opinkohd.OPINKOHD AND "
                    + "opinto.OPINTO = arvosana.OPINTO AND opinto.HLO = \'" + HLO + "\' AND "
                    + "opinto.KIRJPVM > \'" + dateString + "\' AND arvosana.SELITE = 'Yleinen asteikko' "
                    + "AND arvosana.ARVSANARV IN (1,2,3,4,5)");
    }
}
