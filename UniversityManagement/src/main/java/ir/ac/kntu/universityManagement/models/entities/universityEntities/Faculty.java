package ir.ac.kntu.universityManagement.models.entities.universityEntities;

import ir.ac.kntu.universityManagement.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Faculty")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Faculty extends BaseEntity {
    @Column(
            name = "FACULTY_ID",
            nullable = false
    )
    private String facultyId;

    @Column(
            name = "NAME",
            nullable = false,
            unique = true
    )
    private String name;

    @Column(
            name = "NUMBER_OF_STUDENTS"

    )
    private Integer numberOfStudents;

    @Column(
            name = "NUMBER_OF_CLASSES"

    )
    private Integer numberOfClasses;

    @Column(
            name = "NUMBER_OF_INSTRUCTORS"

    )
    private Integer numberOfInstructors;

    @Column(
            name = "PHONE_NUMBER"
    )
    private String phoneNumber;



    public Faculty(String name, Integer numberOfStudents, Integer numberOfClasses, Integer numberOfInstructors, String phoneNumber) {
        this.name = name;
        this.numberOfStudents = numberOfStudents;
        this.numberOfClasses = numberOfClasses;
        this.numberOfInstructors = numberOfInstructors;
        this.phoneNumber = phoneNumber;
        refreshFacultyId();
    }

    public String toString(){
        return name;
    }

    public void refreshFacultyId(){
        this.facultyId = name + "_" + numberOfClasses;
    }

    public void setName(String name){
        this.name = name;
        refreshFacultyId();
    }

    public void setNumberOfClasses(Integer numberOfClasses){
        this.numberOfClasses = numberOfClasses;
        refreshFacultyId();
    }
}
