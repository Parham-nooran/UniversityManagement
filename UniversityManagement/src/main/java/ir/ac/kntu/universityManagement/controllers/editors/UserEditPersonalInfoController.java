package ir.ac.kntu.universityManagement.controllers.editors;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.general.PersonalInfoController;
import ir.ac.kntu.universityManagement.controllers.managers.InstructorManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.StudentManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.UserManagementController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.individuals.Manager;
import ir.ac.kntu.universityManagement.models.entities.individuals.Person;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.repositories.individuals.ManagerRepository;
import ir.ac.kntu.universityManagement.models.settings.Language;
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
import org.controlsfx.control.Notifications;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@NoArgsConstructor
public class UserEditPersonalInfoController extends BaseController implements Initializable {

    //----------------------------------------------------------------Constructor
     public UserEditPersonalInfoController(Scene scene){super("UserPersonalInfoEdit",scene);}
    //----------------------------------------------------------------


    //----------------------------------------------------------------Edit personal Info fields
    @FXML private TextField editEmail;
    @FXML private TextField editAddress;
    @FXML private TextField editPhoneNumber;
    @FXML private PasswordField editPassword;
    @FXML private PasswordField editRepeatPassword;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User Interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button homePageButton;
    @FXML private Button viewPersonalInfoButton;
    @FXML private Button selected;
    @FXML private Button save;


    @FXML private Label personalInfoHeader;
    @FXML private Label editPersonalInfoHeader;
    @FXML private Label addressLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneNumberLabel;
    @FXML private Label passwordLabel;
    @FXML private Label repeatPasswordLabel;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static ManagerRepository managerRepository;
    public static Person person;
    public static Student student;
    public static Instructor instructor;
    private final UserInfo user = HomePageController.user;
    //----------------------------------------------------------------

    public void setLogOut(){
        logOut(menuButton);
    }

    public void setGoBackToHomePage() throws Exception {
        goBackToHomePage(menuButton.getScene());
    }

    public void goToViewPersonalInfo() throws Exception {
        new PersonalInfoController(menuButton.getScene()).start();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        menuButton.setText(user.getUsername());

        findUser();

        if(person != null){
            editEmail.setText(person.getEmail() != null ? person.getEmail() : "");
            editAddress.setText(person.getAddress() != null ? person.getAddress() : "");
            editPhoneNumber.setText(person.getPhoneNumber() != null ? person.getPhoneNumber() : "");
        }


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

    public void edit() throws Exception {
        if (isNotValidEmail(editEmail.getText().trim())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Email address is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("ایمیل وارد شده قابل قبول نیست!");
            }
        } else if (!editPassword.getText().equals(editRepeatPassword.getText())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Password doesn't match the repeated password!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("رمز وارد شده با رمز تکرار شده برار نیستند!");
            }
        }
        else {
            person.setAddress(editAddress.getText().trim());
            person.setEmail(editEmail.getText().trim());
            person.setPhoneNumber(editPhoneNumber.getText().trim());

            if(user.getRole().equals(Role.STUDENT)){
                new Thread(() -> StudentManagementController.studentRepository.save((Student) person)).start();
            }
            else if(user.getRole().equals(Role.INSTRUCTOR)){
                new Thread(() -> InstructorManagementController.instructorRepository.save((Instructor) person)).start();
            }

            user.setPassword(BCrypt.hashpw(editPassword.getText().trim(), BCrypt.gensalt(10)));
            new Thread(() -> UserManagementController.userInfoRepository.save(user)).start();
            editAddress.setText("");
            editEmail.setText("");
            editPhoneNumber.setText("");
            editPassword.setText("");
            editRepeatPassword.setText("");
            refreshPersonalInfo(person);
            Image successNot = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/GeneralIcons/successNot.png"))));
            if (BaseController.getLanguage().equals("English")) {
                Notifications notification = Notifications.create()
                        .title("Successfully Edited ! ")
                        .text("You can see it now !!!!")
                        .graphic(new ImageView(successNot))
                        .hideAfter(Duration.seconds(10))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            } else if (BaseController.getLanguage().equals("Persian")) {
                Notifications notification = Notifications.create()
                        .title("با موفقیت ثبت شد")
                        .text("می توانید تغییرات را ببینید!!!!")
                        .graphic(new ImageView(successNot))
                        .hideAfter(Duration.seconds(10))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }
        }
    }


    private static final Image ErrorNot = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/GeneralIcons/ErrorNot.png"))));
    private void makeErrorMassage(String message){
        Notifications notification = Notifications.create()
                .title(" Error ! ")
                .text(message)
                .graphic(new ImageView(ErrorNot))
                .hideAfter(Duration.seconds(10))
                .position(Pos.CENTER);
        notification.show();
    }

    private void persianTranslate(ArrayList<String> fxmlID, ArrayList<String> text){
        fxmlID.add("homePageButton");
        fxmlID.add("viewPersonalInfoButton");
        fxmlID.add("editPersonalInfo");
        fxmlID.add("personalInfoHeader");
        fxmlID.add("logOut");
        fxmlID.add("addressLabel");
        fxmlID.add("emailLabel");
        fxmlID.add("phoneNumberLabel");
        fxmlID.add("editPersonalInfoHeader");
        fxmlID.add("save");

        fxmlID.add("passwordLabel");
        fxmlID.add("repeatPasswordLabel");
        fxmlID.add("universityManagementHeader");

        new LanguageConfiguration(fxmlID,text,"ViewPersonalInfo");

        homePageButton.setText(text.get(0));
        viewPersonalInfoButton.setText(text.get(1));
        selected.setText(text.get(2));
        personalInfoHeader.setText(text.get(3));
        logOut.setText(text.get(4));
        addressLabel.setText(text.get(5));
        emailLabel.setText(text.get(6));
        phoneNumberLabel.setText(text.get(7));
        editPersonalInfoHeader.setText(text.get(8));
        save.setText(text.get(9));

        passwordLabel.setText(text.get(10));
        repeatPasswordLabel.setText(text.get(11));
        universityManagementHeader.setText(text.get(12));
    }
}
