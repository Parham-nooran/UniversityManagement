package ir.ac.kntu.universityManagement.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Generated;
import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BaseEntity {

    @Id
    @SequenceGenerator(
            name = "BaseEntitySequence",
            sequenceName = "BaseEntitySequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "BaseEntitySequence"
    )
    private Long id;


}
