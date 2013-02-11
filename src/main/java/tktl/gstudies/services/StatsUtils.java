package tktl.gstudies.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.Stud;

@Service
public class StatsUtils {

    @PersistenceContext
    EntityManager em;

    public List<Stud> getCSStudentsFromCourseWhoPassedOnDate(String courseId, String dateOfAccomplishment) {
        Date paramDate = makeDate(dateOfAccomplishment);
        return em.createNamedQuery("findCSStudentsFromCourseWhoPassedOnDate")
                .setParameter("courseId", courseId)
                .setParameter("dateOfAccomplishment", paramDate)
                .getResultList();
    }

    public List<Stud> getCSStudentsFromCourseWhoFailedOnDate(String courseId, String dateOfAccomplishment) {
        Date paramDate = makeDate(dateOfAccomplishment);
        return em.createNamedQuery("findCSStudentsFromCourseWhoFailedOnDate")
                .setParameter("courseId", courseId)
                .setParameter("dateOfAccomplishment", paramDate)
                .getResultList();
    }

    public List<Stud> getCSStudentsFromCourseOnDate(String dateString, String courseId) {
        Date paramDate = this.makeDate(dateString);
        return em.createNamedQuery("findCSStudentsFromCourseOnDate").setParameter("courseId", courseId)
                .setParameter("dateOfAccomplishment", paramDate).getResultList();
    }

    public List<Date> findDatesOfCourse(String courseId, String year) {
        return em.createNamedQuery("findDatesOfCourse").setParameter("courseId", courseId).setParameter("startDate", year + "-01-01").setParameter("endDate", year + "-12-31").getResultList();
    }

    public Date findMostPopulatedCourseInstance(String courseId, String year) {
        int mostStudents = 0;
        Date dateOfMostStudents = null;
        List<Date> dates = this.findDatesOfCourse(courseId, year);
        //System.out.println(dates);
        for (Date d : dates) {
            int amountStudents = this.getCSStudentsFromCourseOnDate(d.toString(), courseId).size();
            if (amountStudents > mostStudents) {
                mostStudents = amountStudents;
                dateOfMostStudents = d;
            }
        }
        return dateOfMostStudents;
    }

    public Date makeDate(String dateString) {
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

    public List<Date> findMostPopulatedCourseInstancesBetweenYears(String courseId, int startYear, int endYear) {
        List<Date> toReturn = new ArrayList<Date>();
        for (int i = startYear; i <= endYear; i++) {
            toReturn.add(this.findMostPopulatedCourseInstance(courseId, String.valueOf(i)));
        }
        return toReturn;
    }
}
