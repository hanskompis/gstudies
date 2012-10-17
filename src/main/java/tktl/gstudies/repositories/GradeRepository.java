
package tktl.gstudies.repositories;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.Grade;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    
}
