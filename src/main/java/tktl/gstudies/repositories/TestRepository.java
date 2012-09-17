
package tktl.gstudies.repositories;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tktl.gstudies.domain.Student;

/**
 *
 * @author avihavai
 */
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

    public int count() {
        return this.jdbcTemplate.queryForInt("SELECT DISTINCT COUNT (*) FROM opinkohd, opinto, opettaja "
                + "WHERE opinkohd.OPINKOHD = opinto.OPINKOHD AND opettaja.OPINTO = opinto.OPINTO AND opettaja.NIMLYH LIKE 'Luukkainen Matti%'");
        //return jdbcTemplate.queryForInt("SELECT COUNT (*) FROM opinto, opinstat WHERE opinto.OPINSTAT = opinstat.KOODI AND KOODI = 9");
        //jdbcTemplate.execute("SELECT (*) FROM opinstat");
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
//                String query = "SELECT DISTINCT opiskelija.HLO, opinkohd.TUNNISTE, opinto.SUORPVM from "
//                + "opinkohd, opinto, opiskelija,opinstat WHERE opinto.OPINSTAT = opinstat.KOODI "
//                + "AND opinto.HLO = opiskelija.HLO AND opinto.OPINKOHD = opinkohd.OPINKOHD "
//                + "AND opinkohd.TUNNISTE IN ('58131','581325','582103','582104', '581305', '581328', "
//                + "'58160','582203','582102','57049','581324','581326','582101','581329') "
//                        + "AND opinstat.KOODI IN (4) AND opiskelija.HLO IN (32227298,57726954,72814571,53405679,57915379,50360308)";
        return this.jdbcTemplate.queryForList(query);
    }
}
