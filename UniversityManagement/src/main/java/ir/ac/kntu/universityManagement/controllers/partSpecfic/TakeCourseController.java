package ir.ac.kntu.universityManagement.controllers.partSpecfic;

import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Course;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.scheduling.Schedule;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor

public class TakeCourseController extends BaseController {
    public TakeCourseController(String viewName, Scene scene) {
        super(viewName, scene);
    }

    public static void fillCourseTableView(TableColumn<Course, String> courseId, TableColumn<Course, String> name,
                                           TableColumn<Course, Integer> capacity, TableColumn<Course, Schedule> schedule,
                                           TableColumn<Course, Instructor> instructor, TableColumn<Course, LocalDate> finalExam,
                                           TableColumn<Course, Faculty> faculty, TableColumn<Course, Integer> credit,
                                           TableColumn<Course, Integer> enListed) {
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        schedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        instructor.setCellValueFactory(new PropertyValueFactory<>("instructor"));
        finalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        enListed.setCellValueFactory(new PropertyValueFactory<>("enListed"));
    }

    public static void fillTakenCourseTableView(TableColumn<Course, String> courseId, TableColumn<Course, String> name,
                                                TableColumn<Course, Integer> capacity, TableColumn<Course, Schedule> schedule,
                                                TableColumn<Course, Instructor> instructor, TableColumn<Course, LocalDate> finalExam,
                                                TableColumn<Course, Faculty> faculty, TableColumn<Course, Integer> credit,
                                                TableColumn<Course, Integer> enListed) {
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        schedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        instructor.setCellValueFactory(new PropertyValueFactory<>("instructor"));
        finalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        credit.setCellValueFactory(new PropertyValueFactory<>("units"));
        enListed.setCellValueFactory(new PropertyValueFactory<>("enListed"));
    }

    public void fillUserInterface(Button goBackToHomePage, MenuButton menuButton, MenuItem logOut) {
        UserInfo userInfo = HomePageController.user;
        menuButton.setText(userInfo.getUsername());

        if (goBackToHomePage != null) {
            goBackToHomePage.setOnAction((ActionEvent) -> {
                try {
                    goBackToHomePage(menuButton.getScene());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }


//        if (gotoViewAllCourses != null) {
//            gotoViewAllCourses.setOnAction((ActionEvent) -> {
//                try {
//                    gotoViewAllCourses(menuButton);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        }

        logOut.setOnAction((ActionEvent event) -> logOut(menuButton));
    }


}
