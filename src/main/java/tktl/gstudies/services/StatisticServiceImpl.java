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
import org.apache.commons.math3.stat.descriptive.moment.Variance;
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
import tktl.gstudies.responseobjs.CourseStatsResponseObj;

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
//        if (startDate == null) {
//            return -666.0;
//        }
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
//        if (startDate == null) {
//            return -666.0;
//        }
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
        double avg = 0.0;
        int amount = 0;
        for (Stud s : studs) {
            sum = sum + this.getAverageGradeNMonthsSpan(s.getStudies(), startDate, timeSpan);
            amount++;
        }
        avg = sum / amount;
        if (avg == Double.NaN) {
            return 0.0;
        } else {
            return avg;
        }
    }

    @Override
    public double getStandardDeviationOfgrades(List<Stud> studs, Date startDate, int timeSpan) {
        ArrayList<Double> values = new ArrayList();
        Date endDate = AddMonthsToDate(startDate, timeSpan);
        for (Stud s : studs) {
            for (Study st : s.getStudies()) {
                if (st.getTypeOfStudy().getCode() != 1 || st.getStatusOfStudy().getCode() != 4) {
                    continue;
                }
                Date dateOfwrite = st.getDateOfwrite();
                if (dateOfwrite.after(startDate) && dateOfwrite.before(endDate) && acceptableGrades.contains(st.getGrade().getGrade())) {
                    values.add(Double.parseDouble(st.getGrade().getGrade()));
                }
            }

        }
        double[] arr = new double[values.size()];
        for (int i = 0; i < values.size(); i++) {
            arr[i] = values.get(i);
        }
        double variance = org.apache.commons.math3.stat.StatUtils.variance(arr);
        return Math.sqrt(variance);
    }

    private List<Stud> getStudentGroup(String groupIdentifier, String dateString, String courseId) {
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

    private void setStandardDeviations(CourseStats courseStats, String dateString, List<Stud> students) {
        courseStats.setStandardDeviationGradesSevenmonths(this.getStandardDeviationOfgrades(students, this.makeDate(dateString), 7));
        courseStats.setStandardDeviationGradesThirteenmonths(this.getStandardDeviationOfgrades(students, this.makeDate(dateString), 13));
        courseStats.setStandardDeviationGradesNineteenmonths(this.getStandardDeviationOfgrades(students, this.makeDate(dateString), 19));
    }

    private void setAverageGrades(CourseStats courseStats, String dateString, List<Stud> students) {
        courseStats.setAverageGradeSevenMonths(this.getGroupAverageGradeNMonthsSpan(students, this.makeDate(dateString), 7));
        courseStats.setAverageGradeThirteenMonths(this.getGroupAverageGradeNMonthsSpan(students, this.makeDate(dateString), 13));
        courseStats.setAverageGradeNineteenMonths(this.getGroupAverageGradeNMonthsSpan(students, this.makeDate(dateString), 19));
    }

    @Override
    public CourseStats doTheMagic(String groupIdentifier, String dateString, String courseId) {
        CourseStats courseStats = new CourseStats(groupIdentifier);

        List<Stud> students = this.getStudentGroup(groupIdentifier, dateString, courseId);
        courseStats.setAmountStudents(students.size());
        for (Stud s : students) {
            double credits7 = this.getCreditsNMonthsSpan(s.getStudies(), this.makeDate(dateString), 7);
            courseStats.addCreditGainToSevenMonthsCSPassed(credits7);
            double credits13 = this.getCreditsNMonthsSpan(s.getStudies(), this.makeDate(dateString), 13);
            courseStats.addCreditGainToThirteenMonthsCSPassed(credits13);
            double credits19 = this.getCreditsNMonthsSpan(s.getStudies(), this.makeDate(dateString), 19);
            courseStats.addCreditGainToNineteenMonthsCSPassed(credits19);
        }

        this.setAverageGrades(courseStats, dateString, students);
        this.setStandardDeviations(courseStats, dateString, students);

        //System.out.println(courseStats);
        return courseStats;
        //test
//        List<Stud> testStuds = new ArrayList<Stud>();
//        testStuds.add(students.get(0));
//        testStuds.add(students.get(1));
//        System.out.println(testStuds.get(0).getStudies().toString());
//        System.out.println(testStuds.get(1).getStudies().toString());
//        System.out.println(this.getGroupAverageGradeNMonthsSpan(testStuds, this.makeDate(dateString), 7));

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
    
    @Override
    public CourseStatsResponseObj getData(){
        CourseStatsResponseObj statsResponseObj = new CourseStatsResponseObj();
        statsResponseObj.addCourseStatsObj(this.doTheMagic("CSPassed", "2006-03-15", "581305"));
        statsResponseObj.addCourseStatsObj(this.doTheMagic("CSFailed", "2006-03-15", "581305"));
        statsResponseObj.addCourseStatsObj(this.doTheMagic("OtherPassed", "2006-03-15", "581305"));
        statsResponseObj.addCourseStatsObj(this.doTheMagic("OtherFailed", "2006-03-15", "581305"));
        statsResponseObj.countPercentages();
        return statsResponseObj;
    }

    public static void main(String[] args) {
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "spring-context.xml", prefix + "spring-database.xml"});
        StatisticService ss = (StatisticService) ctx.getBean("statisticServiceImpl");
        CourseStatsResponseObj statsResponseObj = new CourseStatsResponseObj();
        statsResponseObj.addCourseStatsObj(ss.doTheMagic("CSPassed", "2006-03-15", "581305"));
        statsResponseObj.addCourseStatsObj(ss.doTheMagic("CSFailed", "2006-03-15", "581305"));
        statsResponseObj.addCourseStatsObj(ss.doTheMagic("OtherPassed", "2006-03-15", "581305"));
        statsResponseObj.addCourseStatsObj(ss.doTheMagic("OtherFailed", "2006-03-15", "581305"));
        statsResponseObj.countPercentages();
        System.out.println(statsResponseObj);
        //List<Stud> studs = ss.getOtherStudentsFromCourseWhoPassedOnDate("581305", "2006-03-15");
        //List<Stud> studs = ss.getOtherStudentsFromCourseWhoFailedOnDate("581305", "2006-03-15");


    }
}
