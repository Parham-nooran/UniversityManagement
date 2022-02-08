package ir.ac.kntu.universityManagement.models.scheduling;

import java.util.ArrayList;
import java.util.List;

public enum TimeInterval {

    SEVEN_30("7:30"), NINE("9:00"), TEN_30("10:30"), THIRTEEN_30("13:30"),
    FIFTEEN_30("15:30"), SEVENTEEN_30("17:30"), NINETEEN("19");

    private final String name;
    TimeInterval(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public static List<String> getValues(){
        List<String> temp = new ArrayList<>();
        for (TimeInterval timeInterval:TimeInterval.values()){
            temp.add(timeInterval.toString());
        }
        return temp;
    }

    public static TimeInterval getValueOf(String input){
        switch (input){
            case "7:30":
                return TimeInterval.SEVEN_30;
            case "9:00":
                return TimeInterval.NINE;
            case "10:30":
                return TimeInterval.TEN_30;
            case "13:30":
                return TimeInterval.THIRTEEN_30;
            case "15:30":
                return TimeInterval.FIFTEEN_30;
            case "17:30":
                return TimeInterval.SEVENTEEN_30;
            case "19":
                return TimeInterval.NINETEEN;
        }
        return TimeInterval.SEVEN_30;
    }

}
