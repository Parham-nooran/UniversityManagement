package ir.ac.kntu.universityManagement.models.scheduling;

import ir.ac.kntu.universityManagement.models.auth.Role;

import java.util.ArrayList;
import java.util.List;

public enum WeekDay {
    SATURDAY, SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;

    @Override
    public String toString(){
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    public static List<String> getValues(){
        List<String> temp = new ArrayList<>();
        for (WeekDay weekDay:WeekDay.values()){
            temp.add(weekDay.toString());
        }
        return temp;
    }
}
