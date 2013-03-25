package tktl.gstudies.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.AcademicYearEnrollment;
import tktl.gstudies.domain.CourseObject;
import tktl.gstudies.domain.RightToStudy;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.domain.Study;
import tktl.gstudies.importClasses.ImportService;

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
            System.out.println("KUSI DATEN TEKO!");
        }
        return toReturn;
    }

    public List<Date> findMostPopulatedCourseInstancesBetweenYears(String courseId, int startYear, int endYear) {
        List<Date> toReturn = new ArrayList<Date>();
        for (int i = startYear; i <= endYear; i++) {
            Date popularCourseInstanceDate = this.findMostPopulatedCourseInstance(courseId, String.valueOf(i));
            if (popularCourseInstanceDate != null) {
                toReturn.add(popularCourseInstanceDate);
            }
        }
        return toReturn;
    }

    public Date getDateOfConsequentCource(String idOfConsequentCourse, Date dateOfPrecedingCourse) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String startYear = sdf.format(dateOfPrecedingCourse);
        List<Date> dates = this.findMostPopulatedCourseInstancesBetweenYears(idOfConsequentCourse, Integer.parseInt(startYear), Integer.parseInt(startYear) + 2);
//        System.out.println("POPULARDATES: " + dates);
        Date anotherDate = new Date();
        for (Date d : dates) {
//            System.out.println("DATE D: "+d);
            if (d.after(dateOfPrecedingCourse)) {
//                System.out.println("AFTER!!");
                anotherDate = d;
                break;
            }
        }
        return anotherDate;
    }

    public List<Stud> getStudentsWhoPassedConsequentCourseOnDate(List<Stud> studs, String courseId, Date dateOfPrecedingCourse, Date dateOfConsequentCourse) {
////        System.out.println("DATE DATE: "+date);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//        String startYear = sdf.format(date);
////        System.out.println("STARTYEAR: " + startYear);
        List<Stud> toReturn = new ArrayList<Stud>();
//
//        List<Date> dates = this.findMostPopulatedCourseInstancesBetweenYears(courseId, Integer.parseInt(startYear), Integer.parseInt(startYear) + 2);
////        System.out.println("POPULARDATES: " + dates);
//        Date anotherDate = new Date();
//        for (Date d : dates) {
////            System.out.println("DATE D: "+d);
//            if (d.after(date)) {
////                System.out.println("AFTER!!");
//                anotherDate = d;
//                break;
//            }
//        }
//        System.out.println("ANOTHERDATE: "+anotherDate);
        // Date dateOfConsequentCourse = this.getDateOfConsequentCource(courseId, dateOfPrecedingCourse);

        for (Stud s : studs) {
            if (containsPassedStudy(s.getStudies(), dateOfConsequentCourse, courseId)) {
                toReturn.add(s);
            }
        }
        return toReturn;
    }

    private boolean containsPassedStudy(List<Study> studies, Date d, String courseId) {
        if (studies == null) {
            System.out.println("EI OPINTOJA!!");
            return false;
        }
        for (Study s : studies) {
            if (s.getCourseObjects() == null) {
                System.out.println("EI KURSSIOBJEKTEJA!!");
                return false;
            }
            if (s.getDateOfAccomplishment() == null) {
                System.out.println("EI DATEOFACCOA");
                return false;
            }
            if (s.getStatusOfStudy() == null) {
                System.out.println("EI STATUSOFÄÄSSIÄ");
                return false;
            }
            if (courseObjectHasCorrectCourseId(s.getCourseObjects(), courseId)
                    && s.getDateOfAccomplishment().compareTo(d) == 0 && s.getStatusOfStudy().getCode().intValue() == 4) {
                return true;
            }

        }
        return false;
    }

    private boolean courseObjectHasCorrectCourseId(List<CourseObject> courseObjects, String courseId) {
        for (CourseObject co : courseObjects) {
            if (co.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }

    public List<Study> getStudiesByStudentNumber(String studentNumber) {
        return em.createNamedQuery("getStudiesBasedOnStudentNumber").setParameter("studentNumber", studentNumber).getResultList();
    }
    
    public static void main(String[] args) {
        // /home/hkeijone/gstudies/
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "gstudies-servlet.xml", prefix + "database.xml"});

        StatsUtils su = (StatsUtils) ctx.getBean("statsUtils");
        //System.out.println(su.getStudiesByStudentNumber("013546975"));
//        su.getAllCSStudsEnrollingInSemester(su.makeDate("2010-08-01"));
    }
}
