
package tktl.gstudies.domain;

public class CourseValidator {
    private String tunniste;
    private String suorpvm;

    public CourseValidator() {
    }

    public CourseValidator(String tunniste, String suorpvm) {
        this.tunniste = tunniste;
        this.suorpvm = suorpvm;
    }

    public String getSuorpvm() {
        return suorpvm;
    }

    public void setSuorpvm(String suorpvm) {
        this.suorpvm = suorpvm;
    }

    public String getTunniste() {
        return tunniste;
    }

    public void setTunniste(String tunniste) {
        this.tunniste = tunniste;
    }
    
    public CourseInstance getCourseInstance(){
        return new CourseInstance(tunniste, suorpvm);
    }
    
}
