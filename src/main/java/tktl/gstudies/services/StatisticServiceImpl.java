package tktl.gstudies.services;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.CourseObject;
import tktl.gstudies.domain.RightToStudy;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.domain.Study;
import tktl.gstudies.domain.TypeOfStudy;
import tktl.gstudies.importClasses.ImportService;

@Service
public class StatisticServiceImpl implements StatisticService {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Stud> getCSStudentsFromCourseWhoPassedOnDate(String courseId, String dateOfWrite) {
        Date paramDate = makeDate(dateOfWrite);
        return em.createNamedQuery("findCSStudentsFromCourseWhoPassedOnDate")
                .setParameter("courseId", courseId)
                .setParameter("dateOfwrite", paramDate)
                .getResultList();
    }

    @Override
    public List<Stud> getOtherStudentsFromCourseWhoPassedOnDate(String courseId, String dateOfWrite) {
        Date paramDate = makeDate(dateOfWrite);
        return em.createNamedQuery("findOtherStudentsFromCourseWhoPassedOnDate")
                .setParameter("courseId", courseId)
                .setParameter("dateOfwrite", paramDate)
                .getResultList();
    }

    @Override
    public List<Stud> getCSStudentsFromCourseWhoFailedOnDate(String courseId, String dateOfWrite) {
        Date paramDate = makeDate(dateOfWrite);
        return em.createNamedQuery("findCSStudentsFromCourseWhoFailedOnDate")
                .setParameter("courseId", courseId)
                .setParameter("dateOfwrite", paramDate)
                .getResultList();
    }
    
    
    @Override
    public List<Stud> getOtherStudentsFromCourseWhoFailedOnDate(String courseId, String dateOfWrite) {
        Date paramDate = makeDate(dateOfWrite);
        return em.createNamedQuery("findOtherStudentsFromCourseWhoFailedOnDate")
                .setParameter("courseId", courseId)
                .setParameter("dateOfwrite", paramDate)
                .getResultList();
    }

    @Override
    public List<TypeOfStudy> getTypesOfStudy() {
        return em.createQuery("SELECT t FROM TypeOfStudy t").getResultList();
    }

//    @Override
//    public List<Stud> getCSStudentsFromCourseWhoFailedOnDate(String courseId, String dateOfWrite) {
//        Date paramDate = makeDate(dateOfWrite);
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Stud> cq = cb.createQuery(Stud.class);
//        cq.distinct(true);
//
//        Root<Stud> stud = cq.from(Stud.class);
//        
//        Join<Stud,Study> study = stud.join("studies");
//        Join<Study,CourseObject> courseObject = study.join("courseObjects");
//        Join<Stud,RightToStudy> rightToStudy = stud.join("rightsToStudy");
//        cq.where(cb.equal(courseObject.get("courseId"), "581305"));
//
//        cq.where(cb.equal(study.get("dateOfwrite"), this.makeDate("2006-03-15")));
//      //  cq.where(cb.equal(rightToStudy.get("mainSubject"), "Tietojenkäsittelytiede"));
//        cq.select(stud);
//        TypedQuery tq = em.createQuery(cq);
//        return tq.getResultList();   
//    }
    private Date makeDate(String dateString) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date toReturn = new Date();
        try {
            toReturn = df.parse(dateString);
            return toReturn;
        } catch (ParseException e) {
            System.out.println("KUSI!");
        }
        return toReturn;
    }
    
    private Stud sortCourses(Stud s){
       List<Study> cos = s.getStudies();
//       Collections.sort(cos);
       
    }

    public static void main(String[] args) {
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "spring-context.xml", prefix + "spring-database.xml"});
        StatisticService ss = (StatisticService) ctx.getBean("statisticServiceImpl");
        List<Stud> studs = ss.getCSStudentsFromCourseWhoPassedOnDate("58131", "2006-06-02");
        //List<Stud> studs = ss.getOtherStudentsFromCourseWhoPassedOnDate("581305", "2006-03-15");
        //List<Stud> studs = ss.getOtherStudentsFromCourseWhoFailedOnDate("581305", "2006-03-15");
        
//        int i = 0;
//        for (Stud s : studs) {
//            i++;
//            System.out.println("" + i + " " + s);
//        }
    }

}
