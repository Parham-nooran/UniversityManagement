package ir.ac.kntu.universityManagement.models.entities.individuals;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="INSTRUCTOR")
@Table
@NoArgsConstructor
@Getter
@Setter
public class Instructor extends Person{
    @Column(
            name = "INSTRUCTOR_ID",
            nullable = false
    )
    private String instructorId;
}
