package ir.ac.kntu.universityManagement.models.settings;

import ir.ac.kntu.universityManagement.models.auth.Role;

import java.util.ArrayList;
import java.util.List;

public enum Language {
    PERSIAN, ENGLISH;

    @Override
    public String toString(){
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    public static List<String> getValues(){
        List<String> temp = new ArrayList<>();
        for (Language language:Language.values()){
            temp.add(language.toString());
        }
        return temp;
    }

}
