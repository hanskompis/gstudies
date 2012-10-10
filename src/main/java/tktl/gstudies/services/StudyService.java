
package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.Study;
import tktl.gstudies.repositories.StudyRepository;

@Service
public class StudyService extends GenericRepositoryService<Study> {
    
   @Autowired
   private StudyRepository studyRepository;
   
   @PostConstruct
   private void init(){
       setRepository(studyRepository);
   }
}
