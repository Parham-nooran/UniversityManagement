package ir.ac.kntu.universityManagement.controllers.partSpecfic;

import ir.ac.kntu.universityManagement.controllers.deleters.DeleteInstructorController;
import ir.ac.kntu.universityManagement.controllers.editors.EditInstructorController;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.managers.InstructorManagementController;
import ir.ac.kntu.universityManagement.controllers.viewLists.ViewAllInstructorsController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InstructorController extends BaseController {


    //----------------------------------------------------------------Constructor
    public InstructorController(String viewName, Scene scene) {super(viewName,scene);}
    //----------------------------------------------------------------

    public void fillInstructorTable(TableColumn<Instructor, String> instructorId,TableColumn<Instructor, String> firstName,TableColumn<Instructor, String> lastName,
                                    TableColumn<Instructor, String> nationalId,TableColumn<Instructor, String> phoneNumber,TableColumn<Instructor, String> email,
                                    TableColumn<Instructor, String> address,TableColumn<Instructor, String> faculty,TableColumn<Instructor, String> userInfo,
                                    TableColumn<Instructor, String> birthdate, TableColumn<Instructor, String> entranceDate){
        instructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        nationalId.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        userInfo.setCellValueFactory(new PropertyValueFactory<>("userInfo"));
        birthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        entranceDate.setCellValueFactory(new PropertyValueFactory<>("entranceDate"));
    }

    public void fillUserTable(TableColumn<UserInfo , String> userUsername,TableColumn<UserInfo , Role> userRole){
        userUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    public void fillFacultyTable(TableColumn<Faculty, String> facultyName){
        facultyName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }


    public void fillInstructorSearch(TextField instructorSearchBox, ComboBox<String> instructorSearchBy, TableView<Instructor> instructorTableView){
        if(BaseController.getLanguage().equals("English")){
            instructorSearchBy.getItems().addAll(FXCollections.observableArrayList("Instructor ID", "FirstName",
                    "LastName", "National ID", "PhoneNumber", "Email", "Address", "Faculty", "Birthdate",
                    "Username"));
        } else if (BaseController.getLanguage().equals("Persian")){
            instructorSearchBy.getItems().addAll(FXCollections.observableArrayList("آی دی استاد", "نام کوچک استاد",
                    "نام خانوادگی استاد", "کد ملی استاد", "شماره تماس استاد", "ایمیل استاد", "آدرس استاد", "دانشکده استاد", "تاریخ تولد استاد",
                    "یوسرنیم استاد"));
        }
        instructorSearchBox.textProperty().addListener((observable, oldValue, newValue) -> instructorSearch(instructorSearchBox,instructorSearchBy,instructorTableView));
    }

    public void fillUserSearch(TextField userSearchBox,ComboBox<String> userSearchBy, TableView<UserInfo> userTableview){
        if(BaseController.getLanguage().equals("English")){
            userSearchBy.getItems().addAll(FXCollections.observableArrayList("Username", "Role"));
        } else if (BaseController.getLanguage().equals("Persian")){
            userSearchBy.getItems().addAll(FXCollections.observableArrayList("یوسرنیم", "نقش"));
        }
        userSearchBox.textProperty().addListener((observable, oldValue, newValue) -> userSearch(userSearchBox,userSearchBy,userTableview));
    }

    public void fillFacultySearch(TextField facultySearchBox, ComboBox<String> facultySearchBy, TableView<Faculty> facultyTableView){
        if(BaseController.getLanguage().equals("English")){
            facultySearchBy.getItems().addAll(FXCollections.observableArrayList("Faculty Name"));
        } else if (BaseController.getLanguage().equals("Persian")){
            facultySearchBy.getItems().addAll(FXCollections.observableArrayList("نام دانشکده"));
        }
        facultySearchBox.textProperty().addListener((observable, oldValue, newValue) -> facultySearch(facultySearchBox,facultySearchBy,facultyTableView));
    }

    public void fillUserInterface(Button goBackToHomePage, Button goToInstructorManagement, Button goToEditInstructor, Button goToDeleteInstructor, Button goToViewAllInstructors, MenuButton menuButton, MenuItem logOut){
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

        if(goToInstructorManagement != null) {
            goToInstructorManagement.setOnAction((ActionEvent) -> {
                try {
                    goToInstructorManagement(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToEditInstructor != null) {
            goToEditInstructor.setOnAction((ActionEvent) -> {
                try {
                    goToEditInstructor(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToDeleteInstructor != null) {
            goToDeleteInstructor.setOnAction((ActionEvent) -> {
                try {
                    goToDeleteInstructor(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToViewAllInstructors != null) {
            goToViewAllInstructors.setOnAction((ActionEvent) -> {
                try {
                    goToViewAllInstructor(menuButton);
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

    public void goToInstructorManagement(MenuButton menuButton) throws Exception {
        new InstructorManagementController(menuButton.getScene()).start();
    }

    public void goToEditInstructor(MenuButton menuButton) throws Exception {
        new EditInstructorController(menuButton.getScene()).start();
    }

    public void goToDeleteInstructor(MenuButton menuButton) throws Exception{
        new DeleteInstructorController(menuButton.getScene()).start();
    }

    public void goToViewAllInstructor(MenuButton menuButton) throws Exception{
        new ViewAllInstructorsController(menuButton.getScene()).start();
    }

}
