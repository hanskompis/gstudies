/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tktl.gstudies.config;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import org.h2.tools.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author avihavai
 */
public class InitServlet extends HttpServlet {

    private String dataLocation;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        dataLocation = config.getInitParameter("gstudies-data-location");

        
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        
        try {
            loadDb(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void loadDb(DataSource dataSource) throws Exception {
        // creates db and loads data into it

        if (dataSource == null) {
            System.out.println("FUU");
        }

        initTables(dataSource);


        File dataDir = new File(dataLocation);
        for (File file : dataDir.listFiles()) {
            if (!file.getName().endsWith(".sql")) {
                continue;
            }

            System.out.println("Reading data from " + file.getAbsolutePath());
            readFileIntoDb(file, dataSource);
        }
    }

    private void initTables(DataSource dataSource) throws Exception {
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(true);

        String createTableQuery = "CREATE TABLE opettaja ( ID INT NOT NULL, NIMI VARCHAR(255) NOT NULL)";

        // LUO VIELÄ MUUT TAULUT

        Statement statement = conn.createStatement();
        statement.executeUpdate(createTableQuery);
        statement.close();

    }

    private void readFileIntoDb(File file, DataSource dataSource) throws Exception {
        // LUE VIELÄ MUUT KUIN OPETTAJAT, MUISTA MYÖS KONFFATA TIEDOSTON SIJAINTI
        if (!file.getName().contains("opettajat")) {
            return;
        }
        
        List<String> queries = readQueries(file);
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();

        for (String query : queries) {
            statement.addBatch(query);
        }
        
        statement.executeBatch();
        statement.close();
        connection.close();
    }

    private List<String> readQueries(File file) throws Exception {
        List<String> queries = new ArrayList<String>();

        String query = "";

        Scanner sc = new Scanner(file, "iso-8859-1");

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            line = line.trim();
            if (line.trim().isEmpty()) {
                continue;
            }
            
            query += " " + line;

            if (query.contains(";")) {
                query = query.trim();
                queries.add(query);
                
                query = "";
            }
        }

        System.out.println("Total " + file.getName() + " queries read: " + queries.size());
        return queries;
    }
    
    public static void main(String[] args) throws Exception {
        // käynnistä palvelin osoitteeseen x
        final String[] arguments = new String[] {
                "-tcpPort", String.valueOf("12345"),
                "-tcpAllowOthers", "" }; //	need the extra empty string
        
        Server server = Server.createTcpServer(arguments).start();
        
        // avaa yhteys palvelimeen, luo datasource
        
        // loadDb datasourcella
        
        // nyt palvelin on käynnissä
        
    }
}
