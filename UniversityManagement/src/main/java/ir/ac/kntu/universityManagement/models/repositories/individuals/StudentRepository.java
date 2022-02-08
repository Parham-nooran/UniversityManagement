package ir.ac.kntu.universityManagement.models.repositories.individuals;

import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    /*
    List<Student> findByStudentNumberContaining(String studentNumber);
    List<Student> findByNationalIdContaining(String nationalId);
    List<Student> findByFirstNameContaining(String firstName);
    List<Student> findByLastNameContaining(String lastNme);
    List<Student> findByPhoneNumberContaining(String phoneNumber);
    List<Student> findByAddressContaining(String Address);
    List<Student> findByEmailContaining(String email);
    List<Student> findByBirthdate(LocalDate birthDate);
    List<Student> findByUserInfoUsername(String username);
     */
    List<Student> findByUserInfoId(Long userId);
    Boolean existsByUserInfoUsername(String username);
    Boolean existsByEmail(String email);
}
