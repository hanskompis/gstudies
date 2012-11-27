package tktl.gstudies.domain;

import javax.persistence.Entity;

/**
 * Entity class to map statuses of study.
 * @author hkeijone
 */
@Entity
public class StatusOfStudy extends AbstractModel {

    private Integer code;
    private String description;
  
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
 
    @Override
    public String toString(){
        return Integer.toString(code)+" "+this.description;
    }
}
