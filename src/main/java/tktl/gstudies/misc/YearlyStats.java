package tktl.gstudies.misc;

import java.util.ArrayList;
import java.util.List;

public class YearlyStats {

    int credits3 = 0;
    int credits6 = 0;
    int credits9 = 0;
    int credits12 = 0;
//    int creditsNorm3;
//    int creditsNorm6;
//    int creditsNorm9;
//    int creditsNorm12;
//    List<ProducedCredits> credObjs = new ArrayList<ProducedCredits>();
    ProducedCredits pc3;
    ProducedCredits pc6;
    ProducedCredits pc9;
    ProducedCredits pc12;

    @Override
    public String toString() {
        String s = "";
        s += pc3;
        s += pc6;
        s += pc9;
        s += pc12;

        return s;
    }

    public void setCredits() {
        this.credits3 = pc3.credits;
        this.credits6 = pc6.credits;
        this.credits9 = pc9.credits;
        this.credits12 = pc12.credits;

    }
//    public void normalizeCredits() {
//        setMostCredits();
//        for (ProducedCredits pc : credObjs) {
//             pc.creditsNorm = (mostCredits/pc.credits)*pc.credits;
//        }
//
//    }
}
