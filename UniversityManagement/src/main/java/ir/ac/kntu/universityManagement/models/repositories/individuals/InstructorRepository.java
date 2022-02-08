package ir.ac.kntu.universityManagement.models.repositories.individuals;

import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    /*
    List<Instructor> findByInstructorIdContaining(String instructorId);
    List<Instructor> findByNationalIdContaining(String nationalId);
    List<Instructor> findByFirstNameContaining(String firstName);
    List<Instructor> findByLastNameContaining(String lastNme);
    List<Instructor> findByPhoneNumberContaining(String phoneNumber);
    List<Instructor> findByAddressContaining(String Address);
    List<Instructor> findByEmailContaining(String email);
    List<Instructor> findByBirthdate(LocalDate birthdate);
    List<Instructor> findByInstructorId(String text);
    List<Instructor> findByUserInfoUsernameContaining(String username);
    List<Instructor> findByFacultyNameContaining(String facultyName);
    List<Instructor> findByUserInfoUsername(String username);

     */
    List<Instructor> findByUserInfoId(Long userId);
    Boolean existsByUserInfoUsername(String username);
    Boolean existsByEmail(String email);
}
