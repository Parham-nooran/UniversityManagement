package ir.ac.kntu.universityManagement.models.entities.universityEntities;

import ir.ac.kntu.universityManagement.models.entities.BaseEntity;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.scheduling.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name="COURSE")
@Table
@NoArgsConstructor
@Setter
@Getter
public class Course extends BaseEntity {


    @Column(
            name = "COURSE_ID",
            unique = true
    )
    private String courseId;
    @Column(
            name = "NAME"
    )
    private String name;
    @Column(
            name = "CAPACITY"
    )
    private Integer capacity;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @NotFound(action = NotFoundAction.IGNORE)
    private Instructor instructor;

    @Column(
            name = "FINAL_EXAM"
    )
    private LocalDate finalExam;

    @Enumerated(EnumType.STRING)
    private Semester semester;

    @Column(
            name = "START_DATE"
    )
    private LocalDate startDate;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Schedule> schedules = new HashSet<>();

    @Column(
            name = "UNITS"
    )
    private Integer units;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Course> prerequisites = new HashSet<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Course> corequisites = new HashSet<>();

    @ManyToOne(cascade = CascadeType.REFRESH)
    @NotFound(action = NotFoundAction.IGNORE)
    private Faculty faculty;

    public Course(String name, Integer capacity, Set<Schedule> schedules, Instructor instructor, Integer units,
                  Semester semester, LocalDate startDate, HashSet<Course> prerequisites, HashSet<Course> corequisites,
                  Faculty faculty, LocalDate finalExam) {
        this.name = name;
        this.capacity = capacity;
        this.schedules = schedules;
        this.instructor = instructor;
        this.units = units;
        this.finalExam = finalExam;
        this.semester = semester;
        this.startDate = startDate;
        this.prerequisites = prerequisites;
        this.corequisites = corequisites;
        this.faculty = faculty;
        refreshCourseId();
    }

    @Override
    public String toString(){
        return courseId;
    }

    public void refreshCourseId(){
        courseId = name + "_" + instructor.getInstructorId();
    }

    public void setName(String name){
        this.name = name;
        refreshCourseId();
    }

    public void setInstructor(Instructor instructor){
        this.instructor = instructor;
        refreshCourseId();
    }



}
