
package tktl.gstudies.repositories;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.RightToStudy;

public interface RightToStudyRepository extends JpaRepository<RightToStudy,Integer> {
    
}
