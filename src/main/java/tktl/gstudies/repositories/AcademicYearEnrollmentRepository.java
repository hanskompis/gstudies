package tktl.gstudies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.AcademicYearEnrollment;

/**
 * Repository for academic-year-enrollment -objects.
 *
 * @author hkeijone
 */
public interface AcademicYearEnrollmentRepository extends JpaRepository<AcademicYearEnrollment, Integer> {
}
