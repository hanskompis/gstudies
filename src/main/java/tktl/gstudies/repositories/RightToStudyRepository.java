package tktl.gstudies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.RightToStudy;

/**
 * Repository for RightToStudy-objects.
 *
 * @author hkeijone
 */
public interface RightToStudyRepository extends JpaRepository<RightToStudy, Integer> {
}
