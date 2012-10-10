
package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.repositories.StudentRepository;

@Service
public class StudentService extends GenericRepositoryService<Stud> {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @PostConstruct
    private void Init(){
        setRepository(studentRepository);
    }
    
}
