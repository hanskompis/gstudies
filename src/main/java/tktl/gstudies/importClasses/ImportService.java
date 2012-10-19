package tktl.gstudies.importClasses;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void importDB() {
//        this.importStudentObjects();
//        this.importRightToStudyObjects();
        //      this.importAcademicEnrollmentObjects();
//        this.importStatusOfStudyObjects();
//        this.importTypeOfStudyObjects();
 //this.importCourseObjectObjects();
        this.importStudyObjects2();
//        this.importTeacherObjects();
    }
    //OK, muuta vaan privateksi

    public void importStatusOfStudyObjects() {
        List<StatusOfStudy> list = this.jdbcrepository.getStatusOfStudyObjects();
        for (StatusOfStudy s : list) {
            statusOfStudyService.save(s);
        }
    }
    //OK, muuta vaan privateksi

    public void importStudentObjects() {
        List<Stud> list = this.jdbcrepository.getStudentObjects();
        // System.out.println("total students: " + list.size());
        for (Stud s : list) {
            studentService.save(s);
        }
    }
    //OK, muuta vaan privateksi

    public void importTypeOfStudyObjects() {
        List<TypeOfStudy> list = this.jdbcrepository.getTypeOfStudyObjects();
        for (TypeOfStudy t : list) {
            typeOfStudyService.save(t);
        }
    }

    public void importCourseObjectObjects() {
        List<CourseObject> list = this.jdbcrepository.getCourseObjectObjects();
        for (CourseObject c : list) {
            courseObjectService.save(c);
        }
    }
    //VAIHEESSA

    public void importGradeObjects() {
//        List<Grade> list = this.jdbcrepository.getGradeObjects();
//        for(Grade g : list){
//            gradeService.save(g);
//        }
    }

    public void importAcademicEnrollmentObjects() {
        List<Map> list = this.jdbcrepository.getAcademicEnrollmentData();
        for (Map row : list) {
            AcademicYearEnrollment aye = new AcademicYearEnrollment();
            aye.setStartDate((Date) row.get("ALPVM"));
            aye.setEndDate((Date) row.get("PAATPVM"));
            aye.setType((String) row.get("TYYPPI"));
            aye = academicYearEnrollmentService.save(aye);
            Integer studentId = ((Integer) row.get("HLO"));

            AcademicYearEnrollment returned = academicYearEnrollmentService.save(aye, studentId);
            //System.out.println(returned);
        }
    }

    public void importRightToStudyObjects() {
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

    public void importStudyObjects() {
        List<Map> list = this.jdbcrepository.getStudyData();
        for (Map row : list) {
            Study study = new Study();
            study.setStudyNumber((Integer) row.get("OPINTO"));
            study.setCredits(((Double) row.get("LAAJOP")).doubleValue());
            study.setDateOfwrite((Date) row.get("KIRJPVM"));
            Integer studentId = (Integer) row.get("HLO");
            Integer courseObjectId = (Integer) row.get("OPINKOHD");
            Integer statusOfStudyCode = (Integer) row.get("OPINSTAT");
            Integer typeOfStudyCode = (Integer) row.get("SUORTYYP");
            studyService.save(study, studentId, courseObjectId, statusOfStudyCode, typeOfStudyCode);
        }
    }

    public void importStudyObjects2() {
        this.jdbcrepository.getStudyData(new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    Study study = new Study();
                    study.setStudyNumber((Integer) rs.getObject("OPINTO"));
                    study.setCredits(((Float) rs.getObject("LAAJOP")).doubleValue());
                    study.setDateOfwrite((Date) rs.getObject("KIRJPVM"));

                    Integer typeOfStudyCode = (Integer) rs.getObject("SUORTYYP");
                    Integer[] acceptableTypeOFStudyCodes = {1, 3, 4, 7, 12, 13, 14, 15, 20};
                    List lst = Arrays.asList(acceptableTypeOFStudyCodes);
                    if (!lst.contains(typeOfStudyCode)) {
                        continue;
                    }
                    study = studyService.save(study);

                    Integer studentId = (Integer) rs.getObject("HLO");
                    Integer courseObjectId = (Integer) rs.getObject("OPINKOHD");
                    Integer statusOfStudyCode = (Integer) rs.getObject("OPINSTAT");



                    studyService.save(study, studentId, courseObjectId, statusOfStudyCode, typeOfStudyCode);
                }

            }
        });
    }

    public void importTeacherObjects() {
        this.jdbcrepository.getTeacherData(new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                Teacher teacher = new Teacher();
                teacher.setName((String) rs.getObject("NIMLYH"));
                teacher = teacherService.save(teacher);
                Integer studyNumber = (Integer) rs.getObject("OPINTO");
                System.out.println("teacher: " + teacher.getName() + " study: " + studyNumber);
                teacherService.save(teacher, studyNumber);
            }
        });
    }
}
