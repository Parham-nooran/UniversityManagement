package ir.ac.kntu.universityManagement.models.auth;


import ir.ac.kntu.universityManagement.models.entities.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="USER")
@Table
@Getter
@Setter
@NoArgsConstructor
public class UserInfo extends BaseEntity {


    @Column (
            unique = true,
            nullable = false,
            name = "USERNAME"
    )
    private String username;
    @Column (
            name = "PASSWORD"
    )
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    public UserInfo(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString(){
        return username;
    }

}
