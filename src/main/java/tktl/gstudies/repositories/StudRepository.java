
package tktl.gstudies.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.Stud;


public interface StudRepository extends JpaRepository<Stud, Integer> {
    public Stud findByStudentId(Integer studentId);
}
