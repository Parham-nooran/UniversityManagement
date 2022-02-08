package ir.ac.kntu.universityManagement.models.repositories.individuals;

import ir.ac.kntu.universityManagement.models.entities.individuals.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    List<Manager> findByUserInfoId(Long id);
    List<Manager> findByEmail(String email);
    List<Manager> findByNationalId(String nationalId);
}
