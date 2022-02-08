package ir.ac.kntu.universityManagement.models.entities.individuals;

import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity(name="MANAGER")
@Table
@NoArgsConstructor
@Getter
@Setter
public class Manager extends Person {

    public Manager(String nationalId, String firstName, String lastName, String email, String phoneNumber, LocalDate birthdate, LocalDate entranceDate, String address, UserInfo userInfo, Faculty faculty) {
        super(nationalId, firstName, lastName, email, phoneNumber, birthdate, entranceDate, address, userInfo, faculty);
    }
}
