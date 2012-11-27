
package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.CourseObject;
import tktl.gstudies.repositories.CourseObjectRepository;

/**
 * Repository-service -class for CourseObject
 *
 * @author hkeijone
 */
@Service
public class CourseObjectService extends GenericRepositoryService<CourseObject> {
    @Autowired
    private CourseObjectRepository courseObjectRepository;
    
    @PostConstruct
    private void init(){
        setRepository(courseObjectRepository);
    }
}
