package ir.ac.kntu.libraryManagement.models.individuals;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity(name = "INSTRUCTOR")
@NoArgsConstructor
@Getter
@Setter
public class Instructor extends Person{
    private String instructorId;
}
