package ir.ac.kntu.universityManagement.models.scheduling;

import ir.ac.kntu.universityManagement.models.entities.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity(name="SCHEDULE")
@Table
@Getter
@Setter
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private TimeInterval timeInterval;
    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;

    public String toString(){
        return weekDay.toString() + " " + timeInterval.toString();
    }

    public Schedule(TimeInterval timeInterval, WeekDay weekDay) {
        this.timeInterval = timeInterval;
        this.weekDay = weekDay;
    }
}
