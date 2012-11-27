package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.TypeOfStudy;
import tktl.gstudies.repositories.TypeOfStudyRepository;

/**
 * Repository-service -class for TypeOfStudy
 *
 * @author hkeijone
 */
@Service
public class TypeOfStudyService extends GenericRepositoryService<TypeOfStudy> {

    @Autowired
    private TypeOfStudyRepository typeOfStudyRepository;

    @PostConstruct
    private void init() {
        setRepository(typeOfStudyRepository);
    }
}
