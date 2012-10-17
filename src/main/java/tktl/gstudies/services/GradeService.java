
package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.Grade;
import tktl.gstudies.repositories.GradeRepository;

@Service
public class GradeService extends GenericRepositoryService<Grade> {
    
    @Autowired
    private GradeRepository gradeRepository;
    
    @PostConstruct
    private void init(){
        setRepository(gradeRepository);
    }
    //TODO: overridaa save
}
