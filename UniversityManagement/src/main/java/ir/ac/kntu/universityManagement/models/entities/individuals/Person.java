package ir.ac.kntu.universityManagement.models.entities.individuals;


import ir.ac.kntu.universityManagement.models.entities.BaseEntity;
import lombok.*;

import javax.persistence.*;


@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public abstract class Person extends BaseEntity {

    @Column(
            name = "NATIONAL_ID",
            nullable = false
    )
    private String nationalId = this.firstName+super.getId();
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
            name = "EMAIL",
            unique = true
    )
    private String email;

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}
