package tktl.gstudies.services;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.responseobjs.CourseStatResponse;
import tktl.gstudies.responseobjs.CourseStats;

@Service
public class CoursePairStatsService {

    @Autowired
    private StatsUtils statsUtils;
    @Autowired
    private StatisticService statisticService;

    public CourseStats getCourseStatsForCoursePair(String courseId, int year, String followingCourseId) {
        System.out.println(courseId + " " + year + " " + followingCourseId);
        Date date = this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(courseId, year, year).get(0);
        System.out.println(date);
        CourseStats courseStats = new CourseStats(courseId + "-" + followingCourseId);
        Date dateOfConsequentCourse = this.statsUtils.getDateOfConsequentCource(followingCourseId, date);
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

    public List<Stud> getStudentGroupFromTwoCourses(String courseId, Date dateOfCourse, String followingCourseId, Date dateOfConsequentCourse) {
//        List<Date> dateOfCourse = this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(courseId, year, year);
//        System.out.println("EKADATE: "+dateOfCourse);
        List<Stud> studsOnCourse = this.statsUtils.getCSStudentsFromCourseWhoPassedOnDate(courseId, dateOfCourse.toString());
//        System.out.println("STUDSONCOURSE: "+studsOnCourse);
        List<Stud> studsWhoPassedBothCourses = this.statsUtils.getStudentsWhoPassedConsequentCourseOnDate(studsOnCourse, followingCourseId, dateOfCourse, dateOfConsequentCourse);
//        System.out.println("STUDSPASSED: "+studsWhoPassedBothCourses);
//        System.out.println(studsOnCourse.size());
//        System.out.println(studsWhoPassedBothCourses.size());
//        System.out.println("PROSSA: " + (1.0 * studsWhoPassedBothCourses.size() / studsOnCourse.size()) * 100);
        return studsWhoPassedBothCourses;
    }

    public static void main(String[] args) {
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "gstudies-servlet.xml", prefix + "database.xml"});
        CoursePairStatsService cpss = (CoursePairStatsService) ctx.getBean("coursePairStatsService");
        System.out.println(cpss.getCourseStatsForCoursePair("581325", 2008, "582103"));

    }
}
