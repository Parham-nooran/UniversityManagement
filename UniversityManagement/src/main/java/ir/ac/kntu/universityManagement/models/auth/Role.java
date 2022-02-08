package ir.ac.kntu.universityManagement.models.auth;


import java.util.ArrayList;
import java.util.List;

public enum Role {
    STUDENT, INSTRUCTOR, ADMIN;


    @Override
    public String toString(){
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    public static List<String> getValues(){
        List<String> temp = new ArrayList<>();
        for (Role role:Role.values()){
            temp.add(role.toString());
        }
        return temp;
    }
}
