package tktl.gstudies.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


//@NamedQueries({
//    @NamedQuery(
//            name = "findCSStudentsFromCourseWhoPassedOnDate",
//    query = "SELECT DISTINCT s FROM Stud s JOIN s.rightsToStudy r, s.studies t JOIN t.courseObjects c, t.statusOfStudy o "
//    + "WHERE c.courseId = :courseId AND t.dateOfwrite = :dateOfwrite AND o.code = 4 AND r.mainSubject ='Tietojenkäsittelytiede'"),
//    @NamedQuery(
//            name = "findOtherStudentsFromCourseWhoPassedOnDate",
//    query = "SELECT DISTINCT s FROM Stud s JOIN s.rightsToStudy r, s.studies t JOIN t.courseObjects c, t.statusOfStudy o "
//    + "WHERE c.courseId = :courseId AND t.dateOfwrite = :dateOfwrite AND o.code = 4 AND r.mainSubject <>'Tietojenkäsittelytiede'"),
//    @NamedQuery(
//            name = "findCSStudentsFromCourseWhoFailedOnDate",
//    query = "SELECT DISTINCT s FROM Stud s JOIN s.rightsToStudy r, s.studies t JOIN t.courseObjects c, t.statusOfStudy o "
//    + "WHERE c.courseId = :courseId AND t.dateOfwrite = :dateOfwrite AND o.code = 10 AND r.mainSubject ='Tietojenkäsittelytiede'"),
//    @NamedQuery(
//            name = "findOtherStudentsFromCourseWhoFailedOnDate",
//    query = "SELECT DISTINCT s FROM Stud s JOIN s.rightsToStudy r, s.studies t JOIN t.courseObjects c, t.statusOfStudy o "
//    + "WHERE c.courseId = :courseId AND t.dateOfwrite = :dateOfwrite AND o.code = 10 AND r.mainSubject <>'Tietojenkäsittelytiede'")
//})
@MappedSuperclass
public class AbstractModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
