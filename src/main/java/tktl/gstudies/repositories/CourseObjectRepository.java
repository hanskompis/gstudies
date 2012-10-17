package tktl.gstudies.repositories;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.CourseObject;

public interface CourseObjectRepository extends JpaRepository<CourseObject, Integer> {

    public List<CourseObject> findByObjectId(Integer objectId);
    //public CourseObject findByObjectId(Integer objectId);
}
