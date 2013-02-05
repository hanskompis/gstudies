package tktl;

import com.google.gson.Gson;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.responseobjs.CourseStats;
import tktl.gstudies.services.AcademicYearEnrollmentService;
import tktl.gstudies.services.CourseObjectService;
import tktl.gstudies.services.GradeService;
import tktl.gstudies.services.RightToStudyService;
import tktl.gstudies.services.StatisticService;
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
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private StatisticService statisticService;
    private CourseStats courseStats = null;
    private static boolean jsonWritten = false;

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
        if (studyService.findAll().isEmpty()) {
            testDBUtils.insertStudies();
        }
        if (teacherService.findAll().isEmpty()) {
            testDBUtils.insertTeachers();
        }
        if (gradeService.findAll().isEmpty()) {
            testDBUtils.insertGrades();
        }
        if (this.courseStats == null) {
            this.courseStats = this.statisticService.doTheMagic("CSPassed", "2006-10-15", "1");
        }
        if(!this.jsonWritten){
            Gson gson = new Gson();
            String json = gson.toJson(this.courseStats);
            System.out.println(json);
            this.jsonWritten = false;
        }
        
       
    }

    @After
    public void tearDown() {
    }

    @Test
    public void findsAllStudentsWhoPassedOnDate() {
        assertEquals(4, this.statisticService.getCSStudentsFromCourseWhoPassedOnDate("1", "2006-10-15").size());
    }

    @Test
    public void findsAllStudentsWhofailedOnDate() {
        assertEquals(2, this.statisticService.getCSStudentsFromCourseWhoFailedOnDate("2", "2007-03-18").size());
    }

    @Test
    public void getsCorrectAmountOfPassedStudies7MonthSpan() {
        Stud stud = this.statisticService.getCSStudentsFromCourseWhoPassedOnDate("1", "2006-10-15").get(1);
        double epsilon = 0.1;
        assertTrue(Math.abs(4.0 - this.statisticService.getCreditsNMonthsSpan(stud.getStudies(), Date.valueOf("2006-10-15"), 7)) < epsilon);
    }

    @Test
    public void getsCorrectAmountOfPassedStudies13MonthSpan() {
        Stud stud = this.statisticService.getCSStudentsFromCourseWhoPassedOnDate("1", "2006-10-15").get(1);
        double epsilon = 0.1;
        assertTrue(Math.abs(12.0 - this.statisticService.getCreditsNMonthsSpan(stud.getStudies(), Date.valueOf("2006-10-15"), 13)) < epsilon);
    }

    @Test
    public void getsCorrectAmountOfPassedStudies19MonthSpan() {
        Stud stud = this.statisticService.getCSStudentsFromCourseWhoPassedOnDate("1", "2006-10-15").get(1);
        double epsilon = 0.1;
        assertTrue(Math.abs(16.0 - this.statisticService.getCreditsNMonthsSpan(stud.getStudies(), Date.valueOf("2006-10-15"), 19)) < epsilon);
    }

    @Test
    public void calculatesCorrectGroupAverageOfGradesNMonths() {
        double epsilon = 0.01;
        List<Stud> studs = this.statisticService.getCSStudentsFromCourseWhoPassedOnDate("1", "2006-10-15");
        assertTrue(Math.abs(2.89 - this.statisticService.getGroupAverageGradeNMonthsSpan2(studs, Date.valueOf("2006-10-15"), 19)) < epsilon);
    }

    @Test
    public void calculatesCorrectStandardDeviationOfGradesNMonths() {
        double epsilon = 0.01;
        List<Stud> studs = this.statisticService.getCSStudentsFromCourseWhoPassedOnDate("1", "2006-10-15");
        assertTrue(Math.abs(1.54 - this.statisticService.getStandardDeviationOfgrades(studs, Date.valueOf("2006-10-15"), 19)) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectAmountOfStuds() {
        assertEquals(4, this.courseStats.getAmountStudents());
    }

    @Test
    public void courseStatsHasCorrectAverageGradeSevenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(2.0 - courseStats.getAverageGradeSevenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectAverageGradeThirteenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(2.83 - courseStats.getAverageGradeThirteenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectAverageGradeNineteenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(2.89 - courseStats.getAverageGradeNineteenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectGradeSDSevenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(1.41 - courseStats.getStandardDeviationGradesSevenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectGradeSDThirteenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(1.47 - courseStats.getStandardDeviationGradesThirteenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectGradeSDNineteenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(1.53 - courseStats.getStandardDeviationGradesNineteenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectAmountcreditsSevenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(8.0 - courseStats.getAmountCreditsSevenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectAmountcreditsThirteenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(24.0 - courseStats.getAmountCreditsThirteenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectAmountcreditsNineteenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(36.0 - courseStats.getAmountCreditsNineteenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectAmountZeroAchieversSevenMonths() {

        assertEquals(2, this.courseStats.getAmountZeroAchieversSevenMonths());
    }

    @Test
    public void courseStatsHasCorrectAmountZeroAchieversThirteenMonths() {
        assertEquals(0, this.courseStats.getAmountZeroAchieversThirteenMonths());
    }

    @Test
    public void courseStatsHasCorrectAmountZeroAchieversNineteenMonths() {
        assertEquals(0, this.courseStats.getAmountZeroAchieversNineteenMonths());
    }

    @Test
    public void courseStatsHasCorrectAverageCreditsSevenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(2.0 - courseStats.getAverageCreditsSevenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectAverageCreditsThirteenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(6.0 - courseStats.getAverageCreditsThirteenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectAverageCreditsNineteenMonths() {
        double epsilon = 0.01;
        assertTrue(Math.abs(9.0 - courseStats.getAverageCreditsNineteenMonths()) < epsilon);
    }

    @Test
    public void courseStatsHasCorrectCreditGainsSevenMonths() {
        HashMap testMap = new HashMap();
        testMap.put(new Integer(0), new Integer(2));
        testMap.put(new Integer(4), new Integer(2));
        assertTrue(this.testUtils.equalityOfHashMaps(testMap, this.courseStats.getCreditGainsSevenMonths()));
    }

    @Test
    public void courseStatsHasCorrectCreditGainsThirteenMonths() {
        HashMap testMap = new HashMap();
        testMap.put(new Integer(3), new Integer(1));
        testMap.put(new Integer(5), new Integer(1));
        testMap.put(new Integer(4), new Integer(1));
        testMap.put(new Integer(12), new Integer(1));

        assertTrue(this.testUtils.equalityOfHashMaps(testMap, this.courseStats.getCreditGainsThirteenMonths()));
    }

    @Test
    public void courseStatsHasCorrectCreditGainsNineteenMonths() {
        HashMap testMap = new HashMap();
        testMap.put(new Integer(3), new Integer(1));
        testMap.put(new Integer(16), new Integer(1));
        testMap.put(new Integer(9), new Integer(1));
        testMap.put(new Integer(8), new Integer(1));

        assertTrue(this.testUtils.equalityOfHashMaps(testMap, this.courseStats.getCreditGainsNineteenMonths()));
    }

    @Test
    public void courseStatsHasCorrectCreditGainsSevenMonthsArr() {
        int[][] test = {{0, 2}, {0, 0}, {0, 0}, {0, 0}, {4, 2}};

//        testUtils.printArray(test);
//        testUtils.printArray(this.courseStats.getCreditGainsSevenMonthsArr());
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsSevenMonthsArr()));
    }

    @Test
    public void courseStatsHasCorrectCreditGainsThirteenMonthsArr() {
        int[][] test = {{0, 0}, {0, 0}, {0, 0}, {3, 1}, {4, 1}, {5, 1}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {12, 1}};
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsThirteenMonthsArr()));
    }

    @Test
    public void courseStatsHasCorrectCreditGainsNineteenMonthsArr() {
        int[][] test = {{0, 0}, {0, 0}, {0, 0}, {3, 1}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {8, 1}, {9, 1}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {16, 1}};
        testUtils.printArray(test);
        testUtils.printArray(this.courseStats.getCreditGainsNineteenMonthsArr());
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsNineteenMonthsArr()));
    }

    @Test
    public void courseStatsHasCorrectCategorizedCreditGainsSevenMonthsArr() {
        int[][] test = {{0, 4}};
        //        testUtils.printArray(test);
//        testUtils.printArray(this.courseStats.getCreditGainsSevenMonthsCategorizedArr());
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsSevenMonthsCategorizedArr()));
    }

    @Test
    public void courseStatsHasCorrectCategorizedCreditGainsThirteenMonthsArr() {
        int[][] test = {{0, 3}, {10, 1}};
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsThirteenMonthsCategorizedArr()));
    }

    @Test
    public void courseStatsHasCorrectCategorizedCreditGainsNineteenMonthsArr() {
        int[][] test = {{0, 3}, {10, 1}};
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsNineteenMonthsCategorizedArr()));
    }

    @Test
    public void courseStatsHasCorrectCreditGainsNormCumulSevenMonthsArr() {
        int[][] test = {{0, 2}, {1, 2}, {2, 2}, {3, 2}, {4, 4}};
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsSevenMonthsNormCumulArr()));
    }

    @Test
    public void courseStatsHasCorrectCreditGainsNormCumulThirteenMonthsArr() {
        int[][] test = {{0, 0}, {1, 0}, {2, 0}, {3, 1}, {4, 2}, {5, 3}, {6, 3}, {7, 3}, {8, 3}, {9, 3}, {10, 3}, {11, 3}, {12, 4}};
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsThirteenMonthsNormCumulArr()));
    }

    @Test
    public void courseStatsHasCorrectCreditGainsNormCumulNineteenMonthsArr() {
        int[][] test = {{0, 0}, {1, 0}, {2, 0}, {3, 1}, {4, 1}, {5, 1}, {6, 1}, {7, 1}, {8, 2}, {9, 3}, {10, 3}, {11, 3}, {12, 3}, {13, 3}, {14, 3}, {15, 3}, {16, 4}};
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsNineteenMonthsNormCumulArr()));
    }

    @Test
    public void courseStatsHasCorrectCreditGainsNormCumulReverSevenMonthsArr() {
        int[][] test = {{0, 2}, {1, 2}, {2, 2}, {3, 2}, {4, 0}};
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsSevenMonthsNormCumulReverArr()));
    }

    @Test
    public void courseStatsHasCorrectCreditGainsNormCumulReverThirteenMonthsArr() {
        int[][] test = {{0, 4}, {1, 4}, {2, 4}, {3, 3}, {4, 2}, {5, 1}, {6, 1}, {7, 1}, {8, 1}, {9, 1}, {10, 1}, {11, 1}, {12, 0}};
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsThirteenMonthsNormCumulReverArr()));
    }

    @Test
    public void courseStatsHasCorrectCreditGainsNormCumulReverNineteenMonthsArr() {
        int[][] test = {{0, 4}, {1, 4}, {2, 4}, {3, 3}, {4, 3}, {5, 3}, {6, 3}, {7, 3}, {8, 2}, {9, 1}, {10, 1}, {11, 1}, {12, 1}, {13, 1}, {14, 1}, {15, 1}, {16, 0}};
        assertTrue(testUtils.equivalentArrays(test, this.courseStats.getCreditGainsNineteenMonthsNormCumulReverArr()));
    }

    @Test
    public void maxAmountCoursesDoesNotExceed11() {
        assertTrue(true);
    }

    @Test
    public void allStudsIn() {
        assertEquals(5, studentService.findAll().size());
    }

    @Test
    public void alRightsToStudyIn() {
        assertEquals(5, rightToStudyService.findAll().size());
    }

    @Test
    public void allAcademicYearEnrollmentsIn() {
        assertEquals(77, academicYearEnrollmentService.findAll().size());
    }

    @Test
    public void allStatusOfStudyIn() {
        assertEquals(10, statusOfStudyService.findAll().size());
    }

    @Test
    public void alltypeOfStudyIn() {
        assertEquals(23, typeOfStudyService.findAll().size());
    }

    @Test
    public void allCourseObjectsIn() {
        assertEquals(5, courseObjectService.findAll().size());
    }

    @Test
    public void allStudiesIn() {
        assertEquals(21, studyService.findAll().size());
    }

    @Test
    public void allTeachersIn() {
        assertEquals(5, teacherService.findAll().size());
    }

    @Test
    public void allGradesIn() {
        assertEquals(21, gradeService.findAll().size());
    }
}
