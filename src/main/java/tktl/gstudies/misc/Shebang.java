package tktl.gstudies.misc;

import java.util.ArrayList;
import java.util.List;

public class Shebang {

    List<YearlyStats> arses = new ArrayList<YearlyStats>();
    
    
    double mostStudsWithAtleastOneCredit4 = 0;
    double mostStudsWithAtleastOneCredit7 = 0;
    double mostStudsWithAtleastOneCredit10 = 0;
    double mostStudsWithAtleastOneCredit13 = 0;
    double mostStudsWithAtleastOneCredit16 = 0;
    double mostStudsWithAtleastOneCredit19 = 0;
    
    double mostStuds4 = 0;
    double mostStuds7 = 0;
    double mostStuds10 = 0;
    double mostStuds13 = 0;
    double mostStuds16 = 0;
    double mostStuds19 = 0;
    
    
    
    double overallMostStuds4 = 0;
    double overallMostStuds7 = 0;
    double overallMostStuds10 = 0;
    double overallMostStuds13 = 0;
    double overallMostStuds16 = 0;
    double overallMostStuds19 = 0;

    @Override
    public String toString() {
        String s = "";
        for (YearlyStats ar : arses) {
            s += ar.toString();
        }
        return s;
    }
    
    
    public String toCSVString() {
        String s = "";
        for (YearlyStats ar : arses) {
            s += ar.toCSVString();
        }
        return s;
    }


    private void findMostStuds() {
        for (YearlyStats ar : arses) {
            
            // kirjautunut, mukana myös tyypit ketkä ei edes yrittänyt
            
            if (ar.pc4.amountStuds > overallMostStuds4) {
                overallMostStuds4 = 1.0 * ar.pc4.amountStuds;
            }            
            if (ar.pc7.amountStuds > overallMostStuds7) {
                overallMostStuds7 = 1.0 * ar.pc7.amountStuds;
            }            
            if (ar.pc10.amountStuds > overallMostStuds10) {
                overallMostStuds10 = 1.0 * ar.pc10.amountStuds;
            }            
            if (ar.pc13.amountStuds > overallMostStuds13) {
                overallMostStuds13 = 1.0 * ar.pc13.amountStuds;
            }
            if (ar.pc16.amountStuds > overallMostStuds16) {
                overallMostStuds16 = 1.0 * ar.pc16.amountStuds;
            }
            if (ar.pc19.amountStuds > overallMostStuds19) {
                overallMostStuds19 = 1.0 * ar.pc19.amountStuds;
            }
            
            
            // yrittänyt opintoja
            
            if (ar.pc4.correctedAmountOfStuds > mostStuds4) {
                mostStuds4 = 1.0 * ar.pc4.correctedAmountOfStuds;
            }            
            if (ar.pc7.correctedAmountOfStuds > mostStuds7) {
                mostStuds7 = 1.0 * ar.pc7.correctedAmountOfStuds;
            }            
            if (ar.pc10.correctedAmountOfStuds > mostStuds10) {
                mostStuds10 = 1.0 * ar.pc10.correctedAmountOfStuds;
            }            
            if (ar.pc13.correctedAmountOfStuds > mostStuds13) {
                mostStuds13 = 1.0 * ar.pc13.correctedAmountOfStuds;
            }
            if (ar.pc16.correctedAmountOfStuds > mostStuds16) {
                mostStuds16 = 1.0 * ar.pc16.correctedAmountOfStuds;
            }
            if (ar.pc19.correctedAmountOfStuds > mostStuds19) {
                mostStuds19 = 1.0 * ar.pc19.correctedAmountOfStuds;
            }
            
            
            // väh 1 op
            if (ar.pc4.studentsWithAtleastOneCredit() > mostStudsWithAtleastOneCredit4) {
                mostStudsWithAtleastOneCredit4 = 1.0 * ar.pc4.studentsWithAtleastOneCredit();
            }            
            if (ar.pc7.studentsWithAtleastOneCredit() > mostStudsWithAtleastOneCredit7) {
                mostStudsWithAtleastOneCredit7 = 1.0 * ar.pc7.studentsWithAtleastOneCredit();
            }            
            if (ar.pc10.studentsWithAtleastOneCredit() > mostStudsWithAtleastOneCredit10) {
                mostStudsWithAtleastOneCredit10 = 1.0 * ar.pc10.studentsWithAtleastOneCredit();
            }            
            if (ar.pc13.studentsWithAtleastOneCredit() > mostStudsWithAtleastOneCredit13) {
                mostStudsWithAtleastOneCredit13 = 1.0 * ar.pc13.studentsWithAtleastOneCredit();
            }
            if (ar.pc16.studentsWithAtleastOneCredit() > mostStudsWithAtleastOneCredit16) {
                mostStudsWithAtleastOneCredit16 = 1.0 * ar.pc16.studentsWithAtleastOneCredit();
            }
            if (ar.pc19.studentsWithAtleastOneCredit() > mostStudsWithAtleastOneCredit19) {
                mostStudsWithAtleastOneCredit19 = 1.0 * ar.pc19.studentsWithAtleastOneCredit();
            }
            
            
            
        }

    }

    public void normalizeCredits() {
//        this.setMostCreditsByMonth();
        this.findMostStuds();

        for (YearlyStats ys : arses) {
            // pitäisi olla credits normeerattu
            ys.pc4.creditsNorm = (overallMostStuds4 / ys.pc4.amountStuds) * ys.pc4.credits;
            ys.pc7.creditsNorm = (overallMostStuds7 / ys.pc7.amountStuds) * ys.pc7.credits;
            ys.pc10.creditsNorm = (overallMostStuds10 / ys.pc10.amountStuds) * ys.pc10.credits;
            ys.pc13.creditsNorm = (overallMostStuds13 / ys.pc13.amountStuds) * ys.pc13.credits;
            ys.pc16.creditsNorm = (overallMostStuds16 / ys.pc16.amountStuds) * ys.pc16.credits;
            ys.pc19.creditsNorm = (overallMostStuds19 / ys.pc19.amountStuds) * ys.pc19.credits;
            
            // credits normeerattu, otettu pois opiskelijat jotka ei opiskellut
            ys.pc4.creditsNormActiveStudents = (mostStuds4 / ys.pc4.correctedAmountOfStuds) * ys.pc4.credits;
            ys.pc7.creditsNormActiveStudents = (mostStuds7 / ys.pc7.correctedAmountOfStuds) * ys.pc7.credits;
            ys.pc10.creditsNormActiveStudents = (mostStuds10 / ys.pc10.correctedAmountOfStuds) * ys.pc10.credits;
            ys.pc13.creditsNormActiveStudents = (mostStuds13 / ys.pc13.correctedAmountOfStuds) * ys.pc13.credits;
            ys.pc16.creditsNormActiveStudents = (mostStuds16 / ys.pc16.correctedAmountOfStuds) * ys.pc16.credits;
            ys.pc19.creditsNormActiveStudents = (mostStuds19 / ys.pc19.correctedAmountOfStuds) * ys.pc19.credits;
            
             // credits normeerattu, otettu pois opiskelijat jotka ei saaneet noppia
            ys.pc4.creditsNormOnlyStudentsWithAtleastOneCredit = (mostStudsWithAtleastOneCredit4 / ys.pc4.studentsWithAtleastOneCredit()) * ys.pc4.credits;
            ys.pc7.creditsNormOnlyStudentsWithAtleastOneCredit = (mostStudsWithAtleastOneCredit7 / ys.pc7.studentsWithAtleastOneCredit()) * ys.pc7.credits;
            ys.pc10.creditsNormOnlyStudentsWithAtleastOneCredit = (mostStudsWithAtleastOneCredit10 / ys.pc10.studentsWithAtleastOneCredit()) * ys.pc10.credits;
            ys.pc13.creditsNormOnlyStudentsWithAtleastOneCredit = (mostStudsWithAtleastOneCredit13 / ys.pc13.studentsWithAtleastOneCredit()) * ys.pc13.credits;
            ys.pc16.creditsNormOnlyStudentsWithAtleastOneCredit = (mostStudsWithAtleastOneCredit16 / ys.pc16.studentsWithAtleastOneCredit()) * ys.pc16.credits;
            ys.pc19.creditsNormOnlyStudentsWithAtleastOneCredit = (mostStudsWithAtleastOneCredit19 / ys.pc19.studentsWithAtleastOneCredit()) * ys.pc19.credits;
        }
    }
}
