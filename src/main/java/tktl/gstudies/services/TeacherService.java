
package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tktl.gstudies.domain.Study;
import tktl.gstudies.domain.Teacher;
import tktl.gstudies.repositories.StudyRepository;
import tktl.gstudies.repositories.TeacherRepository;

@Service
public class TeacherService extends GenericRepositoryService<Teacher> {
    
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudyRepository studyRepository;
    
    @PostConstruct
    private void init(){
        setRepository(teacherRepository);
    }
    
    @Transactional
    public Teacher save(Teacher t, Integer studyNumber){
        Study s = studyRepository.findByStudyNumber(studyNumber);
        if(s == null){
            return null;
        }
        s.addTeacher(t);
        t.setStudy(s);
        return teacherRepository.save(t);
    }
}
