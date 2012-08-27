
package tktl.gstudies.services;

import java.util.List;
import tktl.gstudies.domain.Student;

public interface LineService {
    public void addLineString(String line);
    public void addLineString(int mx, int my, int lx, int ly);
    public List<String> getLines(List<Student> studs);


}
