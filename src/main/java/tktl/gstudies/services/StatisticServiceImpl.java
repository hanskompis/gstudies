package tktl.gstudies.services;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.Stud;
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
    public List<TypeOfStudy> getTypesOfStudy() {
        return em.createQuery("SELECT t FROM TypeOfStudy t").getResultList();
    }

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

public static void main(String[] args) {
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "spring-context.xml", prefix + "spring-database.xml"});
        StatisticService ss = (StatisticService) ctx.getBean("statisticServiceImpl");
        List<Stud> studs = ss.getCSStudentsFromCourseWhoPassedOnDate("581305", "2006-03-15");
        int i = 0;
        for(Stud s : studs){
            i++;
            System.out.println(""+i+" "+s);           
        }
    }
}
