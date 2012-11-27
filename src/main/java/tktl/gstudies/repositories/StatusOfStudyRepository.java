package tktl.gstudies.repositories;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.StatusOfStudy;

/**
 * Repository for StatusOfStudy-objects.
 *
 * @author hkeijone
 */
public interface StatusOfStudyRepository extends JpaRepository<StatusOfStudy, Integer> {

    public StatusOfStudy findByCode(Integer code);
}
