
package tktl.gstudies.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Grade extends AbstractModel{
    @OneToOne(mappedBy = "grade")
    private Study study;
    private String grade;
    private String description;

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
    
}
