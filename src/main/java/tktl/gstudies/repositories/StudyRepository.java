
package tktl.gstudies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Integer> {
    
}
