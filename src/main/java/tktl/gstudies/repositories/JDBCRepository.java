package tktl.gstudies.repositories;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import tktl.gstudies.domain.CourseObject;
import tktl.gstudies.domain.StatusOfStudy;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.domain.TypeOfStudy;

/**
 * A class to conduct queries with plain SQL. Mainly needed for graphics service
 * but also for importing purposes.
 *
 * @author hkeijone
 */
@Repository
public class JDBCRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("importDataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List studsEnrolledOnCourse(String tunniste, String kirjpvm) {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT opiskelija.HLO "
                + "from opiskelija, opinto, opinkohd WHERE "
                + "opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = "
                + "opinkohd.OPINKOHD AND opinkohd.TUNNISTE = \'" + tunniste + "\' AND opinto.KIRJPVM = \'" + kirjpvm + "\'");
    }

    public List query(String query) {
        System.out.println(query);
        return jdbcTemplate.queryForList(query);
    }

    public List fetchSelectedCoursesOfStudent(Integer HLO) {
        String query = "SELECT DISTINCT opinkohd.TUNNISTE, opinto.SUORPVM from opinkohd, opinto, opiskelija,opinstat "
                + "WHERE opinto.OPINSTAT = opinstat.KOODI AND opinto.HLO = opiskelija.HLO "
                + "AND opinto.OPINKOHD = opinkohd.OPINKOHD AND opiskelija.HLO =" + HLO
                + "AND opinkohd.TUNNISTE IN ('58131','581325','582103','582104', '581305', "
                + "'581328', '58160','582203','582102','57049','581324','581326','582101'"
                + ",'581329') AND opinstat.KOODI IN (4) ORDER BY opinto.SUORPVM ASC";
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

    public List getGradesForStudentAfterDate(Integer HLO, String dateString) {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT ARVSANARV FROM opiskelija, arvosana, opinto, opinkohd "
                + "WHERE opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = opinkohd.OPINKOHD AND "
                + "opinto.OPINTO = arvosana.OPINTO AND opinto.HLO = \'" + HLO + "\' AND "
                + "opinto.KIRJPVM > \'" + dateString + "\' AND arvosana.SELITE = 'Yleinen asteikko' "
                + "AND arvosana.ARVSANARV IN (1,2,3,4,5)");
    }

    public List getGradesForStudentNMonthsSpan(Integer HLO, String dateString, int amountMonths) {
        return this.jdbcTemplate.queryForList("SELECT DISTINCT ARVSANARV FROM opiskelija, arvosana, opinto, opinkohd "
                + "WHERE opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = opinkohd.OPINKOHD AND "
                + "opinto.OPINTO = arvosana.OPINTO AND opinto.HLO = \'" + HLO + "\' AND "
                + "opinto.KIRJPVM > \'" + dateString + "\' AND opinto.KIRJPVM < \'"
                + this.AddMonthsToDateString(dateString, amountMonths) + "\' AND arvosana.SELITE = 'Yleinen asteikko' "
                + "AND arvosana.ARVSANARV IN (1,2,3,4,5)");
    }

    public List<StatusOfStudy> getStatusOfStudyObjects() {
        return this.jdbcTemplate.query("SELECT KOODI as code, SELITE as description FROM opinstat",
                new BeanPropertyRowMapper(StatusOfStudy.class));
    }

    public List<Stud> getStudentObjects() {
        return this.jdbcTemplate.query("SELECT HLO as studentId, OPISNRO as studentNumber, SUKUPUOLI as gender, "
                + "SYNTAIK AS dateOfBirth, KIRJOILLETULO AS dateOfEnrollment FROM opiskelija",
                new BeanPropertyRowMapper(Stud.class));
    }

    public void getStudentObjects(RowCallbackHandler rch) {
        this.jdbcTemplate.query("SELECT * from opiskelija", rch);
    }

//    public List<Stud> getStudentObjects() {
//        return this.jdbcTemplate.query("SELECT HLO as studentId, SUKUPUOLI as gender, "
//                + "SYNTAIK AS dateOfBirth, KIRJOILLETULO AS dateOfEnrollment FROM opiskelija",
//                new BeanPropertyRowMapper(Stud.class));
//    }
    public List<TypeOfStudy> getTypeOfStudyObjects() {
        return this.jdbcTemplate.query("SELECT KOODI as code, SELITE description FROM suortyyp",
                new BeanPropertyRowMapper(TypeOfStudy.class));
    }

    public List<CourseObject> getCourseObjectObjects() {
        return this.jdbcTemplate.query("SELECT OPINKOHD as objectId, TUNNISTE as courseId, NIMI as name, TYYPPI as type, LAJI as kind FROM opinkohd",
                new BeanPropertyRowMapper(CourseObject.class));
    }

    public List getAcademicEnrollmentData() {
        return this.jdbcTemplate.queryForList("SELECT * from lkilm");
    }

    public List getRightToStudyData() {
        return this.jdbcTemplate.queryForList("SELECT * FROM opinoik");
    }

    public List getStudyData() {
        return this.jdbcTemplate.queryForList("SELECT * FROM opinto");
    }

    public void getStudyData(RowCallbackHandler rch) {
        this.jdbcTemplate.query("SELECT * FROM opinto", rch);
    }

    public void getTeacherData(RowCallbackHandler rch) {
        this.jdbcTemplate.query("SELECT * FROM opettaja", rch);
    }

    public void getGradeObjects(RowCallbackHandler rch) {
        this.jdbcTemplate.query("SELECT * FROM arvosana ", rch);
    }

    /**
     * Fu*kiest method ever written in history of JAVA. Actually does the job
     * pretty good but looks terrible. If length of months need to be taken into
     * account, use this.
     */
//    private void AddmonthsToDateString(String dateString, int increment) {
//        int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
//
//        String[] subs = dateString.split("-");
//        int year = Integer.parseInt(subs[0]);
//        int month = Integer.parseInt(subs[1]);
//        int day = Integer.parseInt(subs[2]);
//        month = month + increment;
//        if (month > 12) {
//            year += month / 12;
//            month = month % 12;
//            if (month < 10) {
//                subs[1] = "0" + Integer.toString(month);
//            }
//            subs[1] = Integer.toString(month);
//            subs[0] = Integer.toString(year);
//        }
//        if (month < 10) {
//            subs[1] = "0" + Integer.toString(month);
//        } else {
//            subs[1] = Integer.toString(month);
//        }
//        if (day > daysOfMonth[--month]) {
//            subs[2] = Integer.toString(daysOfMonth[month]);
//        }
//        StringBuilder sb = new StringBuilder();
//        for (String s : subs) {
//            sb.append(s);
//            sb.append("-");
//        }
//        System.out.println(sb.toString().substring(0, sb.toString().length() - 1));
//    }
    private String AddMonthsToDateString(String dateString, int incrementInMonths) {
        Date baseDate = this.makeDate(dateString);
        long millis = baseDate.getTime();
        long inc = 31l * 24 * 60 * 60 * 1000 * incrementInMonths;
        millis = millis + inc;
        Date toReturn = new Date(millis);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(toReturn);
    }

    private Date makeDate(String dateString) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date toReturn = new Date();
        try {
            toReturn = df.parse(dateString);
            return toReturn;
        } catch (ParseException e) {
            System.out.println("KUSI!");
        }
        return toReturn;
    }
}
