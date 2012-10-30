package tktl.gstudies.services;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import tktl.gstudies.responseobjs.CourseStats;

@Service
public class StatisticServiceImpl implements StatisticService {

    public List<String> acceptableGrades = Arrays.asList(new String[]{"1", "2", "3", "4", "5"});
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

    @Override
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

    @Override
    public void sortCourses(Stud s) {
        List<Study> cos = s.getStudies();
        Collections.sort(cos);
        s.setStudies(cos);
    }

    /**
     * Counts sum of credits of certain time span. Only 'normal' courses Only
     * passed courses
     *
     * @param studies list of Study-objects
     * @param startDate
     * @param timeSpan
     * @return sum of credits as double
     */
    @Override
    public double getCreditsNMonthsSpan(List<Study> studies, Date startDate, int timeSpan) {
        if (startDate == null) {
            return -666.0;
        }
        double sumCredits = 0;
        Date endDate = AddMonthsToDate(startDate, timeSpan);
        for (Study s : studies) {
            if (s.getTypeOfStudy().getCode() != 1 || s.getStatusOfStudy().getCode() != 4) {
                continue;
            }
            Date dateOfwrite = s.getDateOfwrite();
            if (dateOfwrite.after(startDate) && dateOfwrite.before(endDate)) {
                sumCredits = sumCredits + s.getCredits();
            }
        }
        return sumCredits;
    }

    @Override
    public double getAverageGradeNMonthsSpan(List<Study> studies, Date startDate, int timeSpan) {
        if (startDate == null) {
            return -666.0;
        }
        double sum = 0.0;
        int amountCourses = 0;
        double avg = 0.0;
        Date endDate = AddMonthsToDate(startDate, timeSpan);
        for (Study s : studies) {
            if (s.getTypeOfStudy().getCode() != 1 || s.getStatusOfStudy().getCode() != 4) {
                continue;
            }
            Date dateOfwrite = s.getDateOfwrite();
            if (dateOfwrite.after(startDate) && dateOfwrite.before(endDate) && acceptableGrades.contains(s.getGrade().getGrade())) {
                int grade = Integer.parseInt(s.getGrade().getGrade());
                sum = sum + grade;
                amountCourses++;
            }
        }
        if (sum == 0.0) {
            return 0.0;
        }
        
        return sum / amountCourses;
    }

    @Override
    public double getGroupAverageGradeNMonthsSpan(List<Stud> studs, Date startDate, int timeSpan) {
        double sum = 0.0;
        int amount = 0;
        for (Stud s : studs) {
            sum = sum + this.getAverageGradeNMonthsSpan(s.getStudies(), startDate, timeSpan);
            amount++;
        }
        if (sum == 0.0) {
            return 0.0;
        }
        return sum / amount;
    }

    private List<Stud> studentGroup(String groupIdentifier, String dateString, String courseId) {
        if (groupIdentifier.equals("CSPassed")) {
            return this.getCSStudentsFromCourseWhoPassedOnDate(courseId, dateString);
        } else if (groupIdentifier.equals("CSFailed")) {
            return this.getCSStudentsFromCourseWhoFailedOnDate(courseId, dateString);
        } else if (groupIdentifier.equals("OtherPassed")) {
            return this.getOtherStudentsFromCourseWhoPassedOnDate(courseId, dateString);
        } else if (groupIdentifier.equals("OtherFailed")) {
            return this.getOtherStudentsFromCourseWhoFailedOnDate(courseId, dateString);
        } else {
            return null;
        }
    }

    @Override
    public void doTheMagic(String groupIdentifier, String dateString, String courseId) {
        CourseStats courseStats = new CourseStats(groupIdentifier);

        List<Stud> students = this.studentGroup(groupIdentifier, dateString, courseId);
        courseStats.setAmountStudents(students.size());
        for (Stud s : students) {
            double credits7 = this.getCreditsNMonthsSpan(s.getStudies(), this.makeDate(dateString), 7);
            courseStats.addCreditGainToSevenMonthsCSPassed(credits7);
            double credits13 = this.getCreditsNMonthsSpan(s.getStudies(), this.makeDate(dateString), 13);
            courseStats.addCreditGainToThirteenMonthsCSPassed(credits13);
            double credits19 = this.getCreditsNMonthsSpan(s.getStudies(), this.makeDate(dateString), 19);
            courseStats.addCreditGainToNineteenMonthsCSPassed(credits19);
        }
        courseStats.setAverageGradeSevenMonths(this.getGroupAverageGradeNMonthsSpan(students, this.makeDate(dateString), 7));
        courseStats.setAverageGradeThirteenMonths(this.getGroupAverageGradeNMonthsSpan(students, this.makeDate(dateString), 13));
        courseStats.setAverageGradeNineteenMonths(this.getGroupAverageGradeNMonthsSpan(students, this.makeDate(dateString), 19));

        System.out.println(courseStats);
        //test
        List<Stud> testStuds = new ArrayList<Stud>();
        testStuds.add(students.get(0));
        testStuds.add(students.get(1));
        System.out.println(testStuds.get(0).getStudies().toString());
        System.out.println(testStuds.get(1).getStudies().toString());
        System.out.println(this.getGroupAverageGradeNMonthsSpan(testStuds, this.makeDate(dateString), 7));

        //test
    }

    //privateksi
    @Override
    public Date AddMonthsToDate(Date date, int incrementInMonths) {
        long millis = date.getTime();
        long inc = 31l * 24 * 60 * 60 * 1000 * incrementInMonths;
        millis = millis + inc;
        Date toReturn = new Date(millis);
        return toReturn;
    }

    public static void main(String[] args) {
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "spring-context.xml", prefix + "spring-database.xml"});
        StatisticService ss = (StatisticService) ctx.getBean("statisticServiceImpl");
        ss.doTheMagic("CSPassed", "2006-03-15", "581305");


        //List<Stud> studs = ss.getOtherStudentsFromCourseWhoPassedOnDate("581305", "2006-03-15");
        //List<Stud> studs = ss.getOtherStudentsFromCourseWhoFailedOnDate("581305", "2006-03-15");


    }
}
