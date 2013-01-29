
package tktl.gstudies.domain;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity class to map rights to study.
 * @author hkeijone
 */
@Entity
public class RightToStudy extends AbstractModel{
    @ManyToOne
    @JoinColumn
    private Stud student;
    private Date startingDate;
    private String mainSubject;

    public RightToStudy() {
    }

    public RightToStudy(Stud student, Date startingDate, String mainSubject) {
        this.student = student;
        this.startingDate = startingDate;
        this.mainSubject = mainSubject;
    }
    
    @Override
    public String toString() {
        return this.student.studentId+" "+this.startingDate+" "+this.mainSubject;
    }

    public Stud getStudent() {
        return student;
    }

    public void setStudent(Stud student) {
        this.student = student;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public String getMainSubject() {
        return mainSubject;
    }

    public void setMainSubject(String mainSubject) {
        this.mainSubject = mainSubject;
    }
    
    
}
