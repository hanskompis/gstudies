
package tktl.gstudies.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import tktl.gstudies.domain.Study;

@Entity
public class CourseObject extends AbstractModel{
    @ManyToMany
    private List<Study> studies;
    private Integer objectId;
    private String courseId;
    private String name;
    private String type;
    private String kind;
    
    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public List<Study> getStudies() {
        return studies;
    }

    public void setStudies(List<Study> studies) {
        this.studies = studies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
    
    public void addStudy(Study s){
        if(this.studies == null){
            this.studies = new ArrayList<Study>();
        }
        this.studies.add(s);
    }
}
