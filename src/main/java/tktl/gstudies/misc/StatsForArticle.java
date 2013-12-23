package tktl.gstudies.misc;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.AcademicYearEnrollment;
import tktl.gstudies.domain.RightToStudy;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.domain.Study;
import tktl.gstudies.services.StatisticService;
import tktl.gstudies.services.StatsUtils;
import org.apache.commons.math3.*;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

@Service
public class StatsForArticle {

    boolean gotZeroAchievers = false;
    @Autowired
    private StatsUtils statsUtils;
    @Autowired
    private StatisticService statisticService;
    @PersistenceContext
    EntityManager em;

    private AcademicYearEnrollment getFirstEnrollment(List<AcademicYearEnrollment> enrollments) {
        if (enrollments.size() == 0) {
            return null;
        }
        AcademicYearEnrollment first = enrollments.get(0);
        for (AcademicYearEnrollment a : enrollments) {
            if (a.getStartDate().before(first.getStartDate()) && a.getType().equals("Läsnäoleva")) {
                first = a;
            }
        }
        return first;
    }

    private RightToStudy getFirstRightToStudy(List<RightToStudy> ritesToStudy) {
        if (ritesToStudy.size() == 0) {
            return null;
        }
        RightToStudy first = ritesToStudy.get(0);
        for (RightToStudy r : ritesToStudy) {
            if (!r.getMainSubject().equals("Tietojenkäsittelytiede")) {
                continue;
            }
            if (r.getStartingDate().before(first.getStartingDate())) {
                first = r;
            }
        }
        return first;
    }

    public List<Stud> getAllCSStudsEnrollingInSemester(String dateOfEnrollment, String dateOfRightToStudy) {
        Date ofEnrollment = statsUtils.makeDate(dateOfEnrollment);
        Date ofRiteToStudy = statsUtils.makeDate(dateOfRightToStudy);
        List<Stud> studs = em.createNamedQuery("getAllCSStuds").getResultList();
        List<Stud> toReturn = new ArrayList<Stud>();
        for (Stud s : studs) {
            if (this.getFirstEnrollment(s.getEnrollments()) != null
                    && this.getFirstEnrollment(s.getEnrollments()).getStartDate().equals(ofEnrollment)
                    && this.getFirstRightToStudy(s.getRightsToStudy()).getStartingDate().equals(ofRiteToStudy)
                    && this.getFirstRightToStudy(s.getRightsToStudy()) != null) {
                toReturn.add(s);
            }
        }
        return toReturn;
    }

    public ProducedCredits getProducedCreditsNMonthsSpan(String dateOfEnrollment, String dateOfRightToStudy, int timeSpan, int minAge, int maxAge) {
        ProducedCredits pc = new ProducedCredits(dateOfEnrollment, dateOfRightToStudy, timeSpan);
        List<Stud> studs = this.getAllCSStudsEnrollingInSemester(dateOfEnrollment, dateOfRightToStudy);
        System.out.println(dateOfEnrollment + ", total " + studs.size() + ", limiting with max age: " + maxAge);

        int students = 0;
        int zeroAchievers = 0;
        int neverStartedAtAll = 0;
        int sum = 0;
        ArrayList<Double> individualCreditsWithZeros = new ArrayList();

        for (Stud s : studs) {
            int age = s.getAge(statsUtils.makeDate(dateOfEnrollment.substring(0, 4) + "-12-31"));

            if (minAge != -1 && maxAge != -1) {
                if (age > maxAge) {
                    continue;
                }

                if (age < minAge) {
                    continue;
                }
            }

            if (minAge != -1) {
                if (age < minAge) {
                    continue;
                }
            }

            if (maxAge != -1) {
                if (age > maxAge) {
                    continue;
                }
            }
            //cs vai kaikki opinnot?
//            double amountCredits = this.statisticService.getCreditsNMonthsSpan(s.getStudies(), this.statsUtils.makeDate(dateOfEnrollment), timeSpan);
            double amountCredits = this.statisticService.getCSCreditsNMonthsSpan(s.getStudies(), this.statsUtils.makeDate(dateOfEnrollment), timeSpan);
//            individualCreditsWithZeros.add(amountCredits);

//            if (amountCredits == 0.0) {
//                zeroAchievers++;
//            }
//
//            if (s.getStudies().isEmpty()) {
//                neverStartedAtAll++;
//            }
//
//            sum += amountCredits;
//            students++;
            if (amountCredits == 0.0 && s.getStudies().size() != 0) {
                zeroAchievers++;
                students++;
                sum += amountCredits;
//                System.out.println(amountCredits);
//                individualCreditsWithZeros.add(amountCredits);

            } else if (s.getStudies().isEmpty()) {
                neverStartedAtAll++;
                sum += amountCredits;
                students++;

            } else {
                individualCreditsWithZeros.add(amountCredits);
                sum += amountCredits;
                students++;
                System.out.println(amountCredits);
            }

        }

        //poistetaan nollasuoritukset pois krediiteistä
//        for (Iterator<Double> i = individualCreditsWithZeros.iterator(); i.hasNext();) {
//            Double d = i.next();
//            if (d == 0.0) {
//                i.remove();
//            }
//        }
        //natiivi-double-taulukko apachen standarddeviin
        double[] individualCreditsWithoutZeros = new double[individualCreditsWithZeros.size()];

        for (int i = 0; i < individualCreditsWithZeros.size(); i++) {
            individualCreditsWithoutZeros[i] = individualCreditsWithZeros.get(i);
        }

        StandardDeviation d = new StandardDeviation();
        pc.stdev = d.evaluate(individualCreditsWithoutZeros);

        pc.zeroAchievers = zeroAchievers;
//        System.out.println("Amount of students: " + students + " when max age: " + maxAge);
        pc.neverTookStudies = neverStartedAtAll;
        pc.amountStuds = students;
        pc.credits = sum;
        return pc;
    }

    public ProducedCredits getProducedCreditsNMonthsSpan(String dateOfEnrollment, String dateOfRightToStudy, int timeSpan) {
        return getProducedCreditsNMonthsSpan(dateOfEnrollment, dateOfRightToStudy, timeSpan, -1, -1);
    }

//        public ProducedCredits getProducedCSCreditsNMonthsSpan(String dateOfEnrollment, String dateOfRightToStudy, int timeSpan) {
//        ProducedCredits pc = new ProducedCredits(dateOfEnrollment, dateOfRightToStudy, timeSpan);
//        List<Stud> studs = this.getAllCSStudsEnrollingInSemester(dateOfEnrollment, dateOfRightToStudy);
//        pc.amountStuds = studs.size();
//        int sum = 0;
//        for (Stud s : studs) {
////            double amountCredits = this.statisticService.getCreditsNMonthsSpan(s.getStudies(), this.statsUtils.makeDate(dateOfEnrollment), timeSpan);
//            double amountCredits = this.statisticService.getCSCreditsNMonthsSpan(s.getStudies(), this.statsUtils.makeDate(dateOfEnrollment), timeSpan);
//
//            sum += amountCredits;
//        }
//        pc.credits = sum;
//        return pc;
//    }
//
//    public ProducedCredits getProducedCSCreditsNMonthsSpan(String dateOfEnrollment, String dateOfRightToStudy, int timeSpan) {
//        ProducedCredits pc = new ProducedCredits(dateOfEnrollment, dateOfRightToStudy, timeSpan);
//        List<Stud> studs = this.getAllCSStudsEnrollingInSemester(dateOfEnrollment, dateOfRightToStudy);
//        pc.amountStuds = studs.size();
//        int sum = 0;
//        for (Stud s : studs) {
//            sum += this.statisticService.getCreditsNMonthsSpan(s.getStudies(), this.statsUtils.makeDate(dateOfEnrollment), timeSpan);
////            sum += this.statisticService.getCSCreditsNMonthsSpan(s.getStudies(), this.statsUtils.makeDate(dateOfEnrollment), timeSpan);
//        }
//        pc.credits = sum;
//        return pc;
//    }
    public YearlyStats getReport(String dateOfEnrollment, String dateOfRightToStudy, int minAge, int maxAge) {
        YearlyStats ys = new YearlyStats();
        ys.pc4 = this.getProducedCreditsNMonthsSpan(dateOfEnrollment, dateOfRightToStudy, 4, minAge, maxAge);
        ys.pc4.correctAmountOfStuds();

        ys.pc7 = this.getProducedCreditsNMonthsSpan(dateOfEnrollment, dateOfRightToStudy, 7, minAge, maxAge);
        ys.pc7.correctAmountOfStuds();
//
        ys.pc10 = this.getProducedCreditsNMonthsSpan(dateOfEnrollment, dateOfRightToStudy, 10, minAge, maxAge);
        ys.pc10.correctAmountOfStuds();

        ys.pc13 = this.getProducedCreditsNMonthsSpan(dateOfEnrollment, dateOfRightToStudy, 13, minAge, maxAge);
        ys.pc13.correctAmountOfStuds();

        ys.pc16 = this.getProducedCreditsNMonthsSpan(dateOfEnrollment, dateOfRightToStudy, 16, minAge, maxAge);
        ys.pc16.correctAmountOfStuds();

        ys.pc19 = this.getProducedCreditsNMonthsSpan(dateOfEnrollment, dateOfRightToStudy, 19, minAge, maxAge);
        ys.pc19.correctAmountOfStuds();

        return ys;
    }

    public YearlyStats getReport(String dateOfEnrollment, String dateOfRightToStudy) {
        return getReport(dateOfEnrollment, dateOfRightToStudy, -1, -1);
    }

    public Shebang getWholeShebang(int minAge, int maxAge) {
        Shebang s = new Shebang();
        s.arses.add(getReport("2006-08-01", "2006-08-01", minAge, maxAge));
        s.arses.add(getReport("2007-08-01", "2007-08-01", minAge, maxAge));
        s.arses.add(getReport("2008-08-01", "2008-08-01", minAge, maxAge));
        s.arses.add(getReport("2009-08-01", "2009-08-01", minAge, maxAge));
        s.arses.add(getReport("2010-08-01", "2010-08-01", minAge, maxAge));
        s.arses.add(getReport("2011-08-01", "2011-08-01", minAge, maxAge));
        s.arses.add(getReport("2012-08-01", "2012-08-01", minAge, maxAge));

        s.normalizeCredits();
        return s;
    }

    public static void main(String[] args) {
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "gstudies-servlet.xml", prefix + "database.xml"});
        StatsForArticle sfa = (StatsForArticle) ctx.getBean("statsForArticle");

        System.out.println("*** 0 - 22 ***");
        Shebang sb = sfa.getWholeShebang(-1, 22);
        System.out.println(sb.toString());


        try {
            FileWriter fw = new FileWriter("stats-22-or-younger.csv");
            fw.write(sb.toCSVString());
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(StatsForArticle.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("*** 23 - ***");
        sb = sfa.getWholeShebang(23, -1);
        System.out.println(sb.toString());

        try {
            FileWriter fw = new FileWriter("stats-23-or-older.csv");
            fw.write(sb.toCSVString());
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(StatsForArticle.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("*** ALL ***");
        sb = sfa.getWholeShebang(-1, -1);
        System.out.println(sb);

        try {
            FileWriter fw = new FileWriter("stats-all.csv");
            fw.write(sb.toCSVString());
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(StatsForArticle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
