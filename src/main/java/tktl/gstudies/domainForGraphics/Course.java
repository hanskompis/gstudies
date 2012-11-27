package tktl.gstudies.domainForGraphics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * An enumeration class used by course instance class.
 * @author hkeijone
 */
public enum Course {

    TIRA("58131"), OHPE("581325"), OHJA("582103", "581326"),
    OHMA("582104", "582101"), TITO("581305"), TIKAPE("581328"),
    OHJHARJ("58160"), TSOHA("582203", "581329"), JTKT("582102"),
    JDM("57049"), TTYO("581324"),;
    private Set<String> indexes;

    private Course(String... indexes) {
        this.indexes = new HashSet(Arrays.asList(indexes));
    }

    public boolean sameCourse(String index) {
        return this.indexes.contains(index);
    }
}
