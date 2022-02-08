package ir.ac.kntu.universityManagement.models.entities.individuals;

import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Grade;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name="STUDENT")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Student extends Person{
    @Column(
            name = "STUDENT_NUMBER",
            unique = true
    )
    private String studentNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Grade> gradeSet = new HashSet<>();


    public String toString(){
        return super.getFirstName() + " " + super.getLastName();
    }

    public Student(String nationalId, String firstName, String lastName, String email, String phoneNumber,
                   LocalDate birthdate, LocalDate entranceDate, String address, UserInfo userInfo, Faculty faculty) {
        super(nationalId, firstName, lastName, email, phoneNumber, birthdate, entranceDate, address, userInfo, faculty);
        this.studentNumber = nationalId + "_" + firstName;
    }
}
