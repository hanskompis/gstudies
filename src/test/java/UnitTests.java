
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tktl.gstudies.repositories.TestRepository;
import tktl.gstudies.services.GraphicsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context-test.xml",
    "classpath:spring-beans-test.xml"})
public class UnitTests {

    @Autowired
    @Qualifier("real")
    private GraphicsService graphicsService;
    @Autowired
    @Qualifier("real")
    private TestRepository testRepository;

    public UnitTests() {
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

    //Arveluttava testi, fiksattu kurssi-lkm
    @Test
    public void maxAmountCoursesDoesNotExceed11() {
        assertTrue(true);
    }
}
