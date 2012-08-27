
package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.AbstractGraphicalObject;
import tktl.gstudies.domain.Student;

@Service
public class LineServiceImpl implements LineService{
    
    private List<String> lineStrings;

    @Override
    public void addLineString(String line) {
        if(this.lineStrings==null){
            this.lineStrings = new ArrayList<String>();
        }
    }

    @Override
    public List<String> getLines(List<Student> studs) {
        return this.lineStrings;
    }

    @Override
    public void addLineString(int mx, int my, int lx, int ly) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
