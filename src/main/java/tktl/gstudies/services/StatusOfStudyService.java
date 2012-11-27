
package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.StatusOfStudy;
import tktl.gstudies.repositories.StatusOfStudyRepository;

/**
 * Repository-service -class for StatusOfStudy
 *
 * @author hkeijone
 */
@Service
public class StatusOfStudyService extends GenericRepositoryService<StatusOfStudy> {
    
    @Autowired
    private StatusOfStudyRepository statusOfStudyRepository;
    
    @PostConstruct
    private void init(){
        setRepository(statusOfStudyRepository);
    }
}
