package ir.ac.kntu.universityManagement.models.entities.individuals;


import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.BaseEntity;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;


@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public abstract class Person extends BaseEntity {

    @Column(
            name = "NATIONAL_ID",
            nullable = false,
            unique = true
    )
    private String nationalId;


    @Column(
            name = "FIRST_NAME",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "LAST_NAME",
            nullable = false
    )
    private String lastName;

    @Column(
            name = "EMAIL"
    )
    private String email;

    @Column(
            name = "PHONE_NUMBER"
    )
    private String phoneNumber;



    @Column(
            name = "BIRTHDATE"
    )
    private LocalDate birthdate;

    @Column(
            name = "ENTRANCE_DATE"
    )
    private LocalDate entranceDate;

    @Column(
            name = "ADDRESS"
    )
    private String address;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(unique = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private UserInfo userInfo;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @NotFound(action = NotFoundAction.IGNORE)
    private Faculty faculty;

}
