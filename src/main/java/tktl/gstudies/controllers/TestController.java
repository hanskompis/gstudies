/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tktl.gstudies.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tktl.gstudies.domain.CourseInstance;
import tktl.gstudies.domain.CourseValidator;
import tktl.gstudies.domain.Query;
import tktl.gstudies.repositories.TestRepository;
import tktl.gstudies.services.GraphicsServiceImpl;

/**
 *
 * @author avihavai
 */
@Controller
public class TestController {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private GraphicsServiceImpl gsi;

    @RequestMapping("/test")
    @ResponseBody
    public List process() {
        return this.testRepository.fetchData();
    }

    @RequestMapping("/test2")
    @ResponseBody
    public String process2() {

        return "" + testRepository.list();
    }

    @RequestMapping("/test3")
    @ResponseBody
    public void process3() {

        gsi.getGraphicsData();
    }

    @RequestMapping(method = RequestMethod.POST, value = "query", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List query(@RequestBody Query q) {
        System.out.println("QQQUUUUUEEEEEERRRRYYYYYY: " + q.getQueryString());
        Long time1 = System.currentTimeMillis();
        List toReturn = this.testRepository.query(q.getQueryString());
        if (toReturn != null) {
            Long time2 = System.currentTimeMillis();
            System.out.println("SAAAAATUUUUU AJASSA: " + ((time2 - time1) / 1000) + "s");
            System.out.println("HAUSSA RIVEJÄ: " + toReturn.size());
            return toReturn;
        }
        return toReturn;
//        return this.testRepository.query(q.getQueryString());
    }

    @RequestMapping(method = RequestMethod.POST, value = "cquery", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public void cquery(@RequestBody CourseValidator c) {
        System.out.println("hePPPPPPPP");
//       List courses = this.testRepository.query("SELECT DISTINCT NIMI, SUORPVM from opinto, "
//                + "opinkohd WHERE opinto.OPINKOHD = opinkohd.OPINKOHD AND opinkohd.NIMI = "+q.getQueryString());
        
        System.out.println(c.getCourseInstance().getCourse().name()+" "+c.getCourseInstance().getSuorpvm().toString());
   
    }
}
