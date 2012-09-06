/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tktl.gstudies.repositories;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author avihavai
 */
@Repository
public class TestRepository {
    
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public int count() {
        //return this.jdbcTemplate.queryForList("SELECT nimi FROM opinkohd");
        return jdbcTemplate.queryForInt("SELECT COUNT (*) FROM opinto, opinstat WHERE opinto.OPINSTAT = opinstat.KOODI AND KOODI = 9");
        //jdbcTemplate.execute("SELECT (*) FROM opinstat");
    }
}
