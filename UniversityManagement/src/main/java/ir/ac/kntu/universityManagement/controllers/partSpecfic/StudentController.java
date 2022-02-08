package ir.ac.kntu.universityManagement.controllers.partSpecfic;

import ir.ac.kntu.universityManagement.controllers.deleters.DeleteStudentController;
import ir.ac.kntu.universityManagement.controllers.editors.EditStudentController;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.managers.StudentManagementController;
import ir.ac.kntu.universityManagement.controllers.viewLists.ViewAllStudentsController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StudentController extends BaseController {

    //----------------------------------------------------------------Constructor
    public StudentController(String viewName, Scene scene) {super(viewName,scene);}
    //----------------------------------------------------------------


    public void fillStudentTable(TableColumn<Student, String> studentNumber, TableColumn<Student,
            String> firstName, TableColumn<Student, String> lastName, TableColumn<Student,
            String> nationalId, TableColumn<Student, String> birthdate, TableColumn<Student,
            String> phoneNumber, TableColumn<Student, String> email, TableColumn<Student,
            String> address, TableColumn<Student, String> userInfo, TableColumn<Student,
            String> faculty, TableColumn<Student, String> entranceDate){
        studentNumber.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        nationalId.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
        birthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        userInfo.setCellValueFactory(new PropertyValueFactory<>("userInfo"));
        faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        entranceDate.setCellValueFactory(new PropertyValueFactory<>("entranceDate"));
    }

    public void fillUserTable(TableColumn<UserInfo , String> userUsername,TableColumn<UserInfo , Role> userRole){
        userUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    public void fillFacultyTable(TableColumn<Faculty, String> facultyName){
        facultyName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void fillStudentSearch(TextField studentSearchBox, ComboBox<String> studentSearchBy,TableView<Student> studentTableView){
        if(BaseController.getLanguage().equals("English")){
            studentSearchBy.getItems().addAll(FXCollections.observableArrayList("Student ID", "FirstName",
                    "LastName", "National ID", "Birthdate", "Username", "PhoneNumber", "Email", "Address", "Faculty"));
        } else if (BaseController.getLanguage().equals("Persian")){
            studentSearchBy.getItems().addAll(FXCollections.observableArrayList("شماره دانشجویی", "نام کوچک",
                    "نام خانوادگی", "کد ملی", "تاریخ تولد", "یوسرنیم", "شماره تماس", "ایمیل", "آدرس", "دانشکده"));
        }
        studentSearchBox.textProperty().addListener((observable, oldValue, newValue) -> studentSearch(studentSearchBox,studentSearchBy,studentTableView));
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
        if(BaseController.getLanguage().equals("English")) {
            facultySearchBy.getItems().addAll(FXCollections.observableArrayList("Faculty Name"));
        } else if (BaseController.getLanguage().equals("Persian")){
            facultySearchBy.getItems().addAll(FXCollections.observableArrayList("نام دانشکده"));
        }
        facultySearchBox.textProperty().addListener((observable, oldValue, newValue) -> facultySearch(facultySearchBox,facultySearchBy,facultyTableView));
    }

    public void fillUserInterface(Button goBackToHomePage, Button goToStudentManagement, Button goToEditStudent, Button goToDeleteStudent, Button goToViewAllStudent, MenuButton menuButton, MenuItem logOut){
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

        if(goToStudentManagement != null) {
            goToStudentManagement.setOnAction((ActionEvent) -> {
                try {
                    goToStudentManagement(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToEditStudent != null) {
            goToEditStudent.setOnAction((ActionEvent) -> {
                try {
                    goToEditStudent(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToDeleteStudent != null) {
            goToDeleteStudent.setOnAction((ActionEvent) -> {
                try {
                    goToDeleteStudent(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToViewAllStudent != null) {
            goToViewAllStudent.setOnAction((ActionEvent) -> {
                try {
                    goToViewAllStudent(menuButton);
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

    public void goToStudentManagement(MenuButton menuButton) throws Exception {
        new StudentManagementController(menuButton.getScene()).start();
    }

    public void goToEditStudent(MenuButton menuButton) throws Exception {
        new EditStudentController(menuButton.getScene()).start();
    }

    public void goToDeleteStudent(MenuButton menuButton) throws Exception{
        new DeleteStudentController(menuButton.getScene()).start();
    }

    public void goToViewAllStudent(MenuButton menuButton) throws Exception{
        new ViewAllStudentsController(menuButton.getScene()).start();
    }
}
