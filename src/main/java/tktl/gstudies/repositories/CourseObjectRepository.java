package tktl.gstudies.repositories;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.CourseObject;

/**
 * Repository for course object -objects
 *
 * @author hkeijone
 */
public interface CourseObjectRepository extends JpaRepository<CourseObject, Integer> {

    public List<CourseObject> findByObjectId(Integer objectId);
}
