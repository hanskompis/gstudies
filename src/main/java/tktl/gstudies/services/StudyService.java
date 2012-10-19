package tktl.gstudies.services;

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
//        if (stud == null) {
//            fail++;
//            System.out.println("STUD NULL, FAIL: " + fail + " NO SUCH STUDENT: " + studentId);
//            return null;
//        } else {
//            succ++;
//            System.out.println("STUD FOUND, SUCC: " + succ);
//        }
        stud.addStudy(s);
        s.addStudent(stud);
        //      this.studRepository.save(stud);

        // System.out.println(courseObjectId);
        List<CourseObject> cos = courseObjectRepository.findByObjectId(courseObjectId);
//        if (cos == null) {
//            fail++;
//            System.out.println("COS NULL, FAIL: " + fail);
//            return null;
//        } else {
//            succ++;
//            System.out.println("COS FOUND, SUCC: " + succ);
//        }

        for(CourseObject co: cos){
            co.addStudy(s);
           s.addCourseObject(co);
        }

        //    s.addCourseObject(co);
        //        this.courseObjectRepository.save(co);


        StatusOfStudy sos = statusOfStudyRepository.findByCode(statusOfStudyCode);
//        if (sos == null) {
//            fail++;
//            System.out.println("SOS NULL, FAIL: " + fail);
//            return null;
//        } else {
//            succ++;
//            System.out.println("SOS FOUND, SUCC: " + succ);
//        }
//        sos.addStudy(s);
        s.setStatusOfStudy(sos);
        //   this.statusOfStudyRepository.save(sos);


        TypeOfStudy tos = typeOfStudyRepository.findByCode(typeOfStudyCode);
//        if (tos == null) {
//            fail++;
//            System.out.println("TOS NULL, FAIL: " + fail);
//            return null;
//        } else {
//            succ++;
//            System.out.println("TOS FOUND, SUCC: " + succ);
//        }
//        tos.addStudy(s);
        s.setTypeOfStudy(tos);
        //     this.typeOfStudyRepository.save(tos);


        return studyRepository.save(s);
    }
}
