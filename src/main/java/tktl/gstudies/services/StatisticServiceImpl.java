package tktl.gstudies.services;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.CourseObject;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.domain.Study;
import tktl.gstudies.responseobjs.CourseStats;
import tktl.gstudies.responseobjs.CourseStatsResponseObj;

/**
 * Service class for producing statistical report on course instance.
 *
 * @author hkeijone
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    public List<String> acceptableGrades = Arrays.asList(new String[]{"1", "2", "3", "4", "5"});
    @Autowired
    private StatsUtils statsUtils;
    @PersistenceContext
    EntityManager em;

    @Override
    public void sortCourses(Stud s) {
        List<Study> cos = s.getStudies();
        Collections.sort(cos);
        s.setStudies(cos);
    }

    @Override
    public double getCreditsNMonthsSpan(List<Study> studies, Date startDate, int timeSpan) {
        double sumCredits = 0;
        Date endDate = AddMonthsToDate(startDate, timeSpan);
        for (Study s : studies) {
            if (s.getTypeOfStudy().getCode() != 1 || s.getStatusOfStudy().getCode() != 4) {
                continue;
            }
            Date dateOfAccomplishment = s.getDateOfAccomplishment();
            if (dateOfAccomplishment.after(startDate) && dateOfAccomplishment.before(endDate)) {
                sumCredits = sumCredits + s.getCredits();
            }
        }
        return sumCredits;
    }

    private int getAmountPassedCoursesFromStud(List<Study> studies, Date startDate, int timeSpan) {
        int amountCourses = 0;
        Date endDate = AddMonthsToDate(startDate, timeSpan);
        for (Study s : studies) {
            if (s.getTypeOfStudy().getCode() != 1 || s.getStatusOfStudy().getCode() != 4) {
                continue;
            }
            Date dateOfAccomplishment = s.getDateOfAccomplishment();
            if (dateOfAccomplishment.after(startDate) && dateOfAccomplishment.before(endDate) && acceptableGrades.contains(s.getGrade().getGrade())) {
                amountCourses++;
            }
        }
        return amountCourses;
    }

    private int getSumOfGradesFromStud(List<Study> studies, Date startDate, int timeSpan) {
        int sumGrades = 0;
        Date endDate = AddMonthsToDate(startDate, timeSpan);
        for (Study s : studies) {
            if (s.getTypeOfStudy().getCode() != 1 || s.getStatusOfStudy().getCode() != 4) {
                continue;
            }
            Date dateOfAccomplishment = s.getDateOfAccomplishment();
            if (dateOfAccomplishment.after(startDate) && dateOfAccomplishment.before(endDate) && acceptableGrades.contains(s.getGrade().getGrade())) {
                int grade = Integer.parseInt(s.getGrade().getGrade());
                sumGrades = sumGrades + grade;
            }
        }
        return sumGrades;
    }

    public double getGroupAverageGradeNMonthsSpan2(List<Stud> studs, Date startDate, int timeSpan) {
        int sumCourses = 0;
        double sumGrades = 0;
        for (Stud s : studs) {
            sumCourses = sumCourses + this.getAmountPassedCoursesFromStud(s.getStudies(), startDate, timeSpan);
            sumGrades = sumGrades + this.getSumOfGradesFromStud(s.getStudies(), startDate, timeSpan);
        }
        return sumGrades / sumCourses;
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
                Date dateOfAccomplishment = st.getDateOfAccomplishment();
                if (dateOfAccomplishment.after(startDate) && dateOfAccomplishment.before(endDate) && acceptableGrades.contains(st.getGrade().getGrade())) {
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
            return this.statsUtils.getCSStudentsFromCourseWhoPassedOnDate(courseId, dateString);
        } else if (groupIdentifier.equals("CSFailed")) {
            return this.statsUtils.getCSStudentsFromCourseWhoFailedOnDate(courseId, dateString);
        } else if (groupIdentifier.equals("CSAll")) {
            return this.statsUtils.getCSStudentsFromCourseOnDate(dateString, courseId);
        } else {
            return null;
        }
    }

    @Override
    public void setStandardDeviations(CourseStats courseStats, String dateString, List<Stud> students) {
        courseStats.setStandardDeviationGradesSevenmonths(this.getStandardDeviationOfgrades(students, this.statsUtils.makeDate(dateString), 7));
        courseStats.setStandardDeviationGradesThirteenmonths(this.getStandardDeviationOfgrades(students, this.statsUtils.makeDate(dateString), 13));
        courseStats.setStandardDeviationGradesNineteenmonths(this.getStandardDeviationOfgrades(students, this.statsUtils.makeDate(dateString), 19));
    }

    @Override
    public void setAverageGrades(CourseStats courseStats, String dateString, List<Stud> students) {
        courseStats.setAverageGradeSevenMonths(this.getGroupAverageGradeNMonthsSpan2(students, this.statsUtils.makeDate(dateString), 7));
        courseStats.setAverageGradeThirteenMonths(this.getGroupAverageGradeNMonthsSpan2(students, this.statsUtils.makeDate(dateString), 13));
        courseStats.setAverageGradeNineteenMonths(this.getGroupAverageGradeNMonthsSpan2(students, this.statsUtils.makeDate(dateString), 19));
    }

    @Override
    public CourseStats doTheMagic(String groupIdentifier, String dateString, String courseId) {
        CourseStats courseStats = new CourseStats(groupIdentifier);

        List<Stud> students = this.getStudentGroup(groupIdentifier, dateString, courseId);
        courseStats.setAmountStudents(students.size());
        for (Stud s : students) {
            double credits7 = this.getCreditsNMonthsSpan(s.getStudies(), this.statsUtils.makeDate(dateString), 7);
            courseStats.addCreditGainToSevenMonthsCSPassed(credits7);
            double credits13 = this.getCreditsNMonthsSpan(s.getStudies(), this.statsUtils.makeDate(dateString), 13);
            courseStats.addCreditGainToThirteenMonthsCSPassed(credits13);
            double credits19 = this.getCreditsNMonthsSpan(s.getStudies(), this.statsUtils.makeDate(dateString), 19);
            courseStats.addCreditGainToNineteenMonthsCSPassed(credits19);
        }
        courseStats.convertAllHashMaps();
        this.setAverageGrades(courseStats, dateString, students);
        this.setStandardDeviations(courseStats, dateString, students);
        courseStats.calculateCreditAverages();
        courseStats.makeCumulativeLists();
        courseStats.setZeroAchievers();
        return courseStats;
    }

    @Override
    public Date AddMonthsToDate(Date date, int incrementInMonths) {
        long millis = date.getTime();
        long inc = 31l * 24 * 60 * 60 * 1000 * incrementInMonths;
        millis = millis + inc;
        Date toReturn = new Date(millis);
        return toReturn;
    }

    @Override
    public int[] getGradeDistribution(List<Stud> studs, String courseId, String dateString) {
        int kurssisuoritukset = 0;
        int[] grades = {0, 0, 0, 0, 0};
        for (Stud stud : studs) {
            for (Study study : stud.getStudies()) {
                if (study == null || study.getDateOfAccomplishment() == null || study.getStatusOfStudy().getCode() != 4) {
                    continue;
                }
                for (CourseObject co : study.getCourseObjects()) {
                    if (co == null || co.getCourseId() == null) {
                        continue;
                    }
                    if (co.getCourseId().equals(courseId) && study.getDateOfAccomplishment().equals(this.statsUtils.makeDate(dateString))) {
                        String grade = study.getGrade().getGrade();
                        if (acceptableGrades.contains(grade)) {
                            int g = Integer.parseInt(grade);
                            int amount = grades[g - 1];
                            amount++;
                            grades[g - 1] = amount;
                            kurssisuoritukset++;
                            //                      System.out.println(stud.getStudentId() + ": " +grade);
                        }
                    }
                }
            }
        }
        return grades;
    }

    @Override
    public CourseStatsResponseObj getData(String dateString, String courseId) {
        CourseStatsResponseObj statsResponseObj = new CourseStatsResponseObj();
        statsResponseObj.setDateOfAccomplishment(dateString);
        statsResponseObj.setCourseId(courseId);
        statsResponseObj.addCourseStatsObj(this.doTheMagic("CSPassed", dateString, courseId));
        statsResponseObj.addCourseStatsObj(this.doTheMagic("CSFailed", dateString, courseId));
        statsResponseObj.addCourseStatsObj(this.doTheMagic("CSAll", dateString, courseId));
        statsResponseObj.countPercentages();
        List<Stud> studs = this.statsUtils.getCSStudentsFromCourseWhoPassedOnDate(courseId, dateString);
        statsResponseObj.setCSCourseGrades(this.getGradeDistribution(studs, courseId, dateString));

        return statsResponseObj;
    }

    @Override
    public List<CourseStatsResponseObj> getAllDataFromCourseBetweenYears(String courseId, int startYear, int endYear) {
        List<Date> dates = this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(courseId, startYear, endYear);

        List<CourseStatsResponseObj> toReturn = new ArrayList<CourseStatsResponseObj>();
        for (Date d : dates) {
            toReturn.add(this.getData(d.toString(), courseId));
        }
        return toReturn;
    }

    private List<Stud> getCSStudents() {
        return em.createNamedQuery("getAllCSStuds").getResultList();
    }

    public static void main(String[] args) {
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "spring-context.xml", prefix + "spring-database.xml"});
        StatisticService ss = (StatisticService) ctx.getBean("statisticServiceImpl");

        //  System.out.println("KOKOHOITO!!!!!!!!!!!!!!!!!!! \n" + ss.getAllDataFromCourseBetweenYears("582206", 2006, 2010).toString());


        CourseStatsResponseObj statsResponseObj = new CourseStatsResponseObj();
        statsResponseObj = ss.getData("2010-12-16", "582206");
        //System.out.println(statsResponseObj.getCourseStatsObjs().get(0).getCreditGainsSevenMonths().toString());
        System.out.println(statsResponseObj);
    }
}
