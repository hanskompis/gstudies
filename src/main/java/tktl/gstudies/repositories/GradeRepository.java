package tktl.gstudies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.Grade;

/**
 * Repository for Grade-objects.
 *
 * @author hkeijone
 */
public interface GradeRepository extends JpaRepository<Grade, Integer> {
}
