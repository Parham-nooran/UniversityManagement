package ir.ac.kntu.universityManagement.models.entities.universityEntities;

import ir.ac.kntu.universityManagement.models.entities.BaseEntity;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity(name="Grade")
@Table
@NoArgsConstructor
@Setter
@Getter
public class Grade extends BaseEntity {


    @ManyToOne(cascade = CascadeType.REFRESH)
    @NotFound(action = NotFoundAction.IGNORE)
    private Course course;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @NotFound(action = NotFoundAction.IGNORE)
    private Student student;


    @Column(
            name = "VALUE"
    )
    private Double value;

    public Grade(Course course, Student student, Double value) {
        this.course = course;
        this.student = student;
        this.value = value;
    }
}
