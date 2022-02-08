package ir.ac.kntu.universityManagement.models.settings;

import ir.ac.kntu.universityManagement.models.auth.Role;

import java.util.ArrayList;
import java.util.List;

public enum Theme {
    DARK, LIGHT;

    @Override
    public String toString(){
        return name().toLowerCase() + "Theme";
    }

    public static List<String> getValues(){
        List<String> temp = new ArrayList<>();
        for (Theme theme:Theme.values()){
            temp.add(theme.toString());
        }
        return temp;
    }

}
