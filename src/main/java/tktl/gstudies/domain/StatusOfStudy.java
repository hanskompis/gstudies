package tktl.gstudies.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

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
