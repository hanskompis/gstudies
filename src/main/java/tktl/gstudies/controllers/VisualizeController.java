
package tktl.gstudies.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tktl.gstudies.services.GraphicsService;

@Controller
public class VisualizeController {
    
    @Autowired
    @Qualifier("dummy")
    private GraphicsService graphicsService;
    
    @RequestMapping(method = RequestMethod.GET, value = "visualize", produces = "application/json")
    @ResponseBody
    public List visualize(){
        return this.graphicsService.getSumMoreDummyData();
    }
    
}
