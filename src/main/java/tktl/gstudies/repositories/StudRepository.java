package tktl.gstudies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.Stud;

/**
 * Repository for Stud-objects.
 *
 * @author hkeijone
 */
public interface StudRepository extends JpaRepository<Stud, Integer> {

    public Stud findByStudentId(Integer studentId);
}
