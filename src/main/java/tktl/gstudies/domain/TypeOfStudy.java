package tktl.gstudies.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Entity class to map types of study.
 * @author hkeijone
 */
@Entity
public class TypeOfStudy extends AbstractModel {

    Integer code;
    String description;

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
    public String toString() {
        return this.code + " " + this.description;
    }
}
