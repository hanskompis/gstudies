package tktl.gstudies.domain;


    public enum Course {
//    TIRA("58131"), OHPE("581325"), OHJA("582103"), 
//    OHMA("582104"), TITO("581305"), TIKAPE("581328"), 
//    OHJHARJ("58160"), TSOHA("582203"), JTKT("582102"), 
//    JDM("57049"), TTYO("581324");

        TIRA("0"), OHPE("1"), OHJA("2"), OHMA("3"), TITO("4"),
        TIKAPE("5"), OHJHARJ("6"), TSOHA("7"), JTKT("8"), JDM("9"), TTYO("10");
        
        private final String index;

        private Course(String index) {
            this.index = index;
        } 
       
        public String getIndex() {
            return index;
        }
    }



//}
