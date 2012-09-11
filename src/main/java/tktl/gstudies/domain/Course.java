
package tktl.gstudies.domain;

public enum Course {

    TIRA("58131"), OHPE("581325"), OHJA("582103"),
    OHMA("582104"), TITO("581305"), TIKAPE("581328"),
    OHJHARJ("58160"), TSOHA("582203"), JTKT("582102"),
    JDM("57049"), TTYO("581324");
    
    private final String index;
    private Course(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
