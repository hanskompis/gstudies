package tktl.gstudies.misc;

import java.sql.Date;
import java.util.ArrayList;

public class ProducedCredits {

    String dateOfEnrollment;
    String dateOfRightToStudy;
    int credits;
    double creditsNorm;
    double creditsNormActiveStudents;
    double creditsNormOnlyStudentsWithAtleastOneCredit;
    int amountStuds;
    int neverTookStudies;
    int zeroAchievers;
    int correctedAmountOfStuds;
    int timeSpan;
    double stdev;
//    ArrayList <Double> credits7 = new ArrayList();
//    ArrayList <Double> credits13 = new ArrayList();

    
    public ProducedCredits(String dateOfEnrollment, String dateOfRightToStudy, int timeSpan) {
        this.dateOfEnrollment = dateOfEnrollment;
        this.dateOfRightToStudy = dateOfRightToStudy;
        this.timeSpan = timeSpan;
    }

    public int studentsWithAtleastOneCredit() {
        return this.amountStuds - this.zeroAchievers;
    }

    public void correctAmountOfStuds() {
        this.correctedAmountOfStuds = this.amountStuds - neverTookStudies;
    }

    @Override
    public String toString() {
        return "date of enrollment: " + dateOfEnrollment + "\n"
                + "date of right to study: " + dateOfRightToStudy + "\n"
                + "time span: " + timeSpan + "\n"
                + "amount students: " + amountStuds + "\n"
                + "never took studies: " + neverTookStudies + "\n"
                + "amount of students that attempted studies: " + correctedAmountOfStuds + "\n"
                + "took studies and has at least 1 credit: " + studentsWithAtleastOneCredit() + "\n"
                + "overall credits: " + credits + "\n"
                + "credits norm (also students who never took studies): " + creditsNorm + "\n" // pitäisi olla credits normeerattu
                + "credits norm (students that studied): " + creditsNormActiveStudents + "\n" // credits normeerattu, otettu pois opiskelijat jotka ei opiskellut
                + "credits norm (students that had at least one credit): " + creditsNormOnlyStudentsWithAtleastOneCredit + "\n" // credits normeerattu, otettu pois opiskelijat jotka ei saaneet noppia

                + "credits per overall student: " + getCreditsPerStudent() + "\n"
                + "credits per (zero achievers included): " + getCreditsPerStudentWithZeroAchievers() + "\n"
                + "credits per student (has at least one credit): " + getCreditsPerStudentIfAtLeastOneCredit() + "\n"
                + "standard deviation of credits per student: " + stdev + "\n"
                + "************************************\n";
    }

    public int numOfStudentsWhoTriedButHadNoSuccess() {
        return zeroAchievers - neverTookStudies;
    }

    public String toCSVString() {
        return dateOfEnrollment + ";" + dateOfRightToStudy + ";" + timeSpan + ";" + amountStuds + ";"
                + neverTookStudies + ";" + correctedAmountOfStuds + ";" + studentsWithAtleastOneCredit() + ";"
                + credits + ";" + creditsNorm + ";" + creditsNormActiveStudents + ";" + creditsNormOnlyStudentsWithAtleastOneCredit + ";"
                + getCreditsPerStudent() + ";" + getCreditsPerStudentWithZeroAchievers() + ";"
                + getCreditsPerStudentIfAtLeastOneCredit() + "\n";
        /*
         + studentsWithAtleastOneCredit() + ";" + numOfStudentsWhoTriedButHadNoSuccess() + ";"
         + neverTookStudies + ";" + numOfStudentsWhoTriedButHadNoSuccess() + ";" + studentsWithAtleastOneCredit() + ";" + correctedAmountOfStuds + ";" + credits + ";" + creditsNormActiveStudents + ";" + creditsNorm + ";" + getCreditsPerStudent() + ";" + getCreditsPerStudentWithZeroAchieversIfAtLeastOneCredit() + ";\n";
         */
    }

    private double getCreditsPerStudent() {
        return 1.0 * credits / amountStuds;
    }

    private double getCreditsPerStudentIfAtLeastOneCredit() {
        return 1.0 * credits / studentsWithAtleastOneCredit();
    }

    private double getCreditsPerStudentWithZeroAchievers() {
        return 1.0 * credits / correctedAmountOfStuds;
    }
}
