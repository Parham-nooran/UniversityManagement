package ir.ac.kntu.universityManagement.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public abstract class BaseEntity {

    @Id
    @Column(
            name="BE_ID"
    )
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
