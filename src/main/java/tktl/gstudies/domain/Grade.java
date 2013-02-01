
package tktl.gstudies.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Entity class to map grades.
 * @author hkeijone
 */
@Entity
public class Grade extends AbstractModel{
    
    @OneToOne(mappedBy = "grade")
    private Study study;
    private String grade;
    private String description;

    public Grade() {
    }

    public Grade(String grade, String description) {
        this.grade = grade;
        this.description = description;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }
    @Override
    public String toString(){
        return this.getId().toString()+" "+this.description+" "+this.getGrade();
    }
    
}
