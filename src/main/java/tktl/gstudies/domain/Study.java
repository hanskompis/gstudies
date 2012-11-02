package tktl.gstudies.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Study extends AbstractModel implements Comparable {

    @ManyToOne
    @JoinColumn
    private Stud student;
    private Integer studyNumber;
    @ManyToMany
    private List<CourseObject> courseObjects;
//    @ManyToOne
//    @JoinColumn        
//    CourseObject courseObject;
    @ManyToOne
    @JoinColumn
    private StatusOfStudy statusOfStudy;
    @ManyToOne
    @JoinColumn
    private TypeOfStudy typeOfStudy;
    private double credits;
    @OneToMany(mappedBy = "study")
    private List<Teacher> teachers;
    @OneToOne
    private Grade grade;
    private Date dateOfwrite;

    public List<CourseObject> getCourseObjects() {
        return courseObjects;
    }

    public void setCourseObjects(List<CourseObject> courseObjects) {
        this.courseObjects = courseObjects;
    }

    public Date getDateOfwrite() {
        return dateOfwrite;
    }

    public void setDateOfwrite(Date dateOfwrite) {
        this.dateOfwrite = dateOfwrite;
    }

    public Integer getStudyNumber() {
        return studyNumber;
    }

    public void setStudyNumber(Integer studyNumber) {
        this.studyNumber = studyNumber;
    }

    public Stud getStudent() {
        return student;
    }

    public void setStudent(Stud student) {
        this.student = student;
    }

    public StatusOfStudy getStatusOfStudy() {
        return statusOfStudy;
    }

    public void setStatusOfStudy(StatusOfStudy statusOfStudy) {
        this.statusOfStudy = statusOfStudy;
    }

    public TypeOfStudy getTypeOfStudy() {
        return typeOfStudy;
    }

    public void setTypeOfStudy(TypeOfStudy typeOfStudy) {
        this.typeOfStudy = typeOfStudy;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

//    public void addStudent(Stud s) {
//        this.student = s;
//    }
    public void addCourseObject(CourseObject co) {
        if (this.courseObjects == null) {
            this.courseObjects = new ArrayList<CourseObject>();
        }
        this.courseObjects.add(co);
    }

    public void addTeacher(Teacher teacher) {
        if (this.teachers == null) {
            this.teachers = new ArrayList<Teacher>();
        }
        teachers.add(teacher);
    }

    @Override
    public String toString() {
        String ret = "";
        if (this.dateOfwrite != null) {
            ret = ret + this.dateOfwrite.toString() + " ";
        }
        if (this.studyNumber != null) {
            ret = ret + this.studyNumber.toString() + " ";
        }
        ret = ret + Double.toString(credits);
        return ret;
    }

    @Override
    public int compareTo(Object o) {
        Study s = (Study) o;
        if (this.dateOfwrite != null && s.dateOfwrite != null) {
            if (this.dateOfwrite.before(s.dateOfwrite)) {
                return -1;
            } else if (this.dateOfwrite.after(s.dateOfwrite)) {
                return 1;
            } else {
                return 0;
            }
        }
        else{
            return 0;
        }

    }
}
