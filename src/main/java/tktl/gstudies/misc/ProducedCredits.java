package tktl.gstudies.misc;

import java.sql.Date;

public class ProducedCredits {

    String dateOfEnrollment;
    String dateOfRightToStudy;
    int credits;
    double creditsNorm;
    int amountStuds;
    int timeSpan;

    public ProducedCredits(String dateOfEnrollment, String dateOfRightToStudy, int timeSpan) {
        this.dateOfEnrollment = dateOfEnrollment;
        this.dateOfRightToStudy = dateOfRightToStudy;
        this.timeSpan = timeSpan;
    }
    @Override
    public String toString(){
        return "date of enrollment: "+dateOfEnrollment+"\n"
                +"date of right to study: "+dateOfRightToStudy+"\n"
                +"time span: "+timeSpan+"\n"
                +"amount students: "+amountStuds+"\n"
                +"credits: "+credits+"\n"
                +"credits norm: "+creditsNorm+"\n"
                +"************************************\n";
    }

   

}
