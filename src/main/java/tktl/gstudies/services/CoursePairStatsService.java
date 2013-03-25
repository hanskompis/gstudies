package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.exceptions.GstudiesException;
import tktl.gstudies.responseobjs.CourseStatResponse;
import tktl.gstudies.responseobjs.CourseStats;
import tktl.gstudies.responseobjs.CourseStatsResponseObj;

@Service
public class CoursePairStatsService {

    @Autowired
    private StatsUtils statsUtils;
    @Autowired
    private StatisticService statisticService;

    //ei käytössä atm
    public CourseStats getCourseStatsForCoursePair(String courseId, int year, String followingCourseId) {
        System.out.println(courseId + " " + year + " " + followingCourseId);
        Date date = this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(courseId, year, year).get(0);

        CourseStats courseStats = new CourseStats(courseId + "-" + followingCourseId);
        Date dateOfConsequentCourse = this.statsUtils.getDateOfConsequentCource(followingCourseId, date);
        System.out.println("DATE OF PRECEDING COURSE: " + date);
        System.out.println("DATE OF CONSEQUENT COURSE: " + dateOfConsequentCourse);
        List<Stud> students = this.getStudentGroupFromTwoCourses(courseId, date, followingCourseId, dateOfConsequentCourse);
        courseStats.setAmountStudents(students.size());
        for (Stud s : students) {
            double credits7 = this.statisticService.getCreditsNMonthsSpan(s.getStudies(), dateOfConsequentCourse, 7);
            courseStats.addCreditGainToSevenMonthsCSPassed(credits7);
            double credits13 = this.statisticService.getCreditsNMonthsSpan(s.getStudies(), dateOfConsequentCourse, 13);
            courseStats.addCreditGainToThirteenMonthsCSPassed(credits13);
            double credits19 = this.statisticService.getCreditsNMonthsSpan(s.getStudies(), dateOfConsequentCourse, 19);
            courseStats.addCreditGainToNineteenMonthsCSPassed(credits19);
        }
        courseStats.convertAllHashMaps();
        this.statisticService.setAverageGrades(courseStats, dateOfConsequentCourse.toString(), students);
        this.statisticService.setStandardDeviations(courseStats, dateOfConsequentCourse.toString(), students);
        courseStats.calculateCreditAverages();
        courseStats.makeCumulativeLists();
        courseStats.setZeroAchievers();
        return courseStats;
    }
    
        public CourseStats getCourseStatsForCoursePair(String firstCourseId, int firstYear, String followingCourseId, int followingYear) {
            if(this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(firstCourseId, firstYear, firstYear).size() == 0){
                throw new GstudiesException("zero dates motherfucker, course: "+firstCourseId+", year: "+firstYear);
            }
        Date date = this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(firstCourseId, firstYear, firstYear).get(0);

        CourseStats courseStats = new CourseStats(firstCourseId + "-" + followingCourseId);
        Date dateOfConsequentCourse = this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(followingCourseId, followingYear, followingYear).get(0);
        List<Stud> students = this.getStudentGroupFromTwoCourses(firstCourseId, date, followingCourseId, dateOfConsequentCourse);
        courseStats.setAmountStudents(students.size());
        for (Stud s : students) {
            double credits7 = this.statisticService.getCreditsNMonthsSpan(s.getStudies(), dateOfConsequentCourse, 7);
            courseStats.addCreditGainToSevenMonthsCSPassed(credits7);
            double credits13 = this.statisticService.getCreditsNMonthsSpan(s.getStudies(), dateOfConsequentCourse, 13);
            courseStats.addCreditGainToThirteenMonthsCSPassed(credits13);
            double credits19 = this.statisticService.getCreditsNMonthsSpan(s.getStudies(), dateOfConsequentCourse, 19);
            courseStats.addCreditGainToNineteenMonthsCSPassed(credits19);
        }
        courseStats.convertAllHashMaps();
        this.statisticService.setAverageGrades(courseStats, dateOfConsequentCourse.toString(), students);
        this.statisticService.setStandardDeviations(courseStats, dateOfConsequentCourse.toString(), students);
        courseStats.calculateCreditAverages();
        courseStats.makeCumulativeLists();
        courseStats.setZeroAchievers();
        return courseStats;
    }

    public List<Stud> getStudentGroupFromTwoCourses(String courseId, Date dateOfCourse, String followingCourseId, Date dateOfConsequentCourse) {
        List<Stud> studsOnCourse = this.statsUtils.getCSStudentsFromCourseWhoPassedOnDate(courseId, dateOfCourse.toString());
        System.out.println("STUDSONCOURSE: " + studsOnCourse.size());
        List<Stud> studsWhoPassedBothCourses = this.statsUtils.getStudentsWhoPassedConsequentCourseOnDate(studsOnCourse, followingCourseId, dateOfCourse, dateOfConsequentCourse);
        if(studsWhoPassedBothCourses.size() == 0){
            throw new GstudiesException("Number of student who passed both courses: zero, nil. Jesus...");
        }
        System.out.println("STUDSPASSEDBOTH: " + studsWhoPassedBothCourses.size());
        System.out.println("PROSSA: " + (1.0 * studsWhoPassedBothCourses.size() / studsOnCourse.size()) * 100);
        return studsWhoPassedBothCourses;
    }

    public static void main(String[] args) {
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "gstudies-servlet.xml", prefix + "database.xml"});
        CoursePairStatsService cpss = (CoursePairStatsService) ctx.getBean("coursePairStatsService");
//        System.out.println(cpss.getCourseStatsForCoursePair("582103", 2009, "58131"));
        System.out.println(cpss.getCourseStatsForCoursePair("582103", 2009, "58131",2010));
    }
}
