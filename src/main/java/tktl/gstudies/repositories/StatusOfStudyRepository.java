
package tktl.gstudies.repositories;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.StatusOfStudy;

public interface StatusOfStudyRepository extends JpaRepository<StatusOfStudy, Integer> {
    
}
