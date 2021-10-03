package ir.ac.kntu.universityManagement.models.individuals;

import lombok.*;

import javax.persistence.*;

@Entity(name="STUDENT")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Student extends Person{
    private String studentNumber;

    public Student(String firstName, String lastName, String email, String studentNumber) {
        super(firstName, lastName, email);
        this.studentNumber = studentNumber;
    }
    public Student(String firstName, String lastName, String studentNumber) {
        super(firstName, lastName);
        this.studentNumber = studentNumber;
    }
}
