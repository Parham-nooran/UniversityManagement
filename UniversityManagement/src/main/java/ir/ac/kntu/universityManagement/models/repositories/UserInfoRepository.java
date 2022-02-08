package ir.ac.kntu.universityManagement.models.repositories;

import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    List<UserInfo> findByUsername(String username);
    Boolean existsByUsername(String username);
    /*
    List<UserInfo> findByRole(Role role);

     */

}
