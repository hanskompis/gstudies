
package tktl.gstudies.domain;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity class to map academic year enrollments.
 * @author hkeijone
 */
@Entity
public class AcademicYearEnrollment extends AbstractModel {
    
    @ManyToOne
    @JoinColumn
    private Stud student;
    private Date startDate;
    private Date endDate;
    private String type;

    public Stud getStudent() {
        return student;
    }

    public void setStudent(Stud student) {
        this.student = student;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
   @Override
   public String toString(){
       return this.startDate.toString()+" "+this.endDate.toString()+this.type;
   }
    
}
