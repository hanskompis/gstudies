
package tktl.gstudies.services;

import java.util.Date;
import java.util.List;
import tktl.gstudies.domain.Stud;
import tktl.gstudies.domain.TypeOfStudy;

public interface StatisticService {
    public List<Stud> getCSStudentsFromCourseWhoPassedOnDate(String courseId, String dateOfWrite);
    public List<TypeOfStudy> getTypesOfStudy();
}
