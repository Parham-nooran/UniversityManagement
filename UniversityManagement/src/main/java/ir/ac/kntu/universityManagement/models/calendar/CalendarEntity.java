//package ir.ac.kntu.universityManagement.models.calendar;
//
//import com.calendarfx.model.Calendar;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity(name="CALENDAR")
//@Table
//@NoArgsConstructor
//@Setter
//@Getter
//public class CalendarEntity extends Calendar {
//    @Id
//    @Column(
//            name="CE_ID"
//    )
//    private Long calenderId;
//
//    public CalendarEntity(Calendar calendar) {
//        super(calendar.getName());
//        super.setLookAheadDuration(calendar.getLookAheadDuration());
//        super.setReadOnly(calendar.isReadOnly());
//        super.setStyle(calendar.getStyle());
//    }
//}
