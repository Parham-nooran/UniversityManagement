package ir.ac.kntu.universityManagement.models.repositories;

import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
