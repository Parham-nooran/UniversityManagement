package ir.ac.kntu.universityManagement.models.repositories.universityEntites;

import ir.ac.kntu.universityManagement.models.entities.universityEntities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    /*
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findByCourseId(Long courseId);
    List<Grade> findByValue(Double value);
     */
}
