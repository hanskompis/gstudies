package tktl.gstudies.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tktl.gstudies.services.GraphicsService;
import tktl.gstudies.services.LineService;

@Controller
public class VisualizeController {

    @Autowired
    @Qualifier("dummy")
    private GraphicsService graphicsService;
    @Autowired
    @Qualifier("dummy")
    private LineService lineService;

    @RequestMapping(method = RequestMethod.GET, value = "visualize", produces = "application/json")
    @ResponseBody
    public List visualize() {
        return this.graphicsService.getSumMoreDummyData();
    }

//    @RequestMapping(method = RequestMethod.GET, value = "paths", produces = "application/json")
//    @ResponseBody
//    public List paths() {
//       // System.out.println(this.lineService.getSumPathData());
//        return this.lineService.getSumPathData();
//    }
    @RequestMapping(method = RequestMethod.GET, value = "paths", produces = "application/json")
    @ResponseBody
    public List paths() {
        // System.out.println(this.lineService.getSumPathData());
        return this.lineService.getSumPathData();
    }
}
