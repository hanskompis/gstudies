
package tktl.gstudies.responseobjs;

public class CourseCatcher {
    private String tunniste;
    private String suorpvm;

    public CourseCatcher() {
    }

    public CourseCatcher(String tunniste, String suorpvm) {//validoi tässä kurssin nimi sun muuta
        this.tunniste = tunniste;
        this.suorpvm = suorpvm;
    }
    public String getTunniste() {
        return tunniste;
    }

    public void setTunniste(String tunniste) {
        this.tunniste = tunniste;
    }

    public String getSuorpvm() {
        return suorpvm;
    }

    public void setSuorpvm(String suorpvm) {
        this.suorpvm = suorpvm;
    }
    
    
    
}
