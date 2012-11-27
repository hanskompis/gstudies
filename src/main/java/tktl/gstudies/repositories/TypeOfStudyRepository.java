package tktl.gstudies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tktl.gstudies.domain.TypeOfStudy;

/**
 * Repository for TypeOfStudy-objects
 *
 * @author hkeijone
 */
public interface TypeOfStudyRepository extends JpaRepository<TypeOfStudy, Integer> {

    public TypeOfStudy findByCode(Integer code);
}
