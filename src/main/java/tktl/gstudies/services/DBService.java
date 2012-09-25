package tktl.gstudies.services;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import org.h2.tools.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class DBService extends HttpServlet {

    private static boolean running = false;
    private String dataLocation = "/home/hkeijone/kanta/gkanta";

    @Override
    @PostConstruct
    public void init() throws ServletException {
        if (this.running) {
            System.out.println("RUUNNIINNGGGG");
            return;
        } else {
            this.running = true;
        }

        System.out.println("STARTING INIT SERVLET");

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
        createTableQueries.add("CREATE TABLE opettaja ( OPINTO INT NOT NULL, NIMLYH VARCHAR(255) NOT NULL)");//opettajat_insert
        createTableQueries.add("CREATE TABLE opinstat ( KOODI INT NOT NULL PRIMARY KEY, SELITE VARCHAR(255) NOT NULL)"); //opintostatus_insert
        createTableQueries.add("CREATE TABLE lkilm ( HLO INT NOT NULL, ALPVM DATE NOT NULL, PAATPVM DATE NOT NULL, TYYPPI VARCHAR(255) NOT NULL)"); //**lasnaolotiedot
        createTableQueries.add("CREATE TABLE suortyyp ( KOODI INT NOT NULL PRIMARY KEY, SELITE VARCHAR(255) NOT NULL)"); //suortyyp_insert
        createTableQueries.add("CREATE TABLE opinoik ( HLO INT NOT NULL, ALKPVM DATE NOT NULL, PAAAINE VARCHAR(255) NOT NULL)");//**opinto-oikeudet_insert
        createTableQueries.add("CREATE TABLE opinkohd ( OPINKOHD INT NOT NULL, TUNNISTE VARCHAR(255) NOT NULL, "
                + "NIMI VARCHAR(255) NOT NULL, TYYPPI VARCHAR(255) NOT NULL, LAJI VARCHAR(255) NOT NULL)");//opintokohteet_insert
        createTableQueries.add("CREATE TABLE opiskelija (HLO INT NOT NULL PRIMARY KEY, SUKUPUOLI VARCHAR(255) NOT NULL, SYNTAIK DATE NOT NULL, KIRJOILLETULO DATE NOT NULL)");//**opiskelijat_insert
        createTableQueries.add("CREATE TABLE opinto (OPINTO INT NOT NULL PRIMARY KEY, HLO INT NOT NULL, OPINKOHD INT NOT NULL, OPINOIK INT, "
                + "ENNPAAT INT, ORGANISAATIO INT, OPINSTAT INT NOT NULL, LAAJUUS NUMBER(5,2), GENOPIN INT NOT NULL, KIELI INT NOT NULL, LASKSUOROTT INT NOT NULL, "
                + "OPINKOHTTYYP INT NOT NULL, KAYTMUUT INT NOT NULL, TAPPVM DATE NOT NULL, SUORTYYP INT NOT NULL, SUORPVM DATE, KORPVM DATE, KIRJPVM DATE, "
                + "HYVPVM DATE, PISTMAAR INT, LASKPISTMAAR NUMBER(5,2), ECTSLAAJ NUMBER(5,2), ILMTAULPVM DATE, ENNALKPVM DATE, "
                + "ENNPAATPVM DATE, SUORULKMAA INT, TULSUOROTT INT, VIIMVOIMPVM DATE, SUORMUU INT, PROJEKTI VARCHAR(255), "
                + "AVAAJA INT NOT NULL, AVAUPVM DATE NOT NULL, MUUTTAJA INT NOT NULL, MUUTPVM DATE NOT NULL, OPISAIKOPIN INT, PAINKERRKA NUMBER(5,2) NOT NULL, "
                + "ERILPAAT INT, SUUNSUORLK INT, OPISPALHIST INT, LAAJOP NUMBER(5,2) NOT NULL,"
                + " ALKPERLAAJ INT NOT NULL)");//**opinnot_insert
        createTableQueries.add("CREATE TABLE arvosana (OPINTO INT NOT NULL, ARVSANARV VARCHAR(255) NOT NULL, SELITE VARCHAR(255) NOT NULL)");

        for (int i = 0; i < createTableQueries.size(); i++) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(createTableQueries.get(i));
            statement.close();
        }
        conn.close();
    }

    private void readFileIntoDb(File file, DataSource dataSource) throws Exception {
        // LUE VIELÄ MUUT KUIN OPETTAJAT, MUISTA MYÖS KONFFATA TIEDOSTON SIJAINTI
//        if ((!file.getName().contains("opintokohteet")) && (!file.getName().contains("opettajat"))
//                && (!file.getName().contains("opintostatus")) && (!file.getName().contains("suortyyp")) && (!file.getName().contains("opiskelijat_test"))) {
//            return;
//        }

//        if (!file.getName().contains("arvosanat_insert")) {
//            return;
//        }

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
                query = query.replace("to_date(", "");
                query = query.replace(", 'dd-mm-yyyy')", "");
                query = query.replace(", 'dd-mm-yyyy hh24:mi:ss')", "");

                query = removeTime(reverseQueryDates(query));
                queries.add(query);

                query = "";
            }
        }
        System.out.println("Total " + file.getName() + " queries read: " + queries.size());
        return queries;
    }

    private String reverseQueryDates(String query) {
        String re1 = "((?:(?:[0-2]?\\d{1})|(?:[3][01]{1}))[-:\\/.](?:[0]?[1-9]|[1][012])"
                + "[-:\\/.](?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])";	// YYYYMMDD 
        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(query);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String yyyymmdd1 = m.group(1);
            m.appendReplacement(sb, reverseDate(yyyymmdd1));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String reverseDate(String date) {
        String[] ret = date.split("-");
        Collections.reverse(Arrays.asList(ret));
        StringBuilder sb = new StringBuilder();
        sb.append(ret[0]).append("-").append(ret[1]).append("-").append(ret[2]);
        return sb.toString();
    }

    private static String removeTime(String query) {
        String re2 = "((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])(?::[0-5][0-9])?(?:\\s?(?:am|AM|pm|PM))?)";
        query = query.replaceAll(re2, "");
        return query;
    }

    public static void main(String[] args) throws Exception {
        // käynnistä palvelin osoitteeseen x
        final String[] arguments = new String[]{
            "-tcpPort", String.valueOf("12345"),
            "-tcpAllowOthers", ""}; //	need the extra empty string
        Server server = Server.createTcpServer(arguments).start();

    }
}
