
package tktl.gstudies.repositories;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.AcademicYearEnrollment;

public interface AcademicYearEnrollmentRepository extends JpaRepository<AcademicYearEnrollment, Integer> {
    
}
