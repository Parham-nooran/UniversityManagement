package ir.ac.kntu.universityManagement.models.repositories.universityEntites;

import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    /*
    List<Faculty> findByFacultyIdContaining(String facultyId);
    List<Faculty> findByNameContaining(String name);
    List<Faculty> findByNumberOfClasses(Integer numOfClasses);
    List<Faculty> findByNumberOfStudents(Integer numOfStudents);
    List<Faculty> findByNumberOfInstructors(Integer numberOfInstructors);
    List<Faculty> findByPhoneNumberContaining(String phoneNumber);
    List<Faculty> findByFacultyId(String phoneNumber);
    List<Faculty> findByName(String name);*/
    Boolean existsByName(String name);
}