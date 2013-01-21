package tktl;


import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tktl.gstudies.repositories.CourseObjectRepository;
import tktl.gstudies.repositories.JDBCRepository;

@ActiveProfiles("test")
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/gstudies-servlet.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UnitTest {

    @Autowired
    private CourseObjectRepository courseRepository;
    
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
        assertNotNull(courseRepository.findAll());
    }
}
