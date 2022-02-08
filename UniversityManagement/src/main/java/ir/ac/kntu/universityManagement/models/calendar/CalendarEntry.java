
package ir.ac.kntu.universityManagement.models.calendar;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity(name="CALENDAR_ENTRY")
@Table
@NoArgsConstructor
@Setter
@Getter
public class CalendarEntry<T> {
    @Id
    @Column(
            name="CE_ID"
    )
    private Long id;

    @Column(
            name = "TITLE"
    )
    private String title;

    @Column(
            name = "START_DATE"
    )
    private LocalDate startDate;

    @Column(
            name = "START_TIME"
    )
    private LocalTime startTime;

    @Column(
            name = "END_DATE"
    )
    private LocalDate endDate;
    @Column(
            name = "END_TIME"
    )
    private LocalTime endTime;

    @Column(
            name = "FULL_DAY"
    )
    private Boolean fullDay;

    @Column(
            name = "MINIMUM_DURATION"
    )
    private Duration minimumDuration;

    @Column(
            name = "RECURRENCE_RULE"
    )
    private String recurrenceRule;



    @Enumerated(EnumType.STRING)
    private EntryType entryType;
    @Column(
            name = "LOCATION"
    )
    private String location;


    public CalendarEntry(Entry<T> entry) {
        update(entry);
    }

    public void update(Entry<T> entry){
        id = Long.parseLong(entry.getId());
        title = entry.getTitle();
        startDate = entry.getStartDate();
        startTime = entry.getStartTime();
        endDate = entry.getEndDate();
        endTime = entry.getEndTime();
        fullDay = entry.isFullDay();
        minimumDuration = entry.getMinimumDuration();
        recurrenceRule = entry.getRecurrenceRule();
        findEntryType(entry);
        location = entry.getLocation();
    }


    public Entry<T> getEntry(){
        Entry<T> entry = new Entry<>(title, new Interval(startDate, startTime, endDate, endTime));
        entry.setFullDay(fullDay);
        entry.setId(String.valueOf(id));
        entry.setMinimumDuration(minimumDuration);
        entry.setRecurrenceRule(recurrenceRule);
        entry.setLocation(location);
        return entry;
    }

    public void findEntryType(Entry<T> entry){
        if(entry.getCalendar() != null) {
            switch (entry.getCalendar().getName()) {
                case "Course Registration":
                    this.entryType = EntryType.COURSE_REGISTRATION;
                    break;
                case "Instructors Evaluation":
                    this.entryType = EntryType.INSTRUCTORS_EVALUATION;
                    break;
                case "Free Times":
                    this.entryType = EntryType.FREE_TIMES;
                    break;
                case "Default Calendar":
                    this.entryType = EntryType.DEFAULT;
                    break;
            }
        }
    }

}