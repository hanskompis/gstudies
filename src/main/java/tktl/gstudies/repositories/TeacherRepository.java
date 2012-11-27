package tktl.gstudies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.Teacher;

/**
 * Repository for Teacher-objects.
 *
 * @author hkeijone
 */
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
