package ir.ac.kntu.universityManagement.models.individuals;


import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public abstract class Person {
    @Id
    @SequenceGenerator(
            name = "person_sequence",
            sequenceName = "person_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "person_sequence"
    )
    private Long nationalId;
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
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
