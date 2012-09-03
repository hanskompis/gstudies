/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tktl.gstudies.repositories;

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
        return jdbcTemplate.queryForInt("SELECT COUNT (*) FROM opinkohd");
    }
}
