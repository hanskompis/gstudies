/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tktl;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.xml.crypto.Data;
import org.springframework.beans.factory.annotation.Autowired;
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
import tktl.gstudies.repositories.StudRepository;
import tktl.gstudies.repositories.StudyRepository;
import tktl.gstudies.services.AcademicYearEnrollmentService;
import tktl.gstudies.services.CourseObjectService;
import tktl.gstudies.services.GradeService;
import tktl.gstudies.services.RightToStudyService;
import tktl.gstudies.services.StatusOfStudyService;
import tktl.gstudies.services.StudentService;
import tktl.gstudies.services.StudyService;
import tktl.gstudies.services.TeacherService;
import tktl.gstudies.services.TypeOfStudyService;

@Service
public class TestDBUtils {

    @Autowired
    private JDBCRepository jdbcRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private RightToStudyService rightToStudyService;
    @Autowired
    private AcademicYearEnrollmentService academicYearEnrollmentService;
    @Autowired
    private StatusOfStudyService statusOfStudyService;
    @Autowired
    private TypeOfStudyService typeOfStudyService;
    @Autowired
    private CourseObjectService courseObjectService;
    @Autowired
    private StudyService studyService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private GradeService gradeService;

    public TestDBUtils() {
    }

    private java.util.Date makeDate(String dateString) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date toReturn = new java.util.Date();
        try {
            toReturn = df.parse(dateString);
            return toReturn;
        } catch (ParseException e) {
            System.out.println("KUSI!");
        }
        return toReturn;
    }

    public void insertStuds() {
        this.saveStud(1, "Mies", Date.valueOf("1980-01-01"), Date.valueOf("2004-08-01"));
        this.saveStud(2, "Mies", Date.valueOf("1985-01-01"), Date.valueOf("2004-01-01"));
        this.saveStud(3, "Nainen", Date.valueOf("1990-01-01"), Date.valueOf("2005-08-01"));
        this.saveStud(4, "Mies", Date.valueOf("1975-01-01"), Date.valueOf("2006-08-01"));
        this.saveStud(5, "Mies", Date.valueOf("1970-01-01"), Date.valueOf("2006-01-01"));
    }

    public void insertRightsToStudy() {
        this.saveRightToStudy(Date.valueOf("2004-08-01"), "Tietojenkäsittelytiede", 1);
        this.saveRightToStudy(Date.valueOf("2004-01-01"), "Tietojenkäsittelytiede", 2);
        this.saveRightToStudy(Date.valueOf("2005-08-01"), "Tietojenkäsittelytiede", 3);
        this.saveRightToStudy(Date.valueOf("2006-08-01"), "Tietojenkäsittelytiede", 4);
        this.saveRightToStudy(Date.valueOf("2006-01-01"), "Tietojenkäsittelytiede", 5);
//        System.out.println("opinto-oikeudet");
//        System.out.println(rightToStudyService.findAll().toString());
//        System.out.println("opiskelijat");
//        System.out.println(studentService.findAll().toString());
    }

    public void insertStatusOfStudy() {
        List<StatusOfStudy> list = this.jdbcRepository.getStatusOfStudyObjects();
        for (StatusOfStudy s : list) {
            statusOfStudyService.save(s);
        }
    }

    public void insertTypesOfStudy() {
        List<TypeOfStudy> list = this.jdbcRepository.getTypeOfStudyObjects();
        for (TypeOfStudy t : list) {
            typeOfStudyService.save(t);
        }
    }

    public void insertCourseObjects() {
        this.saveCourseObject(1, "1", "Satanismin perusteet", "Perusopinnot", "Opintojakso");
        this.saveCourseObject(2, "2", "Seminaari : Okkulttinen hitlerismi", "Syventävät opinnot", "Opintojakso");
        this.saveCourseObject(3, "3", "Räjähteet ja niiden valmistus", "Aineopinnot", "Opintojakso");
        this.saveCourseObject(4, "4", "Misantropian perusteet ja filosofinen oikeutus", "Perusopinnot", "Opintojakso");
        this.saveCourseObject(5, "5", "Rituaalinen sodomia", "Aineopinnot", "Opintojakso");
    }

    public void insertAcademicYearEnrollments() {
        this.saveAcademicYearEnrollment(1, Date.valueOf("2004-08-01"), Date.valueOf("2004-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2005-01-01"), Date.valueOf("2005-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2005-08-01"), Date.valueOf("2005-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2006-01-01"), Date.valueOf("2006-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2006-08-01"), Date.valueOf("2006-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2007-01-01"), Date.valueOf("2007-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2007-08-01"), Date.valueOf("2007-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2008-01-01"), Date.valueOf("2008-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2008-08-01"), Date.valueOf("2008-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2009-01-01"), Date.valueOf("2009-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2009-08-01"), Date.valueOf("2009-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2010-01-01"), Date.valueOf("2010-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2010-08-01"), Date.valueOf("2010-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2011-01-01"), Date.valueOf("2011-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2011-08-01"), Date.valueOf("2011-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2012-01-01"), Date.valueOf("2012-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(1, Date.valueOf("2012-08-01"), Date.valueOf("2012-12-31"), "Läsnäoleva");

        this.saveAcademicYearEnrollment(2, Date.valueOf("2004-01-01"), Date.valueOf("2004-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2004-08-01"), Date.valueOf("2004-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2005-01-01"), Date.valueOf("2005-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2005-08-01"), Date.valueOf("2005-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2006-01-01"), Date.valueOf("2006-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2006-08-01"), Date.valueOf("2006-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2007-01-01"), Date.valueOf("2007-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2007-08-01"), Date.valueOf("2007-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2008-01-01"), Date.valueOf("2008-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2008-08-01"), Date.valueOf("2008-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2009-01-01"), Date.valueOf("2009-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2009-08-01"), Date.valueOf("2009-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2010-01-01"), Date.valueOf("2010-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2010-08-01"), Date.valueOf("2010-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2011-01-01"), Date.valueOf("2011-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2011-08-01"), Date.valueOf("2011-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2012-01-01"), Date.valueOf("2012-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(2, Date.valueOf("2012-08-01"), Date.valueOf("2012-12-31"), "Läsnäoleva");

        this.saveAcademicYearEnrollment(3, Date.valueOf("2005-08-01"), Date.valueOf("2005-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2006-01-01"), Date.valueOf("2006-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2006-08-01"), Date.valueOf("2006-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2007-01-01"), Date.valueOf("2007-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2007-08-01"), Date.valueOf("2007-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2008-01-01"), Date.valueOf("2008-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2008-08-01"), Date.valueOf("2008-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2009-01-01"), Date.valueOf("2009-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2009-08-01"), Date.valueOf("2009-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2010-01-01"), Date.valueOf("2010-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2010-08-01"), Date.valueOf("2010-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2011-01-01"), Date.valueOf("2011-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2011-08-01"), Date.valueOf("2011-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2012-01-01"), Date.valueOf("2012-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(3, Date.valueOf("2012-08-01"), Date.valueOf("2012-12-31"), "Läsnäoleva");

        this.saveAcademicYearEnrollment(4, Date.valueOf("2006-08-01"), Date.valueOf("2006-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2007-01-01"), Date.valueOf("2007-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2007-08-01"), Date.valueOf("2007-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2008-01-01"), Date.valueOf("2008-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2008-08-01"), Date.valueOf("2008-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2009-01-01"), Date.valueOf("2009-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2009-08-01"), Date.valueOf("2009-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2010-01-01"), Date.valueOf("2010-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2010-08-01"), Date.valueOf("2010-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2011-01-01"), Date.valueOf("2011-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2011-08-01"), Date.valueOf("2011-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2012-01-01"), Date.valueOf("2012-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(4, Date.valueOf("2012-08-01"), Date.valueOf("2012-12-31"), "Läsnäoleva");

        this.saveAcademicYearEnrollment(5, Date.valueOf("2006-01-01"), Date.valueOf("2006-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2006-08-01"), Date.valueOf("2006-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2007-01-01"), Date.valueOf("2007-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2007-08-01"), Date.valueOf("2007-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2008-01-01"), Date.valueOf("2008-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2008-08-01"), Date.valueOf("2008-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2009-01-01"), Date.valueOf("2009-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2009-08-01"), Date.valueOf("2009-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2010-01-01"), Date.valueOf("2010-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2010-08-01"), Date.valueOf("2010-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2011-01-01"), Date.valueOf("2011-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2011-08-01"), Date.valueOf("2011-12-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2012-01-01"), Date.valueOf("2012-07-31"), "Läsnäoleva");
        this.saveAcademicYearEnrollment(5, Date.valueOf("2012-08-01"), Date.valueOf("2012-12-31"), "Läsnäoleva");

    }

    public void insertStudies() {
        this.saveStudy(1, 1, 1, 4, 1, 4, Date.valueOf("2006-10-15"));
        this.saveStudy(2, 2, 1, 4, 1, 4, Date.valueOf("2006-10-15"));
        this.saveStudy(3, 3, 1, 4, 1, 4, Date.valueOf("2006-10-15"));
        this.saveStudy(4, 4, 1, 10, 1, 4, Date.valueOf("2006-10-15"));
        this.saveStudy(5, 5, 1, 4, 1, 4, Date.valueOf("2006-10-15"));
        this.saveStudy(2, 6, 2, 4, 1, 4, Date.valueOf("2007-03-18"));
        this.saveStudy(3, 7, 2, 10, 1, 4, Date.valueOf("2007-03-18"));
        this.saveStudy(4, 8, 2, 10, 1, 4, Date.valueOf("2007-03-18"));
        this.saveStudy(5, 9, 2, 4, 1, 4, Date.valueOf("2007-03-18"));
        this.saveStudy(1, 10, 3, 10, 1, 5, Date.valueOf("2007-06-01"));
        this.saveStudy(2, 11, 3, 4, 1, 5, Date.valueOf("2007-06-01"));
        this.saveStudy(3, 12, 3, 4, 1, 5, Date.valueOf("2007-06-01"));
        this.saveStudy(4, 13, 3, 10, 1, 5, Date.valueOf("2007-06-01"));
        this.saveStudy(1, 14, 4, 4, 1, 3, Date.valueOf("2007-10-25"));
        this.saveStudy(2, 15, 4, 4, 1, 3, Date.valueOf("2007-10-25"));
        this.saveStudy(3, 16, 4, 10, 1, 3, Date.valueOf("2007-10-25"));
        this.saveStudy(4, 17, 4, 4, 1, 3, Date.valueOf("2007-10-25"));
        this.saveStudy(2, 18, 5, 4, 1, 4, Date.valueOf("2008-02-01"));
        this.saveStudy(3, 19, 5, 4, 1, 4, Date.valueOf("2008-02-01"));
        this.saveStudy(4, 20, 5, 10, 1, 4, Date.valueOf("2008-02-01"));
        this.saveStudy(5, 21, 5, 4, 1, 4, Date.valueOf("2008-02-01"));
    }

    public void insertTeachers() {
        this.saveTeacher("Möd knuller", 1);
        this.saveTeacher("Möd knuller", 2);
        this.saveTeacher("Ylva hubatsa", 3);
        this.saveTeacher("Arto Brunkkari", 4);
        this.saveTeacher("Vittu-Stina Kulaus-Richter", 5);
    }

    public void insertGrades() {
        this.saveGrade(1, "3", "Yleinen asteikko");
        this.saveGrade(2, "4", "Yleinen asteikko");
        this.saveGrade(3, "2", "Yleinen asteikko");
        this.saveGrade(4, "Hyl.", "Yleinen asteikko");
        this.saveGrade(5, "5", "Yleinen asteikko");
        this.saveGrade(6, "1", "Yleinen asteikko");
        this.saveGrade(7, "Hyl.", "Yleinen asteikko");
        this.saveGrade(8, "Hyl.", "Yleinen asteikko");
        this.saveGrade(9, "3", "Yleinen asteikko");
        this.saveGrade(10, "Hyl.", "Yleinen asteikko");
        this.saveGrade(11, "4", "Yleinen asteikko");
        this.saveGrade(12, "2", "Yleinen asteikko");
        this.saveGrade(13, "Hyl.", "Yleinen asteikko");
        this.saveGrade(14, "2", "Yleinen asteikko");
        this.saveGrade(15, "5", "Yleinen asteikko");
        this.saveGrade(16, "Hyl,", "Yleinen asteikko");
        this.saveGrade(17, "5", "Yleinen asteikko");
        this.saveGrade(18, "3", "Yleinen asteikko");
        this.saveGrade(19, "1", "Yleinen asteikko");
        this.saveGrade(20, "Hyl.", "Yleinen asteikko");
        this.saveGrade(21, "5", "Yleinen asteikko");
    }

    private void saveGrade(int studyNumber, String grade, String description) {
        Grade toAdd = this.gradeService.save(new Grade(grade, description));
        this.gradeService.save(toAdd, studyNumber);
    }

    private void saveTeacher(String nimi, int studynumber) {
        Teacher toAdd = this.teacherService.save(new Teacher(nimi));
        this.teacherService.save(toAdd, studynumber);
    }

    private void saveStudy(int studentId, int studyNumber, int courseObjectId, int statusOfStudyCode, int typeOfStudyCode, double credits, Date dateOfAccomplishment) {
        Study toAdd = new Study(studyNumber, credits, dateOfAccomplishment);
        toAdd = studyService.save(toAdd);
        this.studyService.save(toAdd, studentId, courseObjectId, statusOfStudyCode, typeOfStudyCode);
    }

    private void saveStud(int studentId, String gender, Date dateOfBirth, Date dateOfEnrollment) {
        Stud toAdd = new Stud(studentId, gender, dateOfBirth, dateOfEnrollment);
        this.studentService.save(toAdd);
    }

    private void saveRightToStudy(Date startingDate, String mainSubject, int studentId) {
        RightToStudy toAdd = new RightToStudy(null, startingDate, mainSubject);
        toAdd = this.rightToStudyService.save(toAdd);
        this.rightToStudyService.save(toAdd, studentId);
    }

    private void saveAcademicYearEnrollment(int studentId, Date startDate, Date endDate, String type) {
        AcademicYearEnrollment toAdd = new AcademicYearEnrollment(null, startDate, endDate, type);
        toAdd = this.academicYearEnrollmentService.save(toAdd);
        this.academicYearEnrollmentService.save(toAdd, studentId);
    }

    private void saveCourseObject(int objectId, String courseId, String name, String type, String kind) {
        CourseObject toAdd = new CourseObject(objectId, courseId, name, type, kind);
        toAdd = this.courseObjectService.save(toAdd);
    }
}
