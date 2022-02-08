package ir.ac.kntu.universityManagement.controllers.partSpecfic;

import ir.ac.kntu.universityManagement.controllers.deleters.DeleteCourseController;
import ir.ac.kntu.universityManagement.controllers.editors.EditCourseController;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.managers.CourseManagementController;
import ir.ac.kntu.universityManagement.controllers.viewLists.ViewAllCoursesController;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Course;
import ir.ac.kntu.universityManagement.models.scheduling.Schedule;
import ir.ac.kntu.universityManagement.models.scheduling.TimeInterval;
import ir.ac.kntu.universityManagement.models.scheduling.WeekDay;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
public class CourseController extends BaseController {


    public CourseController(String viewName, Scene scene) {super(viewName,scene);}

    public static void fillCourseTableView(TableColumn<Course, String> courseId, TableColumn<Course, String> name,
                                           TableColumn<Course, Integer> capacity, TableColumn<Course,
            Set<Schedule>> schedules, TableColumn<Course, Instructor> instructor, TableColumn<Course, Integer> units,                                           TableColumn<Course, String> semester,
                                           TableColumn<Course, LocalDate> startDate,
                                           TableColumn<Course, LocalDate> finalExam) {
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        schedules.setCellValueFactory(new PropertyValueFactory<>("schedules"));
        instructor.setCellValueFactory(new PropertyValueFactory<>("instructor"));
        units.setCellValueFactory(new PropertyValueFactory<>("units"));
        semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        finalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
    }

    public void fillRequisitesTableView(TableColumn<Course, String> courseId,
                                        TableColumn<Course, String> name){
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
    }


    public void fillScheduling(ComboBox<String> dayOne, ComboBox<String> dayTwo, ComboBox<String> timeInterval,
                               ComboBox<String> units){
        dayOne.getItems().addAll(FXCollections.observableArrayList(WeekDay.getValues()));
        dayTwo.getItems().addAll(FXCollections.observableArrayList((WeekDay.getValues())));
        timeInterval.getItems().addAll(FXCollections.observableArrayList(TimeInterval.getValues()));
        units.getItems().addAll(FXCollections.observableArrayList("1", "2", "3", "4"));
    }

    public static void fillInstructorTableView(TableColumn<Instructor, String> instructorId,TableColumn<Instructor,
            String> firstName,TableColumn<Instructor, String> lastName){
        instructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    }

    public void fillCourseSearch(TextField courseSearchBox, ComboBox<String> courseSearchBy,TableView<Course> courseTableView){
        if(BaseController.getLanguage().equals("Persian")){
            courseSearchBy.getItems().addAll(FXCollections.observableArrayList("آی دی درس", "نام درس", "ظرفیت درس",
                    "زمانبندی درس", "استاد درس", "پایانترم درس"));
        } else if(BaseController.getLanguage().equals("English")) {
            courseSearchBy.getItems().addAll(FXCollections.observableArrayList("Course ID", "Name", "Capacity",
                    "Schedule", "Instructor", "Final Exam"));
        }
        courseSearchBox.textProperty().addListener((observable, oldValue, newValue) ->
                courseSearch(courseSearchBox, courseSearchBy, courseTableView));
    }

    public void fillRequisitesSearch(TextField courseSearchBox, ComboBox<String> courseSearchBy,
                                     TableView<Course> courseTableView, ArrayList<Course> initialCourses){
        if(BaseController.getLanguage().equals("Persian")){
            courseSearchBy.getItems().addAll(FXCollections.observableArrayList("آی دی درس", "نام درس"));
        } else if(BaseController.getLanguage().equals("English")) {
            courseSearchBy.getItems().addAll(FXCollections.observableArrayList("Course ID", "Name"));
        }
        courseSearchBox.textProperty().addListener((observable, oldValue, newValue) ->
                courseRequisitesSearch(courseSearchBox, courseSearchBy, courseTableView, initialCourses));
    }

    public void fillInstructorSearch(TextField  instructorTableSearchBox, ComboBox<String> instructorTableSearchBy, TableView<Instructor> instructorTableView){
        if(BaseController.getLanguage().equals("English")) {
            instructorTableSearchBy.getItems().addAll(FXCollections.observableArrayList("Instructor ID", "FirstName", "LastName"));
        } else if (BaseController.getLanguage().equals("Persian")){
            instructorTableSearchBy.getItems().addAll(FXCollections.observableArrayList("آی دی استاد", "نام کوچک استاد", "نام خانوادگی استاد"));
        }
        instructorTableSearchBox.textProperty().addListener((observable, oldValue, newValue) -> instructorSearch(instructorTableSearchBox,instructorTableSearchBy,instructorTableView));
    }

    public void fillUserInterface(Button goBackToHomePage,Button goToCourseManagement,Button goToEditCourse,Button goToDeleteCourse, Button gotoViewAllCourses,MenuButton menuButton, MenuItem logOut){
        UserInfo userInfo = HomePageController.user;
        menuButton.setText(userInfo.getUsername());

        if(goBackToHomePage != null) {
            goBackToHomePage.setOnAction((ActionEvent) -> {
                try {
                    goBackToHomePage(menuButton.getScene());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToCourseManagement != null) {
            goToCourseManagement.setOnAction((ActionEvent) -> {
                try {
                    goToCourseManagement(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToEditCourse != null) {
            goToEditCourse.setOnAction((ActionEvent) -> {
                try {
                    goToEditCourse(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToDeleteCourse != null) {
            goToDeleteCourse.setOnAction((ActionEvent) -> {
                try {
                    goToDeleteCourse(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(gotoViewAllCourses != null) {
            gotoViewAllCourses.setOnAction((ActionEvent) -> {
                try {
                    gotoViewAllCourses(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if (BaseController.getLanguage().equals("English")){
            logOut.setText("LogOut");
        } else if (BaseController.getLanguage().equals("Persian")){
            logOut.setText("خروج از حساب کاربری");
        }

        logOut.setOnAction((ActionEvent event) -> logOut(menuButton));
    }
    public void goToCourseManagement(MenuButton menuButton) throws Exception {
        new CourseManagementController(menuButton.getScene()).start();
    }
    public void goToEditCourse(MenuButton menuButton) throws Exception {
        new EditCourseController(menuButton.getScene()).start();
    }
    public void goToDeleteCourse(MenuButton menuButton) throws Exception{
        new DeleteCourseController(menuButton.getScene()).start();
    }
    public void gotoViewAllCourses(MenuButton menuButton) throws Exception {
        new ViewAllCoursesController(menuButton.getScene()).start();
    }

}
