package ir.ac.kntu.universityManagement.controllers.general;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.DetailedWeekView;
import ir.ac.kntu.universityManagement.controllers.editors.EditInstructorController;
import ir.ac.kntu.universityManagement.controllers.managers.InstructorManagementController;
import ir.ac.kntu.universityManagement.models.calendar.CalendarEntry;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.util.Callback;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class FreeTimeDeclarationController extends BaseController implements Initializable {

    @FXML private DetailedWeekView detailedWeekView;
    @FXML private Button save;
    @FXML private Button goBack;

    private static Calendar freeTimes;

    public static Instructor instructor;

    public static Set<CalendarEntry> calendarEntries;

    public static Boolean readOnly;

    public static Boolean calledFromHomePage;

    public FreeTimeDeclarationController(Scene scene) {
        super("FreeTimeDeclaration", scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(instructor != null && calendarEntries == null){
            calendarEntries = instructor.getFreeTimes();
        }
        if(calendarEntries != null && freeTimes == null){
            freeTimes = new Calendar("Free Times");
            for(CalendarEntry calendarEntry:calendarEntries){
                freeTimes.addEntry(calendarEntry.getEntry());
            }
        }


        detailedWeekView.setTrimTimeBounds(true);
        detailedWeekView.setStartTime(LocalTime.of(7, 0));
        detailedWeekView.setEndTime(LocalTime.of(20, 0));

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().add(freeTimes);
        freeTimes.setReadOnly(readOnly);
        freeTimes.setStyle(Calendar.Style.values()[Integer.parseInt(String.valueOf(instructor.getId() > 0 ?
                instructor.getId() % 8 : (instructor.getId() + 1) % 8))]);

        freeTimes.setLookAheadDuration(Duration.of(1, ChronoUnit.DAYS));
        freeTimes.setLookBackDuration(Duration.of(1, ChronoUnit.DAYS));
        detailedWeekView.setDefaultCalendarProvider(new Callback<DateControl, Calendar>() {
            @Override
            public Calendar call(DateControl dateControl) {
                return freeTimes;
            }
        });
        detailedWeekView.getCalendarSources().add(myCalendarSource);

        freeTimes.addEventHandler(calendarEvent -> {
            if(calendarEvent.isEntryAdded()){
                calendarEvent.getEntry().setId(200+calendarEvent.getEntry().getId());
                CalendarEntry calendarEntry = new CalendarEntry(calendarEvent.getEntry());
                calendarEntries.add(calendarEntry);
            } else if(calendarEvent.isEntryRemoved()){
                calendarEntries = calendarEntries.stream().filter(calendarEntry -> calendarEntry.getEntry() != null &&
                                !calendarEntry.getId().equals(Long.parseLong(calendarEvent.getEntry().getId()))).
                        collect(Collectors.toSet());
            } else if(calendarEvent.getEntry() != null) {
                Set<CalendarEntry> temp = calendarEntries.stream().filter(calendarEntry -> calendarEntry.getEntry() != null &&
                                !calendarEntry.getId().equals(Long.parseLong(calendarEvent.getEntry().getId()))).
                        collect(Collectors.toSet());
                temp.addAll(calendarEntries.stream().filter(calendarEntry -> calendarEntry.getEntry() != null &&
                                calendarEntry.getId().equals(Long.parseLong(calendarEvent.getEntry().getId()))).
                        map(calendarEntry -> new CalendarEntry(calendarEvent.getEntry())).
                        collect(Collectors.toSet()));
                calendarEntries = temp;
            }
        });
        if(save != null){
            save.setOnAction((ActionEvent) -> {
                Thread thread = new Thread(() -> {
                    instructor.setFreeTimes(calendarEntries);
                    InstructorManagementController.instructorRepository.save(instructor);
                });
                thread.setDaemon(false);
                thread.setPriority(1);
                thread.start();
            });
        }
        if(goBack != null) {
            goBack.setOnAction((ActionEvent) -> {
                try {
                    if(calledFromHomePage) {
                        goBackToHomePage(detailedWeekView.getScene());
                    } else{
                        new EditInstructorController(detailedWeekView.getScene()).start();
                        abortFreeTimes();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void abortFreeTimes(){
        instructor = null;
        calendarEntries = null;
        freeTimes = null;
    }

    public static void findInstructor(){
        new Thread(() -> {
            List<Instructor> instructors = InstructorManagementController.instructorRepository.
                    findByUserInfoId(HomePageController.user.getId());
            if (!instructors.isEmpty()) {
                instructor = instructors.get(0);
                if(calendarEntries == null){
                    calendarEntries = instructor.getFreeTimes();
                }
            }
        }).start();
    }

}
