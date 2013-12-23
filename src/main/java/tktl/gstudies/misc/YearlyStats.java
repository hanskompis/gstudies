package tktl.gstudies.misc;

import java.util.ArrayList;
import java.util.List;

public class YearlyStats {

    ProducedCredits pc4;
    ProducedCredits pc7;
    ProducedCredits pc10;
    ProducedCredits pc13;
    ProducedCredits pc16;
    ProducedCredits pc19; 

    @Override
    public String toString() {
        String s = "";
        s += pc4;
        s += pc7;
        s += pc10;
        s += pc13;
        s += pc16;
        s += pc19;

        return s;
    }
    public String toCSVString() {
        String s = "";
        s += pc4.toCSVString();
        s += pc7.toCSVString();
        s += pc10.toCSVString();
        s += pc13.toCSVString();
        s += pc16.toCSVString();
        s += pc19.toCSVString();

        return s;
    }
}
