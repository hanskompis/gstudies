package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tktl.gstudies.domain.AcademicYearEnrollment;
import tktl.gstudies.domain.RightToStudy;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.repositories.RightToStudyRepository;
import tktl.gstudies.repositories.StudRepository;

@Service
public class RightToStudyService extends GenericRepositoryService<RightToStudy> {

    @Autowired
    private RightToStudyRepository rightToStudyRepository;
        
    @Autowired
    private StudRepository studRepository;

    @PostConstruct
    private void init() {
        setRepository(rightToStudyRepository);
    }

    @Transactional
    public RightToStudy save(RightToStudy r, Integer studentId) {
        r = rightToStudyRepository.findOne(r.getId());
        Stud s = studRepository.findByStudentId(studentId);
        r.setStudent(s);
        s.addRightToStudy(r);
        //studRepository.save(s);
        return rightToStudyRepository.save(r);
    }
}
