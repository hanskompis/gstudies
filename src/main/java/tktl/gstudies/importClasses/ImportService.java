package tktl.gstudies.importClasses;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.AcademicYearEnrollment;
import tktl.gstudies.domain.CourseObject;
import tktl.gstudies.domain.Grade;
import tktl.gstudies.domain.RightToStudy;
import tktl.gstudies.domain.StatusOfStudy;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.domain.Study;
import tktl.gstudies.domain.Teacher;
import tktl.gstudies.domain.TypeOfStudy;
import tktl.gstudies.repositories.JDBCRepository;
import tktl.gstudies.services.AcademicYearEnrollmentService;
import tktl.gstudies.services.CourseObjectService;
import tktl.gstudies.services.GradeService;
import tktl.gstudies.services.StatusOfStudyService;
import tktl.gstudies.services.StudentService;
import tktl.gstudies.services.TypeOfStudyService;
import tktl.gstudies.services.RightToStudyService;
import tktl.gstudies.services.StudyService;
import tktl.gstudies.services.TeacherService;

/**
 * A class for importing database from H2-database to ORM-database on top of
 * MySQL
 *
 * @author hkeijone
 */
@Service
public class ImportService {

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
    private JDBCRepository jdbcrepository;
    static int i = 0;
    Set<Integer> importedStudyIds = new HashSet();

    public void importDB() {
        this.importStudentObjects();
        this.importRightToStudyObjects();
        this.importAcademicEnrollmentObjects();
        this.importStatusOfStudyObjects();
        this.importTypeOfStudyObjects();
        this.importCourseObjectObjects();
        this.importStudyObjects2();
        this.importTeacherObjects();
        this.importGradeObjects();
    }

    private void importStatusOfStudyObjects() {
        List<StatusOfStudy> list = this.jdbcrepository.getStatusOfStudyObjects();
        for (StatusOfStudy s : list) {
            statusOfStudyService.save(s);
        }
    }

    private void importStudentObjects() {
        List<Stud> list = this.jdbcrepository.getStudentObjects();
        for (Stud s : list) {
            studentService.save(s);
        }
    }

    private void importTypeOfStudyObjects() {
        List<TypeOfStudy> list = this.jdbcrepository.getTypeOfStudyObjects();
        for (TypeOfStudy t : list) {
            typeOfStudyService.save(t);
        }
    }

    private void importCourseObjectObjects() {
        List<CourseObject> list = this.jdbcrepository.getCourseObjectObjects();
        for (CourseObject c : list) {
            courseObjectService.save(c);
        }
    }

    private void importAcademicEnrollmentObjects() {
        List<Map> list = this.jdbcrepository.getAcademicEnrollmentData();
        for (Map row : list) {
            AcademicYearEnrollment aye = new AcademicYearEnrollment();
            aye.setStartDate((Date) row.get("ALPVM"));
            aye.setEndDate((Date) row.get("PAATPVM"));
            aye.setType((String) row.get("TYYPPI"));
            aye = academicYearEnrollmentService.save(aye);
            Integer studentId = ((Integer) row.get("HLO"));
            AcademicYearEnrollment returned = academicYearEnrollmentService.save(aye, studentId);
        }
    }

    private void importRightToStudyObjects() {
        List<Map> list = this.jdbcrepository.getRightToStudyData();
        for (Map row : list) {
            RightToStudy rts = new RightToStudy();
            rts.setStartingDate((Date) row.get("ALKPVM"));
            rts.setMainSubject((String) row.get("PAAAINE"));
            rts = rightToStudyService.save(rts);
            Integer studentId = ((Integer) row.get("HLO"));
            RightToStudy returned = rightToStudyService.save(rts, studentId);
        }
    }

    private void importStudyObjects2() {
        Integer[] acceptableTypeOFStudyCodes = {1, 3, 4, 7, 12, 13, 14, 15, 20};
        final List lst = Arrays.asList(acceptableTypeOFStudyCodes);
        this.jdbcrepository.getStudyData(new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                int studyNumber = (Integer) rs.getObject("OPINTO");
                if (importedStudyIds.contains(studyNumber)) {
                    return;
                }
                Study study = new Study();
                study.setStudyNumber((Integer) rs.getObject("OPINTO"));
                study.setCredits(((Float) rs.getObject("LAAJOP")).doubleValue());
                study.setDateOfwrite((Date) rs.getObject("KIRJPVM"));
                study.setDateOfAccomplishment((Date) rs.getObject("SUORPVM"));
                Integer typeOfStudyCode = (Integer) rs.getObject("SUORTYYP");
                if (!lst.contains(typeOfStudyCode)) {
                    return;
                }
                study = studyService.save(study);
                i++;
                System.out.println(i + " study: " + study);
                Integer studentId = (Integer) rs.getObject("HLO");
                Integer courseObjectId = (Integer) rs.getObject("OPINKOHD");
                Integer statusOfStudyCode = (Integer) rs.getObject("OPINSTAT");
                studyService.save(study, studentId, courseObjectId, statusOfStudyCode, typeOfStudyCode);
                importedStudyIds.add(studyNumber);
            }
        });
    }

    private void importGradeObjects() {
        this.jdbcrepository.getGradeObjects(new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                Grade grade = new Grade();
                grade.setGrade(rs.getString("ARVSANARV"));
                grade.setDescription(rs.getString("SELITE"));
                gradeService.save(grade);
                Integer studyNumber = rs.getInt("OPINTO");
                grade = gradeService.save(grade, studyNumber);
                System.out.println(grade);
            }
        });
    }

    private void importTeacherObjects() {
        this.jdbcrepository.getTeacherData(new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                Teacher teacher = new Teacher();
                teacher.setName((String) rs.getObject("NIMLYH"));
                teacher = teacherService.save(teacher);
                Integer studyNumber = (Integer) rs.getObject("OPINTO");
                System.out.println("teacher: " + teacher.getName() + " study: " + studyNumber);
                teacher = teacherService.save(teacher, studyNumber);
            }
        });
    }

    private void importDatesOfAccomplishment() {
        this.jdbcrepository.getStudyData(new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                Integer studyNumber = (Integer) rs.getObject("OPINTO");
                Date dateOfAccomplishment = (Date) rs.getObject("SUORPVM");
                studyService.addDateOfAccomplishment(studyNumber, dateOfAccomplishment);
            }
        });
    }

    public static void main(String[] args) {
        // /home/hkeijone/gstudies/
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "spring-context.xml", prefix + "spring-database.xml"});

        ImportService impo = (ImportService) ctx.getBean("importService");
        //impo.importDB();
        impo.importDatesOfAccomplishment();
    }
}
