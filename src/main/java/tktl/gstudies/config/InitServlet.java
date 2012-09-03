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

        System.out.println("STARTING INIT SERVLET");

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
        System.out.println("LOAD DB");
        // creates db and loads data into it

        if (dataSource == null) {
            System.out.println("FUU");
        }

        initTables(dataSource);


        File dataDir = new File(dataLocation);
        for (File file : dataDir.listFiles()) {
            System.out.println("FILE: " + file.getName());
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

        ArrayList<String> createTableQueries = new ArrayList<String>();
        createTableQueries.add("CREATE TABLE opettaja ( ID INT NOT NULL, NIMI VARCHAR(255) NOT NULL)");//opettajat_insert
        createTableQueries.add("CREATE TABLE opinstat ( KOODI INT NOT NULL, SELITE VARCHAR(255) NOT NULL)"); //opintostatus_insert
        createTableQueries.add("CREATE TABLE lkilm ( HLO INT NOT NULL, ALPVM DATE NOT NULL, PAATPVM DATE NOT NULL, TYYPPI VARCHAR(255) NOT NULL)"); //**lasnaolotiedot
        createTableQueries.add("CREATE TABLE suortyyp ( KOODI INT NOT NULL, SELITE VARCHAR(255) NOT NULL)"); //suortyyp_insert
        createTableQueries.add("CREATE TABLE opinoik ( HLO INT NOT NULL, ALKPVM DATE NOT NULL, PAAAINE VARCHAR(255) NOT NULL)");//**opinto-oikeudet_insert
        createTableQueries.add("CREATE TABLE opinkohd ( OPINKOHD INT NOT NULL, TUNNISTE VARCHAR(255) NOT NULL, "
                + "NIMI VARCHAR(255) NOT NULL, TYYPPI VARCHAR(255) NOT NULL, LAJI VARCHAR(255) NOT NULL)");//opintokohteet_insert
        createTableQueries.add("CREATE TABLE opinto (OPINTO INT NOT NULL, HLO INT NOT NULL, OPINKOHD INT NOT NULL, OPINOIK INT, "
                + "ENNPAAT INT, ORGANISAATIO INT, OPINSTAT INT NOT NULL, LAAJUUS NUMBER(5,2), GENOPIN INT NOT NULL, KIELI INT NOT NULL, LASKSUOROTT INT NOT NULL, "
                + "OPINKOHTTYYP INT NOT NULL, KAYTMUUT INT NOT NULL, TAPPVM DATE NOT NULL, SUORTYYP INT NOT NULL, SUORPVM DATE, KORPVM DATE, KIRJPVM DATE, "
                + "HYVPVM DATE, PISTMAAR INT, LASKPISTMAAR NUMBER(5,2), ECTSLAAJ NUMBER(5,2), ILMTAULPVM DATE, ENNALKPVM DATE, "
                + "ENNPAATPVM DATE, SUORULKMAA INT, TULSUOROTT INT, VIIMVOIMPVM DATE, SUORMUU INT, PROJEKTI VARCHAR(255), "
                + "AVAAJA INT NOT NULL, AVAUPVM DATE NOT NULL, MUUTTAJA INT NOT NULL, MUUTPVM DATE NOT NULL, OPISAIKOPIN INT, PAINKERRKA NUMBER(5,2) NOT NULL, "
                + "ERILPAAT INT, SUUNSUORLK INT, OPISPALHIST INT, LAAJOP NUMBER(5,2) NOT NULL, ALKPERLAAJ INT NOT NULL)");//**opinnot_insert
        createTableQueries.add("CREATE TABLE opiskelija (HLO INT NOT NULL, SUKUPUOLI VARCHAR(255) NOT NULL, SYNTAIK DATE NOT NULL, KIRJOILLETULO DATE NOT NULL)");//**opiskelijat_insert
        
        for (int i = 0; i < createTableQueries.size(); i++) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(createTableQueries.get(i));
            statement.close();
        }
    }

    private void readFileIntoDb(File file, DataSource dataSource) throws Exception {
        // LUE VIELÄ MUUT KUIN OPETTAJAT, MUISTA MYÖS KONFFATA TIEDOSTON SIJAINTI
        if ((!file.getName().contains("opintokohteet")) && (!file.getName().contains("opettajat")) 
                && (!file.getName().contains("opintostatus")) && (!file.getName().contains("suortyyp"))) {
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
        final String[] arguments = new String[]{
            "-tcpPort", String.valueOf("12345"),
            "-tcpAllowOthers", ""}; //	need the extra empty string

        Server server = Server.createTcpServer(arguments).start();

        // avaa yhteys palvelimeen, luo datasource

        // loadDb datasourcella

        // nyt palvelin on käynnissä

    }
}
