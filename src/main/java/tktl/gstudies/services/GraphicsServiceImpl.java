
package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.AbstractGraphicalObject;
import tktl.gstudies.domain.Rectangle;

@Service
public class GraphicsServiceImpl implements GraphicsService{
    private List<AbstractGraphicalObject> graphObjs;
    
    @Override
    public List<AbstractGraphicalObject> getDummyData() {
        this.graphObjs = new ArrayList<AbstractGraphicalObject>();
        for(int i = 1; i <= 10; i++){
            this.graphObjs.add(new Rectangle("rect", 20, (i*50), 50, 20, 5));
        }
        return graphObjs;
    }

    @Override
    public List<AbstractGraphicalObject> getSumMoreDummyData() {
        this.graphObjs = new ArrayList<AbstractGraphicalObject>();
        
        return this.graphObjs;
    }
    
}
