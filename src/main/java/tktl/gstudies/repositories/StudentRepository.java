
package tktl.gstudies.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.Stud;


public interface StudentRepository extends JpaRepository<Stud, Integer> {
    
}
