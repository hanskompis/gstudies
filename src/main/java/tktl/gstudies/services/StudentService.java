
package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.repositories.StudRepository;

/**
 * Repository-service -class for Student
 *
 * @author hkeijone
 */
@Service
public class StudentService extends GenericRepositoryService<Stud> {
    
    @Autowired
    private StudRepository studentRepository;
    
    @PostConstruct
    private void Init(){
        setRepository(studentRepository);
    }
    
    public void setStudentNumber(String studentNumber, Integer StudentId){
        Stud s = this.studentRepository.findByStudentId(StudentId);
        if(s == null){
            return;
        }
        s.setStudentNumber(studentNumber); 
        this.studentRepository.save(s);
    }
}
