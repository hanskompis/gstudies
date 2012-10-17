
package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.repositories.StudRepository;

@Service
public class StudentService extends GenericRepositoryService<Stud> {
    
    @Autowired
    private StudRepository studentRepository;
    
    @PostConstruct
    private void Init(){
        setRepository(studentRepository);
    }
    
//    @Override //TODO:misses alot 
//    public Stud save(Stud s){
//        return this.studentRepository.save(s);
//    }
    
}
