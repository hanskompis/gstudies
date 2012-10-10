
package tktl.gstudies.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Study extends AbstractModel{
    private Integer studyId;
    @OneToOne
    private Stud stud;

    public Integer getStudyId() {
        return studyId;
    }

    public void setStudyId(Integer studyId) {
        this.studyId = studyId;
    }

    public Stud getStud() {
        return stud;
    }

    public void setStud(Stud stud) {
        this.stud = stud;
    }
    
    
}
