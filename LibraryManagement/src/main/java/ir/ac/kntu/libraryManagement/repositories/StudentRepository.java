package ir.ac.kntu.libraryManagement.repositories;

import ir.ac.kntu.libraryManagement.models.individuals.Student;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.lang.annotation.Annotation;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
