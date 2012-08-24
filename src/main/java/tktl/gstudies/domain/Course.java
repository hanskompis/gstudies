package tktl.gstudies.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Course {

    public enum CourseNames {
//    TIRA("58131"), OHPE("581325"), OHJA("582103"), 
//    OHMA("582104"), TITO("581305"), TIKAPE("581328"), 
//    OHJHARJ("58160"), TSOHA("582203"), JTKT("582102"), 
//    JDM("57049"), TTYO("581324");

        TIRA("0"), OHPE("1"), OHJA("2"), OHMA("3"), TITO("4"),
        TIKAPE("5"), OHJHARJ("6"), TSOHA("7"), JTKT("8"), JDM("9"), TTYO("10");
        private final String index;

        private CourseNames(String index) {
            this.index = index;
        } 
       
        public String getIndex() {
            return index;
        }
    }

    String code;
    String name;

    public Course() {
    }

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Course(String code) {
        this.code = code;
        for(CourseNames c : CourseNames.values()){
            if(c.getIndex().equals(this.code)){
                this.name = c.name();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
