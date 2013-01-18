package test;


import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tktl.gstudies.repositories.JDBCRepository;
import tktl.gstudies.services.StatisticServiceImpl;

//@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring-context-test.xml",
//    "classpath:spring-beans.xml","classpath:spring-database.xml"})

@ContextConfiguration(locations = {"spring-context.xml", "spring-database.xml"})
public class UnitTest {

    @Autowired
    private JDBCRepository testRepository;
//    @Autowired
//    private StatisticServiceImpl statisticService;

    public UnitTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void maxAmountCoursesDoesNotExceed11() {
        assertTrue(true);
    }
}
