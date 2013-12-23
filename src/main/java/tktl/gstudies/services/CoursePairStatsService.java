package tktl.gstudies.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.CourseObject;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.domain.Study;
import tktl.gstudies.exceptions.GstudiesException;
import tktl.gstudies.responseobjs.CourseStats;

@Service
public class CoursePairStatsService {

    @Autowired
    private StatsUtils statsUtils;
    @Autowired
    private StatisticService statisticService;

    //ei käytössä atm
    public CourseStats getCourseStatsForCoursePairs(String courseId, int year, int maxKkEro, String... followingCourseIds) {
        // System.out.println(courseId + " " + year + " at least " + followingCourseIds[0]);

        List<Date> dates = new ArrayList<Date>();
        for (int i = year; i <= year + maxKkEro / 12; i++) {
            dates.addAll(statsUtils.findDatesOfCourse(courseId, "" + year));
        }

        Date startDate = statsUtils.makeDate(year + "-09-01");
        Date endDate = statsUtils.makeDate(year + "-" + (9 + maxKkEro) + "-01");
        List<Date> datesToRetain = new ArrayList<Date>();
        for (Date date : dates) {
            if (date.before(startDate)) {
                continue;
            }

            if (date.after(endDate)) {
                continue;
            }

            datesToRetain.add(date);
        }

        dates = datesToRetain;



        statsUtils.findDatesOfCourse(courseId, "" + year);

        List<Stud> studentsStartingDuringYear = statsUtils.getAllCSStudsEnrollingInSemester(year + "-08-01", year + "-08-01");

        int total = studentsStartingDuringYear.size();
        System.out.println("Students starting at " + year + "-08-01: " + total);

        Map<Date, List<Stud>> students = new TreeMap<Date, List<Stud>>();

        for (Stud stud : studentsStartingDuringYear) {

            for (Study study : stud.getStudies()) {
                boolean foundCourse = false;
                for (CourseObject courseObject : study.getCourseObjects()) {
                    if (courseObject.getCourseId().equals(courseId)) {
                        foundCourse = true;
                        break;
                    }
                }

                if (!foundCourse) {
                    continue;
                }

                boolean courseDateInDates = false;
                for (Date courseDate : dates) {
                    if (courseDate.equals(study.getDateOfAccomplishment())) {
                        courseDateInDates = true;
                        break;
                    }
                }

                if (!courseDateInDates) {
                    continue;
                }

                List<Stud> passingStudents = students.get(study.getDateOfAccomplishment());
                if (passingStudents == null) {
                    passingStudents = new ArrayList<Stud>();
                    students.put(study.getDateOfAccomplishment(), passingStudents);
                }

                passingStudents.add(stud);
            }
        }
//       
        List<Stud> studentsThatHaveDoneCourseDuringTheInterval = new ArrayList<Stud>();
        for (Date date : students.keySet()) {
            studentsThatHaveDoneCourseDuringTheInterval.addAll(students.get(date));
//            System.out.println(date + "\t" + students.get(date).size());
        }

        System.out.println("CS Students that have done course " + courseId + " during interval " + startDate + " - " + endDate + ": " + studentsThatHaveDoneCourseDuringTheInterval.size());

        Date startComparisonFrom = startDate; // statsUtils.makeDate(year + "-08-01"); // statsUtils.findMostPopulatedCourseInstance(courseId, "" + year);
        System.out.println("We is looking at date : " + startComparisonFrom);
//        List<Stud> studentsWhoPassedCourse = statsUtils.getCSStudentsFromCourseWhoPassedOnDate(courseId, startComparisonFrom);
//        System.out.println("CS Students that passed: " + studentsWhoPassedCourse.size());
        List<Stud> studentsWhoPassedCourse = studentsThatHaveDoneCourseDuringTheInterval;

        // pidetään vain opiskelijat jotka aloittanut haluttuna vuotena
        studentsWhoPassedCourse.retainAll(studentsStartingDuringYear);
        System.out.println("CS Students that started during " + year + " that passed the course during interval: " + studentsWhoPassedCourse.size());


        System.out.println("");
        System.out.println("YEAR " + year);
        System.out.println("# STUDENTS PASSED " + courseId + ": " + studentsWhoPassedCourse.size() + " (" + (100.0 * studentsWhoPassedCourse.size() / total) + ")");

        // kuinka moni saa nopat tietyn kk määrän sisällä
        for (String followingCourse : followingCourseIds) {

            int passingStudents = 0;

            for (Stud stud : studentsWhoPassedCourse) {

                for (Study study : stud.getStudies()) {
                    boolean foundCourse = false;
                    for (CourseObject courseObject : study.getCourseObjects()) {
                        if (courseObject.getCourseId().equals(followingCourse)) {
                            foundCourse = true;
                            break;
                        }
                    }

                    if (!foundCourse) {
                        continue;
                    }

                    if (study.getStatusOfStudy().getCode() != 4) {
                        continue;
                    }

                    if (study.getDateOfAccomplishment().before(startComparisonFrom)) {
                        continue;
                    }

                    long diff = study.getDateOfAccomplishment().getTime() - startComparisonFrom.getTime();
                    long months = diff / 2628000000L;

                    if (months > maxKkEro) {
                        continue;
                    }

                    passingStudents++;
                }
            }

            System.out.println("# STUDENTS PASSED " + followingCourse + " " + passingStudents + " ( % from students that passed " + courseId + ": " + (100.0 * passingStudents / studentsWhoPassedCourse.size()) + ", % from all: " + (100.0 * passingStudents / total) + ")");
        }

        System.out.println("********************");
        System.out.println("********************");


        return null;












//        Date date = this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(courseId, year, year).get(0);
//
//        CourseStats courseStats = new CourseStats(courseId + "-" + followingCourseId);
//        Date dateOfConsequentCourse = this.statsUtils.getDateOfConsequentCource(followingCourseId, date);
//        System.out.println("DATE OF PRECEDING COURSE: " + date);
//        System.out.println("DATE OF CONSEQUENT COURSE: " + dateOfConsequentCourse);
//        List<Stud> students = this.getStudentGroupFromTwoCourses(courseId, date, followingCourseId, dateOfConsequentCourse);
//        courseStats.setAmountStudents(students.size());
//        for (Stud s : students) {
//            double credits7 = this.statisticService.getCreditsNMonthsSpan(s.getStudies(), dateOfConsequentCourse, 7);
//            courseStats.addCreditGainToSevenMonthsCSPassed(credits7);
//            double credits13 = this.statisticService.getCreditsNMonthsSpan(s.getStudies(), dateOfConsequentCourse, 13);
//            courseStats.addCreditGainToThirteenMonthsCSPassed(credits13);
//            double credits19 = this.statisticService.getCreditsNMonthsSpan(s.getStudies(), dateOfConsequentCourse, 19);
//            courseStats.addCreditGainToNineteenMonthsCSPassed(credits19);
//        }
//        courseStats.convertAllHashMaps();
//        this.statisticService.setAverageGrades(courseStats, dateOfConsequentCourse.toString(), students);
//        this.statisticService.setStandardDeviations(courseStats, dateOfConsequentCourse.toString(), students);
//        courseStats.calculateCreditAverages();
//        courseStats.makeCumulativeLists();
//        courseStats.setZeroAchievers();
//        return courseStats;
    }

    /**
     * muutettu ikähaarukka, palauta!
     */
    public CourseStats getCourseStatsForCoursePair(String firstCourseId, int firstYear, String followingCourseId, int followingYear) {
        if (this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(firstCourseId, firstYear, firstYear).size() == 0) {
            throw new GstudiesException("zero dates motherfucker, course: " + firstCourseId + ", year: " + firstYear);
        }
        Date date = this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(firstCourseId, firstYear, firstYear).get(0);

        CourseStats courseStats = new CourseStats(firstCourseId + "-" + followingCourseId);

        Date dateOfConsequentCourse = null;
        List<Stud> students = null;

        try {
            dateOfConsequentCourse = this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(followingCourseId, followingYear, followingYear).get(0);
//            students = this.getStudentGroupFromTwoCourses(firstCourseId, date, followingCourseId, dateOfConsequentCourse);
            students = this.getStudentGroupFromTwoCourses(firstCourseId, date, followingCourseId, dateOfConsequentCourse, firstYear);//muutettu yks param lisää

        } catch (Exception e) {
            // ohja
            if (followingCourseId.equals("582103")) {
                followingCourseId = "581326";
            }

            // ohma            
            if (followingCourseId.equals("582104")) {
                followingCourseId = "582101";
            }


            dateOfConsequentCourse = this.statsUtils.findMostPopulatedCourseInstancesBetweenYears(followingCourseId, followingYear, followingYear).get(0);
//            students = this.getStudentGroupFromTwoCourses(firstCourseId, date, followingCourseId, dateOfConsequentCourse);
            students = this.getStudentGroupFromTwoCourses(firstCourseId, date, followingCourseId, dateOfConsequentCourse, firstYear); //muutettu yks param lisää

        }


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

    public List<Stud> getStudentGroupFromTwoCourses(String courseId, Date dateOfCourse, String followingCourseId, Date dateOfConsequentCourse, int enrollmentYear) {
        Date enrollmentDate = this.statsUtils.makeDate(enrollmentYear + "-08-01");

        List<Stud> studentsOnFirstCourse = this.statsUtils.getCSStudentsFromCourseOnDate(dateOfCourse.toString(), courseId);
        studentsOnFirstCourse = this.statsUtils.getUnder22(studentsOnFirstCourse, enrollmentDate); //lisätty
//        System.out.println("studentsOnFirstCourse: "+studentsOnFirstCourse.size());

        List<Stud> studentsPassedFirstCourse = this.statsUtils.getCSStudentsFromCourseWhoPassedOnDate(courseId, dateOfCourse.toString());
        studentsPassedFirstCourse = this.statsUtils.getUnder22(studentsPassedFirstCourse, enrollmentDate);//lisätty
//        System.out.println("studentsPassedFirstCourse: "+studentsPassedFirstCourse.size());

        System.out.println("STUDENTS WHO PASSED " + courseId + ": " + studentsPassedFirstCourse.size() + " / " + studentsOnFirstCourse.size() + " (" + (100.0 * studentsPassedFirstCourse.size() / studentsOnFirstCourse.size()) + " %)");


        List<Stud> studentsOnSecondCourse = this.statsUtils.getCSStudentsFromCourseOnDate(dateOfConsequentCourse.toString(), followingCourseId);
        studentsOnSecondCourse = this.statsUtils.getUnder22(studentsOnSecondCourse, enrollmentDate); //lisätty
//        System.out.println("studentsOnSecondCourse: "+studentsOnSecondCourse.size());

        List<Stud> studentsPassedSecondCourse = this.statsUtils.getCSStudentsFromCourseWhoPassedOnDate(followingCourseId, dateOfConsequentCourse.toString());
        studentsPassedSecondCourse = this.statsUtils.getUnder22(studentsPassedSecondCourse, enrollmentDate); //lisätty
//        System.out.println("studentsPassedSecondCourse: "+studentsPassedSecondCourse.size());

        System.out.println("STUDENTS WHO PASSED " + followingCourseId + ": " + studentsPassedSecondCourse.size() + " / " + studentsOnSecondCourse.size() + " (" + (100.0 * studentsPassedSecondCourse.size() / studentsOnSecondCourse.size()) + " %)");


        List<Stud> studentsOnBothCourses = new ArrayList<Stud>(studentsOnFirstCourse);
        studentsOnBothCourses.retainAll(studentsOnSecondCourse);
        System.out.println("STUDENTS ON BOTH " + studentsOnBothCourses.size() + " (NOT NECESSARILY PASSING!)");


        List<Stud> studentsOnBothWhoPassedFirst = new ArrayList<Stud>(studentsOnBothCourses);
        studentsOnBothWhoPassedFirst.retainAll(studentsPassedFirstCourse);
        System.out.println("FROM THOSE " + studentsOnBothWhoPassedFirst.size() + " / " + studentsOnBothCourses.size() + " PASSED " + courseId + " (" + (100.0 * studentsOnBothWhoPassedFirst.size() / studentsOnBothCourses.size()) + " %)");


        List<Stud> studentsOnBothWhoPassedSecond = new ArrayList<Stud>(studentsOnBothCourses);
        studentsOnBothWhoPassedSecond.retainAll(studentsPassedSecondCourse);
        System.out.println("FROM THOSE " + studentsOnBothWhoPassedSecond.size() + " / " + studentsOnBothCourses.size() + " PASSED " + followingCourseId + " (" + (100.0 * studentsOnBothWhoPassedSecond.size() / studentsOnBothCourses.size()) + " %)");

        List<Stud> studentsOnBothWhoPassedBoth = new ArrayList<Stud>(studentsOnBothWhoPassedSecond);
        studentsOnBothWhoPassedBoth.retainAll(studentsPassedFirstCourse);
        System.out.println("FROM THOSE " + studentsOnBothWhoPassedBoth.size() + " / " + studentsOnBothCourses.size() + " PASSED BOTH " + courseId + " AND " + followingCourseId + " (" + (100.0 * studentsOnBothWhoPassedBoth.size() / studentsOnBothCourses.size()) + " %)");



//        System.out.println("STUDENTS WHO PASSED " + courseId + ": " + studentsPassedFirstCourse.size() + " (" + (100.0 * studentsPassedFirstCourse.size() / studentsOnFirstCourse.size()) + " %)");

//        
//        List<Stud> studsWhoPassedBothCourses = this.statsUtils.getStudentsWhoPassedConsequentCourseOnDate(studentsPassedFirstCourse, followingCourseId, dateOfCourse, dateOfConsequentCourse);
//        List<Stud> studentsPassedSecondCourse = this.statsUtils.getCSStudentsFromCourseWhoPassedOnDate(followingCourseId, dateOfConsequentCourse.toString());
//        
//        if (studsWhoPassedBothCourses.isEmpty()) {
//            throw new GstudiesException("Number of student who passed both courses: zero, nil. Jesus...");
//        }
//        
//        System.out.println("STUDENTS WHO PASSED BOTH: " + studsWhoPassedBothCourses.size());
//        System.out.println("PROSSA: " + (1.0 * studsWhoPassedBothCourses.size() / studentsPassedFirstCourse.size()) * 100);
        return studentsOnBothWhoPassedSecond;
    }

    public static void main(String[] args) {
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "gstudies-servlet.xml", prefix + "database.xml"});
        CoursePairStatsService cpss = (CoursePairStatsService) ctx.getBean("coursePairStatsService");
//        System.out.println(cpss.getCourseStatsForCoursePair("582103", 2009, "58131"));
        // System.out.println(cpss.getCourseStatsForCoursePair("582103", 2009, "58131",2010));
//        System.out.println(cpss.getCourseStatsForCoursePair("582103", 2009, "58160", 2010));

//        for (int monthsFromFirstCourse = 1; monthsFromFirstCourse <= 19; monthsFromFirstCourse += 2) {
//            System.out.println("");
//            System.out.println("MONTHS FROM FIRST COURSE: " + monthsFromFirstCourse);
//            System.out.println("");
//            for (int year = 2006; year < 2012; year++) {
//                // ohpe                                                                  // ohja   // ohjharj // ohma   // tira
//                cpss.getCourseStatsForCoursePairs("581325", year, monthsFromFirstCourse, "582103", "58160", "582104", "58131");
//            }
//        }
//        


//        for (int monthsFromFirstCourse = 1; monthsFromFirstCourse <= 19; monthsFromFirstCourse += 2) {
        System.out.println("");
//            System.out.println("MONTHS FROM FIRST COURSE: " + monthsFromFirstCourse);
        System.out.println("");
        for (int year = 2006; year < 2013; year++) {
            // ohpe                                                                  // ohja   // ohjharj // ohma   // tira
//                cpss.getCourseStatsForCoursePair("581325", year, monthsFromFirstCourse, "582103", "58160", "582104", "58131");
            System.out.println("");
            System.out.println("*****************");
            System.out.println("***** " + year + " *****");
            System.out.println("*****************");
            System.out.println("OHPE - OHJA");
            cpss.getCourseStatsForCoursePair("581325", year, "582103", year); // "58160", "582104", "58131");
            System.out.println("");
            System.out.println("OHPE - OHMA");
            cpss.getCourseStatsForCoursePair("581325", year, "582104", year);
            System.out.println("");
//                System.out.println("OHPE - TIRA");
//                cpss.getCourseStatsForCoursePair("581325", year, "58131", year+1);
        }
//        }
    }
}
