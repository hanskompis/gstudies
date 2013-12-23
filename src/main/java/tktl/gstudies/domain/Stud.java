package tktl.gstudies.domain;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    query = "SELECT DISTINCT s FROM Stud s JOIN  s.studies t JOIN t.courseObjects c, t.statusOfStudy o " // s.rightsToStudy r,
    + "WHERE c.courseId = :courseId AND t.dateOfAccomplishment = :dateOfAccomplishment AND o.code = 4"), // AND r.mainSubject ='Tietojenkäsittelytiede'
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
    String studentNumber;
    String gender;
    Date dateOfBirth;
    Date dateOfEnrollment;

    public Stud() {
    }

    //for tests' sake
    public Stud(Integer studentId, String gender, Date dateOfBirth, Date dateOfEnrollment) {
        this.studentId = studentId;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.dateOfEnrollment = dateOfEnrollment;
    }
    
    @Override
    public String toString(){
        return this.studentId+" "+this.gender+" "+this.dateOfBirth+" "+this.dateOfEnrollment+" "+this.rightsToStudy.get(0).getMainSubject();
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }


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

    public int getAge(java.util.Date atTime) {
//        System.out.println("date of birth: " + new java.util.Date(dateOfBirth.getTime()).toString());
//        System.out.println("comparing with: " + new java.util.Date(atTime.getTime()).toString());
        
        long diff = atTime.getTime() - dateOfBirth.getTime();
        int years = (int) (diff / 31556952000L);
        
//        System.out.println("years: " + years);
        return years;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.studentId != null ? this.studentId.hashCode() : 0);
        hash = 59 * hash + (this.studentNumber != null ? this.studentNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stud other = (Stud) obj;
        if (this.studentId != other.studentId && (this.studentId == null || !this.studentId.equals(other.studentId))) {
            return false;
        }
        if ((this.studentNumber == null) ? (other.studentNumber != null) : !this.studentNumber.equals(other.studentNumber)) {
            return false;
        }
        return true;
    }
}
