package ir.ac.kntu.universityManagement.models.settings;

import java.util.ArrayList;
import java.util.List;

public enum FontSize {
    MEDIUM,LARGE;

    @Override
    public String toString(){
        return name().toLowerCase() + "Font";
    }

    public static List<String> getValues(){
        List<String> temp = new ArrayList<>();
        for (Theme theme:Theme.values()){
            temp.add(theme.toString());
        }
        return temp;
    }
}
