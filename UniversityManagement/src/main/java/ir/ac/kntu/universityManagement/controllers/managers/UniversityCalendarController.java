package ir.ac.kntu.universityManagement.controllers.managers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.models.calendar.CalendarEntry;
import ir.ac.kntu.universityManagement.models.calendar.EntryType;
import ir.ac.kntu.universityManagement.models.repositories.calendar.CalendarEntryRepository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.util.Callback;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


@NoArgsConstructor
public class UniversityCalendarController extends BaseController implements Initializable {


    @FXML
    private CalendarView calendarView;

    public static List<CalendarEntry> calendarEntries;
    public static CalendarEntryRepository calendarEntryRepository;

    public static Boolean isUpToDate = false;

    private static Calendar courseRegistration;
    private static Calendar instructorsEvaluation;

    @FXML
    private Button goBackToHomePage;

    @FXML
    private Button save;

    public UniversityCalendarController(Scene scene) {
        super("UniversityCalendar", scene);
    }

    public static boolean isRegistrationTime() {
        List<CalendarEntry> calendarEntries = calendarEntryRepository.findAll();
        System.out.println(calendarEntries.stream().filter(calendarEntry ->
                calendarEntry.getEntryType().equals(EntryType.COURSE_REGISTRATION)).
                map(calendarEntry -> calendarEntry.getEntry().getInterval()).
                collect(Collectors.toList()));
        Boolean result = calendarEntries.stream().anyMatch(calendarEntry ->
                calendarEntry.getEntryType().equals(EntryType.COURSE_REGISTRATION) &&
                intervalContainsCurrentDateAndTime(calendarEntry.getEntry().getInterval()));
        System.out.println(result);
        return result;
    }

    public static Boolean intervalContainsCurrentDateAndTime(Interval interval){
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        interval = interval.withZoneId(ZoneId.systemDefault());
        return interval.getStartDate().isBefore(localDate) && interval.getEndDate().isAfter(localDate) ||
                ( (interval.getStartDate().isEqual(localDate) || interval.getEndDate().isEqual(localDate)) &&
                interval.getStartTime().isBefore(localTime) && interval.getEndTime().isAfter(localTime));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(instructorsEvaluation == null || courseRegistration == null){
            if(courseRegistration == null){
                courseRegistration = new Calendar("Course Registration");
            }
            if(instructorsEvaluation == null){
                instructorsEvaluation = new Calendar("Instructors Evaluation");
            }
            fillCalendars();
        }

        courseRegistration.setStyle(Calendar.Style.STYLE2);
        instructorsEvaluation.setStyle(Calendar.Style.STYLE3);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");

        myCalendarSource.getCalendars().addAll(courseRegistration, instructorsEvaluation);



        calendarView.setShowAddCalendarButton(false);

        calendarView.getCalendarSources().addAll(myCalendarSource);


        courseRegistration.addEventHandler(calendarEvent -> {
            if(calendarEvent.isEntryAdded()){
                CalendarEntry calendarEntry = new CalendarEntry(calendarEvent.getEntry());
                new Thread(() -> calendarEntryRepository.save(calendarEntry)).start();
                calendarEntries.add(calendarEntry);
            } else if(calendarEvent.isEntryRemoved()){
                new Thread(() -> calendarEntryRepository.deleteById(Long.parseLong(calendarEvent.getEntry().getId()))).
                        start();
                calendarEntries = calendarEntries.stream().filter(calendarEntry -> !calendarEntry.getId().
                        equals(Long.parseLong(calendarEvent.getEntry().getId()))).collect(Collectors.toList());
            } else if(calendarEvent.getEntry() != null){
                List<CalendarEntry> temp = calendarEntries.stream().filter(calendarEntry -> !calendarEntry.getId().
                        equals(Long.parseLong(calendarEvent.getEntry().getId()))).collect(Collectors.toList());
                temp.addAll(calendarEntries.stream().filter(calendarEntry ->
                                calendarEntry.getId().equals(Long.parseLong(calendarEvent.getEntry().getId()))).
                        map(calendarEntry -> new CalendarEntry(calendarEvent.getEntry())).
                        collect(Collectors.toList()));
                calendarEntries = temp;
            }
        });
        instructorsEvaluation.addEventHandler(calendarEvent -> {
            if(calendarEvent.isEntryAdded()){
                CalendarEntry calendarEntry = new CalendarEntry(calendarEvent.getEntry());
                new Thread(() -> calendarEntryRepository.save(calendarEntry)).start();
                calendarEntries.add(calendarEntry);
            } else if(calendarEvent.isEntryRemoved()){
                new Thread(() -> calendarEntryRepository.deleteById(Long.parseLong(calendarEvent.getEntry().getId()))).
                        start();
                calendarEntries = calendarEntries.stream().filter(calendarEntry -> !calendarEntry.getId().
                        equals(Long.parseLong(calendarEvent.getEntry().getId()))).collect(Collectors.toList());
            } else if(calendarEvent.getEntry() != null){
                List<CalendarEntry> temp = calendarEntries.stream().filter(calendarEntry -> !calendarEntry.getId().
                        equals(Long.parseLong(calendarEvent.getEntry().getId()))).collect(Collectors.toList());
                temp.addAll(calendarEntries.stream().filter(calendarEntry ->
                                calendarEntry.getId().equals(Long.parseLong(calendarEvent.getEntry().getId()))).
                        map(calendarEntry -> new CalendarEntry(calendarEvent.getEntry())).
                        collect(Collectors.toList()));
                calendarEntries = temp;
            }
        });


        calendarView.setRequestedTime(LocalTime.now());
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        if(goBackToHomePage != null) {
            goBackToHomePage.setOnAction((ActionEvent) -> {
                try {
                    goBackToHomePage(calendarView.getScene());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if(save != null){
            save.setOnAction((ActionEvent) -> {
                Thread thread = new Thread(() -> {
                    for (CalendarEntry calendarEntry : calendarEntries) {
                        calendarEntryRepository.save(calendarEntry);
                    }
                });
                thread.setDaemon(false);
                thread.setPriority(1);
                thread.start();
            });
        }
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }



    public void fillCalendars(){
        for(CalendarEntry calendarEntry:calendarEntries){
            Entry newEntry = calendarEntry.getEntry();
            if(calendarEntry.getEntryType().equals(EntryType.COURSE_REGISTRATION)){
                newEntry.setCalendar(courseRegistration);
                courseRegistration.addEntry(newEntry);
            } else if(calendarEntry.getEntryType().equals(EntryType.INSTRUCTORS_EVALUATION)) {
                newEntry.setCalendar(instructorsEvaluation);
                instructorsEvaluation.addEntry(newEntry);
            }
        }
    }

    public static void refillEntries(){
        new Thread(() -> {
            calendarEntries = calendarEntryRepository.findAll();
            isUpToDate = true;
        }).start();
    }


}
