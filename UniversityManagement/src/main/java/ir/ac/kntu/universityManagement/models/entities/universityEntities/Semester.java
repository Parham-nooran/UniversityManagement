package ir.ac.kntu.universityManagement.models.entities.universityEntities;


import java.util.ArrayList;
import java.util.List;

public enum Semester {
    _FIRST, _SECOND, _THIRD;

    @Override
    public String toString(){
        return name().charAt(1) + name().substring(2).toLowerCase();
    }

    public static List<String> getValues(){
        List<String> temp = new ArrayList<>();
        for (Semester semester:Semester.values()){
            temp.add(semester.toString());
        }
        return temp;
    }
}
