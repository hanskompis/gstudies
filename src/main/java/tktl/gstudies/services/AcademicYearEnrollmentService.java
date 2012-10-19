
package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.repository.AbstractRepository;
import sun.reflect.generics.tree.Tree;
import tktl.gstudies.domain.AcademicYearEnrollment;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.repositories.AcademicYearEnrollmentRepository;
import tktl.gstudies.repositories.StudRepository;

@Service
public class AcademicYearEnrollmentService extends GenericRepositoryService<AcademicYearEnrollment> {
    
    @Autowired
    private AcademicYearEnrollmentRepository academicYearEnrollmentRepository;
    
    @Autowired
    private StudRepository studRepository;
    
    @PostConstruct
    private void init(){
        setRepository(academicYearEnrollmentRepository);
    }
    
    @Transactional
    public AcademicYearEnrollment save(AcademicYearEnrollment a, Integer studentId){
        a = academicYearEnrollmentRepository.findOne(a.getId());
        Stud s = studRepository.findByStudentId(studentId);
        a.setStudent(s);

        s.addEnrollment(a);
        //studRepository.save(s);
        return academicYearEnrollmentRepository.save(a);
    }
}
