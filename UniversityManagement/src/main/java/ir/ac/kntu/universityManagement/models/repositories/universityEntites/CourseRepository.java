package ir.ac.kntu.universityManagement.models.repositories.universityEntites;

import ir.ac.kntu.universityManagement.models.entities.universityEntities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    /*
    List<Course> findByCourseIdContaining(String courseId);
    List<Course> findByNameContaining(String name);
    List<Course> findByCapacity(Integer capacity);
    List<Course> findByScheduleContaining(String schedule);
    List<Course> findByFinalExam(LocalDate localDate);
    */
}
