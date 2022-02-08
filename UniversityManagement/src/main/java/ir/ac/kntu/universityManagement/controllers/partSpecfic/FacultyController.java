package ir.ac.kntu.universityManagement.controllers.partSpecfic;

import ir.ac.kntu.universityManagement.controllers.deleters.DeleteFacultyController;
import ir.ac.kntu.universityManagement.controllers.editors.EditFacultyController;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.managers.FacultyManagementController;
import ir.ac.kntu.universityManagement.controllers.viewLists.ViewAllFacultiesController;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FacultyController extends BaseController {

    public FacultyController(String viewName, Scene scene){super(viewName,scene);}

    public void fillFacultyTable(TableColumn<Faculty, String> facultyId,TableColumn<Faculty , String> facultyName,TableColumn<Faculty, Integer> facultyNumberOfClasses,
                                 TableColumn<Faculty, Integer> facultyNumberOfStudents ,TableColumn<Faculty, Integer> facultyNumberOfInstructors, TableColumn<Faculty, String> facultyPhoneNumber){

        facultyId.setCellValueFactory(new PropertyValueFactory<>("facultyId"));
        facultyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        facultyNumberOfClasses.setCellValueFactory(new PropertyValueFactory<>("numberOfClasses"));
        facultyNumberOfStudents.setCellValueFactory(new PropertyValueFactory<>("numberOfStudents"));
        facultyNumberOfInstructors.setCellValueFactory(new PropertyValueFactory<>("numberOfInstructors"));
        facultyPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    public void fillFacultySearch(TextField facultySearchBox, ComboBox<String> faucltySearchBy, TableView<Faculty> facultyTableView){
        if(BaseController.getLanguage().equals("English")) {
            faucltySearchBy.getItems().addAll(FXCollections.observableArrayList("Faculty ID", "Faculty Name", "Num of Classes", "Num of Students", "Num of Instructors", "PhoneNumber"));
        } else if (BaseController.getLanguage().equals("Persian")){
            faucltySearchBy.getItems().addAll(FXCollections.observableArrayList("آی دی", "نام دانشکده", "تعداد کلاس", "تعداد دانشجو", "تعداد استاد", "شماره تماس"));
        }
        facultySearchBox.textProperty().addListener((observable, oldValue, newValue) -> facultySearch(facultySearchBox,faucltySearchBy,facultyTableView));
    }

    public void fillUserInterface(Button goBackToHomePage, Button goToFacultyManagement, Button goToEditFaculty, Button goToDeleteFaculty, Button gotoViewAllFaculties, MenuButton menuButton, MenuItem logOut){
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

        if(goToFacultyManagement != null) {
            goToFacultyManagement.setOnAction((ActionEvent) -> {
                try {
                    goToFacultyManagement(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToEditFaculty != null) {
            goToEditFaculty.setOnAction((ActionEvent) -> {
                try {
                    goToEditFaculty(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToDeleteFaculty != null) {
            goToDeleteFaculty.setOnAction((ActionEvent) -> {
                try {
                    goToDeleteFaculty(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(gotoViewAllFaculties != null) {
            gotoViewAllFaculties.setOnAction((ActionEvent) -> {
                try {
                    goToViewAllFaculties(menuButton);
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

    public void goToFacultyManagement(MenuButton menuButton) throws Exception {
        new FacultyManagementController(menuButton.getScene()).start();
    }
    public void goToEditFaculty(MenuButton menuButton) throws Exception {
        new EditFacultyController(menuButton.getScene()).start();
    }
    public void goToDeleteFaculty(MenuButton menuButton) throws Exception {
        new DeleteFacultyController(menuButton.getScene()).start();
    }
    public void goToViewAllFaculties(MenuButton menuButton) throws Exception {
        new ViewAllFacultiesController(menuButton.getScene()).start();
    }
}
