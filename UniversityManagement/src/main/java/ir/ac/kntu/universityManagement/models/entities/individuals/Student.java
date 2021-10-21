package ir.ac.kntu.universityManagement.models.entities.individuals;

import lombok.*;

import javax.persistence.*;

@Entity(name="STUDENT")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Student extends Person{
    @Column(
            name = "STUDENT_NUMBER"
    )
    private String studentNumber;

    public Student(String firstName, String lastName, String email, String studentNumber) {
        super(firstName, lastName, email);
        this.studentNumber = studentNumber;
    }
    public Student(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }
}
