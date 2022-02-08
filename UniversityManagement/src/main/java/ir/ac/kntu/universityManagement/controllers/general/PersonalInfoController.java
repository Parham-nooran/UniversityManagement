package ir.ac.kntu.universityManagement.controllers.general;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.editors.AdminEditPersonalInfoController;
import ir.ac.kntu.universityManagement.controllers.editors.UserEditPersonalInfoController;
import ir.ac.kntu.universityManagement.controllers.managers.InstructorManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.StudentManagementController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.individuals.Manager;
import ir.ac.kntu.universityManagement.models.entities.individuals.Person;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.repositories.individuals.ManagerRepository;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static ir.ac.kntu.universityManagement.controllers.general.HomePageController.applicationContext;

@Setter
@NoArgsConstructor
public class PersonalInfoController extends BaseController implements Initializable {

    @FXML private TextField nationalId;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField facultyName;
    @FXML private DatePicker birthdate;
    @FXML private DatePicker entranceDate;
    @FXML private TextField username;
    @FXML private TextField address;
    @FXML private TextField phoneNumber;
    @FXML private TextField firstName;
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;

    @FXML private Button homePageButton;
    @FXML private Button selected;
    @FXML private Button editPersonalInfo;

    @FXML private Label personalInfoHeader;
    @FXML private Label userNameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label addressLabel;
    @FXML private Label nationalIdLabel;
    @FXML private Label emailLabel;
    @FXML private Label birthDayLabel;
    @FXML private Label phoneNumberLabel;
    @FXML private Label facultyNameLabel;
    @FXML private Label entranceDateLabel;
    @FXML private Label universityManagementHeader;

    public static ManagerRepository managerRepository;
    public static Person person;
    private UserInfo userInfo = HomePageController.user;

    public PersonalInfoController(Scene scene) {
        super("PersonalInfo", scene);
    }

    public void goBackToHomePage() throws Exception {
        new HomePageController(menuButton.getScene()).start();
    }

    public void goToPersonalInfoEdit() throws Exception {
        if(userInfo.getRole().equals(Role.ADMIN)) {
            new AdminEditPersonalInfoController(menuButton.getScene()).start();
        }
        else if(!userInfo.getRole().equals(Role.ADMIN)){
            if(person != null) {
                new UserEditPersonalInfoController(menuButton.getScene()).start();
            }else {
                makeErrorMassage();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        username.setText(userInfo.getUsername());

        if(person == null){
            findUser();
        }

        if(person != null){
            nationalId.setText(person.getNationalId() != null ? person.getNationalId() : "");
            firstName.setText(person.getFirstName() != null ? person.getFirstName() : "");
            lastName.setText(person.getLastName() != null ? person.getLastName() : "");
            email.setText(person.getEmail() != null ? person.getEmail() : "");
            birthdate.setValue(person.getBirthdate() != null ? person.getBirthdate() : null);
            facultyName.setText(person.getFaculty() != null ? person.getFaculty().toString() : "");
            address.setText(person.getAddress() != null ? person.getAddress() : "");
            phoneNumber.setText(person.getPhoneNumber() != null ? person.getPhoneNumber() : "");
            entranceDate.setValue(person.getEntranceDate() != null ? person.getEntranceDate() : null);
            birthdate.setEditable(false);
            birthdate.setOnMouseClicked(e -> {
                if(!birthdate.isEditable())
                    birthdate.hide();
            });
            entranceDate.setEditable(false);
            entranceDate.setOnMouseClicked(e -> {
                if(!entranceDate.isEditable())
                    entranceDate.hide();
            });
        }

        logOut.setOnAction((ActionEvent event) -> {
            abort();
            applicationContext.publishEvent(new LoginController.StageReadyEvent(menuButton.getScene().getWindow()));
        });
        menuButton.setText(userInfo.getUsername());

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------
    }

    public static void findUser(){
        UserInfo userInfo = HomePageController.user;
        if(userInfo != null) {
            if (userInfo.getRole().equals(Role.STUDENT)) {
                List<Student> students;
                if(StudentManagementController.students != null) {
                    students = StudentManagementController.students.stream().
                            filter(student -> student.getUserInfo() != null &&
                                    student.getUserInfo().getId().equals(userInfo.getId())).
                            collect(Collectors.toList());
                } else {
                    students = StudentManagementController.studentRepository.findByUserInfoId(userInfo.getId());
                }
                if (!students.isEmpty()) {
                    person = students.get(0);
                } else{
                    person = null;
                }
            } else if (userInfo.getRole().equals(Role.INSTRUCTOR)) {
                List<Instructor> instructors;
                if(InstructorManagementController.instructors != null) {
                    instructors = InstructorManagementController.instructors.stream().
                            filter(instructor -> instructor.getUserInfo() != null &&
                                    instructor.getUserInfo().getId().equals(userInfo.getId())).
                            collect(Collectors.toList());
                } else {
                    instructors = InstructorManagementController.instructorRepository.findByUserInfoId(userInfo.getId());
                }
                if (!instructors.isEmpty()) {
                    person = instructors.get(0);
                } else{
                    person = null;
                }
            } else if(userInfo.getRole().equals(Role.ADMIN)){
                List<Manager> managers = managerRepository.findByUserInfoId(userInfo.getId());
                if(!managers.isEmpty()){
                    person = managers.get(0);
                } else{
                    person = null;
                }
            } else{
                person = null;
            }
        }
    }

    private static Image ErrorNot = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/GeneralIcons/ErrorNot.png"))));
    private void makeErrorMassage(){
        Notifications notification = Notifications.create()
                .title(" Error ! ")
                .text("You are not registered in system ... Please inform Admin!")
                .graphic(new ImageView(ErrorNot))
                .hideAfter(Duration.seconds(10))
                .position(Pos.CENTER);
        notification.show();
    }

    private void persianTranslate(ArrayList<String> fxmlID,ArrayList<String> text){
        fxmlID.add("homePageButton");
        fxmlID.add("selected");
        fxmlID.add("editPersonalInfo");
        fxmlID.add("personalInfoHeader");
        fxmlID.add("logOut");
        fxmlID.add("userNameLabel");
        fxmlID.add("firstNameLabel");
        fxmlID.add("lastNameLabel");
        fxmlID.add("addressLabel");
        fxmlID.add("nationalIdLabel");
        fxmlID.add("emailLabel");
        fxmlID.add("birthDayLabel");
        fxmlID.add("phoneNumberLabel");
        fxmlID.add("facultyNameLabel");
        fxmlID.add("entranceDateLabel");
        fxmlID.add("universityManagementHeader");

        new LanguageConfiguration(fxmlID,text,"ViewPersonalInfo");

        homePageButton.setText(text.get(0));
        selected.setText(text.get(1));
        editPersonalInfo.setText(text.get(2));
        personalInfoHeader.setText(text.get(3));
        logOut.setText(text.get(4));
        userNameLabel.setText(text.get(5));
        firstNameLabel.setText(text.get(6));
        lastNameLabel.setText(text.get(7));
        addressLabel.setText(text.get(8));
        nationalIdLabel.setText(text.get(9));
        emailLabel.setText(text.get(10));
        birthDayLabel.setText(text.get(11));
        phoneNumberLabel.setText(text.get(12));
        facultyNameLabel.setText(text.get(13));
        entranceDateLabel.setText(text.get(14));
        universityManagementHeader.setText(text.get(15));
    }
}
