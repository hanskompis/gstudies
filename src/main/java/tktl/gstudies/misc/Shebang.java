package tktl.gstudies.misc;

import java.util.ArrayList;
import java.util.List;

public class Shebang {

    int mostCredits3 = 0;
    int mostCredits6 = 0;
    int mostCredits9 = 0;
    int mostCredits12 = 0;
    List<YearlyStats> arses = new ArrayList<YearlyStats>();

    @Override
    public String toString() {
        String s = "";
        for (YearlyStats ar : arses) {
            s += ar.toString();
        }
        return s;
    }

    private void setMostCreditsByMonth() {
        for (YearlyStats ys : arses) {
            if (ys.credits3 > mostCredits3) {
                mostCredits3 = ys.credits3;
            }
            if (ys.credits6 > mostCredits6) {
                mostCredits6 = ys.credits6;
            }
            if (ys.credits9 > mostCredits9) {
                mostCredits9 = ys.credits9;
            }
            if (ys.credits12 > mostCredits12) {
                mostCredits12 = ys.credits12;
            }
        }
        System.out.println("most3: " + mostCredits3);
        System.out.println("most6: " + mostCredits6);
        System.out.println("most9: " + mostCredits9);
        System.out.println("most12: " + mostCredits12);

    }

    public void normalizeCredits() {
        this.setMostCreditsByMonth();
        for (YearlyStats ys : arses) {
            ys.pc3.creditsNorm = (1.0*mostCredits3 / ys.pc3.credits) * ys.pc3.credits;
            System.out.println("most3:" + mostCredits3 + " pc3 credits: " + ys.pc3.credits);
            System.out.println("kerroin3: " + 1.0*mostCredits3 / ys.pc3.credits);
            ys.pc6.creditsNorm = (1.0*mostCredits6 / ys.pc6.credits) * ys.pc6.credits;
            System.out.println("most6:" + mostCredits6 + " pc6 credits: " + ys.pc6.credits);
            System.out.println("kerroin6: " + 1.0*mostCredits6 / ys.pc6.credits);
            ys.pc9.creditsNorm = (1.0*mostCredits9 / ys.pc9.credits) * ys.pc9.credits;
            System.out.println("most9:" + mostCredits9 + " pc9 credits: " + ys.pc9.credits);
            System.out.println("kerroin9: " + 1.0*mostCredits9 / ys.pc9.credits);
            ys.pc12.creditsNorm = (1.0*mostCredits12 / ys.pc12.credits) * ys.pc12.credits;
            System.out.println("most12:" + mostCredits12 + " pc12 credits: " + ys.pc12.credits);
            System.out.println("kerroin12: " + 1.0*mostCredits12 / ys.pc12.credits);

        }
    }
}
