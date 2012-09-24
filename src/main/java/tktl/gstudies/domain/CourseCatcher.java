
package tktl.gstudies.domain;

public class CourseCatcher {
    private String tunniste;

    public CourseCatcher() {
    }

    public CourseCatcher(String tunniste, String suorpvm) {//validoi tässä kurssin nimi sun muuta
        this.tunniste = tunniste;
    }
    public String getTunniste() {
        return tunniste;
    }

    public void setTunniste(String tunniste) {
        this.tunniste = tunniste;
    }    
}
