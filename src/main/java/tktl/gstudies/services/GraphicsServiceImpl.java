/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.AbstractGraphicalObject;
import tktl.gstudies.domain.BoxCoordinatesForLines;
import tktl.gstudies.repositories.TestRepository;

@Service
@Qualifier("real")
public class GraphicsServiceImpl implements GraphicsService {

    @Autowired
    @Qualifier("dummy")
    private LineService lineService;
    @Autowired
    private TestRepository testRepository;
    private List<AbstractGraphicalObject> graphObjs;

    @Override
    public List<AbstractGraphicalObject> getGraphicsData() {
        this.graphObjs = new ArrayList<AbstractGraphicalObject>();
        lineService.setCoords(new ArrayList<List<BoxCoordinatesForLines>>());
        return this.graphObjs;
    }
    
//    private List fetchCourseData(){
//        
//    }
    
//    private List fetchStudents(){
//        return 
//    }
}

