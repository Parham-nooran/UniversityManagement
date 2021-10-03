package ir.ac.kntu.universityManagement.repositories;

import ir.ac.kntu.universityManagement.models.individuals.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
