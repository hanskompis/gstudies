package tktl.gstudies.services;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tktl.gstudies.domain.Grade;
import tktl.gstudies.domain.Study;
import tktl.gstudies.repositories.GradeRepository;
import tktl.gstudies.repositories.StudyRepository;

/**
 * Repository-service -class for Grade
 *
 * @author hkeijone
 */
@Service
public class GradeService extends GenericRepositoryService<Grade> {

    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StudyRepository studyRepository;

    @PostConstruct
    private void init() {
        setRepository(gradeRepository);
    }

    @Transactional
    public Grade save(Grade g, Integer studyNumber) {
        g = this.gradeRepository.findOne(g.getId());
        Study s = this.studyRepository.findByStudyNumber(studyNumber);
        if (s == null) {
            return null;
        }
        s.setGrade(g);
        g.setStudy(s);
        return g;
    }
}
