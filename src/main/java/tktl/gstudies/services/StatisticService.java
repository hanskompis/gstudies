package tktl.gstudies.services;

import java.util.Date;
import java.util.List;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.domain.Study;
import tktl.gstudies.domain.TypeOfStudy;
import tktl.gstudies.responseobjs.CourseStats;
import tktl.gstudies.responseobjs.CourseStatsResponseObj;

public interface StatisticService {

    public List<Stud> getCSStudentsFromCourseWhoPassedOnDate(String courseId, String dateOfWrite);

 //   public List<Stud> getOtherStudentsFromCourseWhoPassedOnDate(String courseId, String dateOfWrite);

    public List<Stud> getCSStudentsFromCourseWhoFailedOnDate(String courseId, String dateOfWrite);

  //  public List<Stud> getOtherStudentsFromCourseWhoFailedOnDate(String courseId, String dateOfWrite);
    
    public void sortCourses(Stud s);
    
    public CourseStats doTheMagic(String groupIdentifier, String dateString, String courseId);
    
    public double getCreditsNMonthsSpan(List<Study> studies, Date startDate, int timeSpan);
    
    public Date makeDate(String dateString);
    
    public Date AddMonthsToDate(Date date, int incrementInMonths);
    
    public double getAverageGradeNMonthsSpan(List<Study> studies, Date startDate, int timeSpan);
    
    public double getGroupAverageGradeNMonthsSpan(List<Stud> studs, Date startDate, int timeSpan);
    
    public double getStandardDeviationOfgrades(List<Stud> studs, Date startDate, int timeSpan);
    
    public int[] getGradeDistribution(List<Stud> studs, String courseId, String dateSrtring);
    
    public CourseStatsResponseObj getData(String dateString, String courseId);
    
    public List<TypeOfStudy> getTypesOfStudy();
    
}
