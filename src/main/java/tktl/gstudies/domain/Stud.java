package tktl.gstudies.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * Entity class to map students. Named queries for obtaining sets of students with different parameters. Queries are used 
 * in StatisticServiceClass.
 * @author hkeijone
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "findCSStudentsFromCourseWhoPassedOnDate",
    query = "SELECT DISTINCT s FROM Stud s JOIN s.rightsToStudy r, s.studies t JOIN t.courseObjects c, t.statusOfStudy o "
    + "WHERE c.courseId = :courseId AND t.dateOfAccomplishment = :dateOfAccomplishment AND o.code = 4 AND r.mainSubject ='Tietojenkäsittelytiede'"),
    @NamedQuery(
            name = "findOtherStudentsFromCourseWhoPassedOnDate",
    query = "SELECT DISTINCT s FROM Stud s JOIN s.rightsToStudy r, s.studies t JOIN t.courseObjects c, t.statusOfStudy o "
    + "WHERE c.courseId = :courseId AND t.dateOfAccomplishment = :dateOfAccomplishment AND o.code = 4 AND r.mainSubject <>'Tietojenkäsittelytiede'"),
    @NamedQuery(
            name = "findCSStudentsFromCourseWhoFailedOnDate",
    query = "SELECT DISTINCT s FROM Stud s JOIN s.rightsToStudy r, s.studies t JOIN t.courseObjects c, t.statusOfStudy o "
    + "WHERE c.courseId = :courseId AND t.dateOfAccomplishment = :dateOfAccomplishment AND o.code = 10 AND r.mainSubject ='Tietojenkäsittelytiede'"),
    @NamedQuery(
            name = "findOtherStudentsFromCourseWhoFailedOnDate",
    query = "SELECT DISTINCT s FROM Stud s JOIN s.rightsToStudy r, s.studies t JOIN t.courseObjects c, t.statusOfStudy o "
    + "WHERE c.courseId = :courseId AND t.dateOfAccomplishment = :dateOfAccomplishment AND o.code = 10 AND r.mainSubject <>'Tietojenkäsittelytiede'"),
    @NamedQuery(
        name = "getAllCSStuds",
    query = "SELECT DISTINCT s FROM Stud s JOIN s.rightsToStudy r WHERE r.mainSubject = 'Tietojenkäsittelytiede'"),
    @NamedQuery(
        name = "findCSStudentsFromCourseOnDate",
    query = "SELECT DISTINCT s FROM Stud s JOIN s.rightsToStudy r, s.studies t JOIN t.courseObjects c, t.statusOfStudy o "
    + " WHERE c.courseId = :courseId AND t.dateOfAccomplishment = :dateOfAccomplishment AND r.mainSubject ='Tietojenkäsittelytiede' AND o.code in (4,10)")
})
public class Stud extends AbstractModel {

    @OneToMany(mappedBy = "student")
    private List<AcademicYearEnrollment> enrollments;
    @OneToMany(mappedBy = "student")
    private List<RightToStudy> rightsToStudy;
    @OneToMany(mappedBy = "student")
    private List<Study> studies;
    Integer studentId;
    String gender;
    Date dateOfBirth;
    Date dateOfEnrollment;

    public List<AcademicYearEnrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<AcademicYearEnrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<RightToStudy> getRightsToStudy() {
        return rightsToStudy;
    }

    public void setRightsToStudy(List<RightToStudy> rightsToStudy) {
        this.rightsToStudy = rightsToStudy;
    }

    public List<Study> getStudies() {
        return studies;
    }

    public void setStudies(List<Study> studies) {
        this.studies = studies;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfEnrollment() {
        return dateOfEnrollment;
    }

    public void setDateOfEnrollment(Date dateOfEnrollment) {
        this.dateOfEnrollment = dateOfEnrollment;
    }

    public void addEnrollment(AcademicYearEnrollment a) {
        if (this.enrollments == null) {
            this.enrollments = new ArrayList<AcademicYearEnrollment>();
        }
        this.enrollments.add(a);
    }

    public void addRightToStudy(RightToStudy r) {
        if (this.rightsToStudy == null) {
            this.rightsToStudy = new ArrayList<RightToStudy>();
        }
        this.rightsToStudy.add(r);
    }

    public void addStudy(Study s) {
        if (this.studies == null) {
            this.studies = new ArrayList();
        }
        this.studies.add(s);
    }

    @Override
    public String toString() {
        return this.studentId.toString();
    }
}
