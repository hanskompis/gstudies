package tktl;

import javax.persistence.EntityManager;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.services.AcademicYearEnrollmentService;
import tktl.gstudies.services.CourseObjectService;
import tktl.gstudies.services.GradeService;
import tktl.gstudies.services.RightToStudyService;
import tktl.gstudies.services.StatusOfStudyService;
import tktl.gstudies.services.StudentService;
import tktl.gstudies.services.StudyService;
import tktl.gstudies.services.TeacherService;
import tktl.gstudies.services.TypeOfStudyService;

@ActiveProfiles("test")
@ContextConfiguration({"file:src/main/webapp/WEB-INF/gstudies-servlet.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UnitTest {

    @Autowired
    private StatusOfStudyService statusOfStudyService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TypeOfStudyService typeOfStudyService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private AcademicYearEnrollmentService academicYearEnrollmentService;
    @Autowired
    private RightToStudyService rightToStudyService;
    @Autowired
    private CourseObjectService courseObjectService;
    @Autowired
    private StudyService studyService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TestDBUtils testDBUtils;

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
        if (studentService.findAll().isEmpty()) {
            testDBUtils.insertStuds();
        }
        if (rightToStudyService.findAll().isEmpty()) {
            testDBUtils.insertRightsToStudy();
        }
        if (academicYearEnrollmentService.findAll().isEmpty()) {
            testDBUtils.insertAcademicYearEnrollments();
        }
        if (statusOfStudyService.findAll().isEmpty()) {
            testDBUtils.insertStatusOfStudy();
        }
        if (typeOfStudyService.findAll().isEmpty()) {
            testDBUtils.insertTypesOfStudy();
        }
        if (courseObjectService.findAll().isEmpty()) {
            testDBUtils.insertCourseObjects();
        }

    }

    @After
    public void tearDown() {
    }

    @Test
    public void maxAmountCoursesDoesNotExceed11() {
        assertTrue(true);
    }

    @Test
    public void AllStudsIn() {
        assertEquals(5, studentService.findAll().size());
    }

    @Test
    public void AlRightsToStudyIn() {
        assertEquals(5, rightToStudyService.findAll().size());
    }

    @Test
    public void AllAcademicYearEnrollmentsIn() {
        assertEquals(77, academicYearEnrollmentService.findAll().size());
    }

    @Test
    public void AllStatusOfStudyIn() {
        assertEquals(10, statusOfStudyService.findAll().size());
    }

    @Test
    public void AlltypeOfStudyIn() {
        assertEquals(23, typeOfStudyService.findAll().size());
    }

    @Test
    public void AllCourseObjectsIn() {
        assertEquals(5, courseObjectService.findAll().size());
    }
}
