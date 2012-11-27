package tktl.gstudies.domainForGraphics;

/**
 * Dummy enum class for testing purposes. 
 * @author hkeijone
 */
public enum DummyCourse {
    
    TIRA("0"), OHPE("1"), OHJA("2"), OHMA("3"), TITO("4"),
    TIKAPE("5"), OHJHARJ("6"), TSOHA("7"), JTKT("8"), JDM("9"), TTYO("10");
    private final String index;

    private DummyCourse(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
