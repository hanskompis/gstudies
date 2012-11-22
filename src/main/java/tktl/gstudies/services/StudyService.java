package tktl.gstudies.services;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tktl.gstudies.domain.CourseObject;
import tktl.gstudies.domain.StatusOfStudy;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.domain.Study;
import tktl.gstudies.domain.TypeOfStudy;
import tktl.gstudies.repositories.CourseObjectRepository;
import tktl.gstudies.repositories.StatusOfStudyRepository;
import tktl.gstudies.repositories.StudRepository;
import tktl.gstudies.repositories.StudyRepository;
import tktl.gstudies.repositories.TypeOfStudyRepository;

@Service
public class StudyService extends GenericRepositoryService<Study> {

    int succ = 0;
    int fail = 0;
    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private StudRepository studRepository;
    @Autowired
    private CourseObjectRepository courseObjectRepository;
    @Autowired
    private StatusOfStudyRepository statusOfStudyRepository;
    @Autowired
    private TypeOfStudyRepository typeOfStudyRepository;

    @PostConstruct
    private void init() {
        setRepository(studyRepository);
    }

    @Transactional
    public Study save(Study s, Integer studentId, Integer courseObjectId, Integer statusOfStudyCode, Integer typeOfStudyCode) {
        s = studyRepository.findOne(s.getId());

        Stud stud = studRepository.findByStudentId(studentId);
        stud.addStudy(s);
        s.setStudent(stud);
        // System.out.println(courseObjectId);
        List<CourseObject> cos = courseObjectRepository.findByObjectId(courseObjectId);

        for(CourseObject co: cos){
            co.addStudy(s);
           s.addCourseObject(co);
        }

        StatusOfStudy sos = statusOfStudyRepository.findByCode(statusOfStudyCode);

        s.setStatusOfStudy(sos);

        TypeOfStudy tos = typeOfStudyRepository.findByCode(typeOfStudyCode);

        s.setTypeOfStudy(tos);

        return s;

    }
    @Transactional
    public void addDateOfAccomplishment(Integer studyNumber, Date dateOfAccomplishment){
        Study s = studyRepository.findByStudyNumber(studyNumber);
        if(s == null){
            fail++;
            System.out.println("fail: "+fail);
            return;
        }
        
        s.setDateOfAccomplishment(dateOfAccomplishment);
        succ++;
        System.out.println("succ: "+succ);
    }
}
