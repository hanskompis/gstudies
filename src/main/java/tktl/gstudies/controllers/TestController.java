/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tktl.gstudies.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tktl.gstudies.domain.Query;
import tktl.gstudies.repositories.TestRepository;

/**
 *
 * @author avihavai
 */
@Controller
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @RequestMapping("/test")
    @ResponseBody
    public String process() {
        return "" + testRepository.count();
    }

    @RequestMapping("/test2")
    @ResponseBody
    public String process2() {
        return "" + testRepository.list();
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "query", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List query(@RequestBody Query q){        
        System.out.println("QQQUUUUUEEEEEERRRRYYYYYY: " + q.getQueryString());
        Long time1 = System.currentTimeMillis();
        List toReturn = this.testRepository.query(q.getQueryString());
        if(toReturn != null){
            Long time2 = System.currentTimeMillis();
            System.out.println("SAAAAATUUUUU AJASSA: " + ((time2-time1)/1000) +"s");
            return toReturn;
        }
        return toReturn;
//        return this.testRepository.query(q.getQueryString());
    }
}
