package tktl.gstudies.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Entity class to map teachers
 * @author hkeijone
 */
@Entity
public class Teacher extends AbstractModel {

    @ManyToOne
    private Study study;
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    public Teacher() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }
}
