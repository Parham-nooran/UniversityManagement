package ir.ac.kntu.universityManagement.models.entities.individuals;

import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.calendar.CalendarEntry;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name="INSTRUCTOR")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Instructor extends Person{
    @Column(
            name = "INSTRUCTOR_ID",
            nullable = false,
            unique = true
    )
    private String instructorId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<CalendarEntry> freeTimes = new HashSet<>();

    @Override
    public String toString() {
        return getInstructorId();
    }

    public Instructor(String nationalId, String firstName, String lastName, String email, String phoneNumber,
                      LocalDate birthdate, LocalDate entranceDate, String address, UserInfo userInfo, Faculty faculty) {
        super(nationalId, firstName, lastName, email, phoneNumber, birthdate, entranceDate, address, userInfo, faculty);
        this.instructorId = nationalId + "_" + firstName;
    }
}
