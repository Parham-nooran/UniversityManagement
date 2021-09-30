package ir.ac.kntu.libraryManagement.models.departments;

import ir.ac.kntu.libraryManagement.models.individuals.Instructor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.util.List;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentBase {
    private String departmentId;
    private String name;
    private List<Instructor> instructors;
}
