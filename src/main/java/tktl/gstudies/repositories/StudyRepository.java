package tktl.gstudies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.Study;

/**
 * Repository for Study-objects.
 *
 * @author hkeijone
 */
public interface StudyRepository extends JpaRepository<Study, Integer> {

    public Study findByStudyNumber(Integer studyNumber);
}
