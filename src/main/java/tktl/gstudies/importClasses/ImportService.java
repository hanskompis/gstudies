package tktl.gstudies.importClasses;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.StatusOfStudy;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.domainForGraphics.Student;
import tktl.gstudies.repositories.JDBCRepository;
import tktl.gstudies.services.StatusOfStudyService;
import tktl.gstudies.services.StudentService;
//import tktl.gstudies.repositories.StatusOfStudyRepository;

@Service
public class ImportService{

    @Autowired
    private StatusOfStudyService statusOfStudyService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private JDBCRepository jdbcrepository;
    
    public void importStatusOfStudyObjects(){
        List<StatusOfStudy> list = this.jdbcrepository.getStatusOfStudyObjects();
        for(StatusOfStudy s : list){
            statusOfStudyService.save(s);
        }
    }

    public void importStudentObjects() {
        List<Stud> list = this.jdbcrepository.getStudentObjects();
        for(Stud s : list){
            studentService.save(s);
        }
    }
    
}
