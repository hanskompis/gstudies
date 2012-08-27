
package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.AbstractGraphicalObject;

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
    public List<String> getLines() {
        return this.lineStrings;
    }
    
}
