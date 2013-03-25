package tktl.gstudies.misc;

import com.sun.java.swing.plaf.gtk.GTKConstants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import tktl.gstudies.domain.AcademicYearEnrollment;
import tktl.gstudies.domain.RightToStudy;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.responseobjs.CourseStatsResponseObj;
import tktl.gstudies.services.StatisticService;
import tktl.gstudies.services.StatsUtils;

@Service
public class StatsForArticle {

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
            if (a.getStartDate().before(first.getStartDate())) {
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

    public ProducedCredits getProducedCreditsNMonthsSpan(String dateOfEnrollment, String dateOfRightToStudy, int timeSpan) {
        ProducedCredits pc = new ProducedCredits(dateOfEnrollment, dateOfRightToStudy, timeSpan);
        List<Stud> studs = this.getAllCSStudsEnrollingInSemester(dateOfEnrollment, dateOfRightToStudy);
        pc.amountStuds = studs.size();
        int sum = 0;
        for (Stud s : studs) {
            sum += this.statisticService.getCreditsNMonthsSpan(s.getStudies(), this.statsUtils.makeDate(dateOfEnrollment), timeSpan);
        }
        pc.credits = sum;
        return pc;
    }

    public YearlyStats getReport(String dateOfEnrollment, String dateOfRightToStudy) {
        YearlyStats ys = new YearlyStats();
        ys.pc3 = this.getProducedCreditsNMonthsSpan(dateOfEnrollment, dateOfRightToStudy, 3);
        ys.pc6 = this.getProducedCreditsNMonthsSpan(dateOfEnrollment, dateOfRightToStudy, 6);
        ys.pc9 = this.getProducedCreditsNMonthsSpan(dateOfEnrollment, dateOfRightToStudy, 9);
        ys.pc12 = this.getProducedCreditsNMonthsSpan(dateOfEnrollment, dateOfRightToStudy, 12);
        ys.setCredits();
        return ys;
    }

    public Shebang getWholeShebang() {
        Shebang s = new Shebang();
        s.arses.add(getReport("2006-08-01", "2006-08-01"));
        s.arses.add(getReport("2007-08-01", "2007-08-01"));
        s.arses.add(getReport("2008-08-01", "2008-08-01"));
        s.arses.add(getReport("2009-08-01", "2009-08-01"));
        s.arses.add(getReport("2010-08-01", "2010-08-01"));
        s.arses.add(getReport("2011-08-01", "2011-08-01"));
        s.arses.add(getReport("2012-08-01", "2012-08-01"));
        s.normalizeCredits();
        return s;
    }

    public static void main(String[] args) {
        String prefix = "src/main/webapp/WEB-INF/";
        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{prefix + "gstudies-servlet.xml", prefix + "database.xml"});
        StatsForArticle sfa = (StatsForArticle) ctx.getBean("statsForArticle");
        System.out.println(sfa.getWholeShebang().toString());
    }
}
