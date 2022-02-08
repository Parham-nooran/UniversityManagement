package ir.ac.kntu.universityManagement.controllers.editors;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.general.LoginController;
import ir.ac.kntu.universityManagement.controllers.general.PersonalInfoController;
import ir.ac.kntu.universityManagement.controllers.managers.FacultyManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.InstructorManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.StudentManagementController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.individuals.Manager;
import ir.ac.kntu.universityManagement.models.entities.individuals.Person;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lombok.NoArgsConstructor;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static ir.ac.kntu.universityManagement.controllers.general.HomePageController.applicationContext;

@NoArgsConstructor
public class AdminEditPersonalInfoController extends BaseController implements Initializable {

    @FXML private TextField nationalId;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField facultyName;
    @FXML private DatePicker birthdate;
    @FXML private TextField username;
    @FXML private TextField address;
    @FXML private TextField phoneNumber;
    @FXML private TextField firstName;
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;

    @FXML private Button homePageButton;
    @FXML private Button viewPersonalInfoButton;
    @FXML private Button selected;
    @FXML private Button save;


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
    @FXML private Label editPersonalInfoHeader;
    @FXML private Label universityManagementHeader;

    public static Person person;

    public void goBackToHomePage() throws Exception {
        new HomePageController(menuButton.getScene()).start();
    }

    public void goBackPersonalInfo() throws Exception {
        new PersonalInfoController(menuButton.getScene()).start();
    }


    public AdminEditPersonalInfoController(Scene scene) {super("PersonalInfoEdit", scene);}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserInfo userInfo = HomePageController.user;

        username.setText(userInfo.getUsername());

        if(person == null){
            findUser();
        }

        if(person != null){
            if(person.getNationalId() != null) {
                nationalId.setText(person.getNationalId());
            }else{
                nationalId.setText("");
            }
            if(person.getFirstName() != null) {
                firstName.setText(person.getFirstName());
            }else{
                firstName.setText("");
            }
            if(person.getLastName() != null) {
                lastName.setText(person.getLastName());
            }else{
                lastName.setText("");
            }
            if(person.getEmail() != null) {
                email.setText(person.getEmail());
            }else{
                email.setText("");
            }
            if(person.getBirthdate() != null) {
                birthdate.setValue(person.getBirthdate());
            }else{
                birthdate.setValue(null);
            }
            if(person.getFaculty() != null) {
                facultyName.setText(person.getFaculty().toString());
            }else{
                facultyName.setText(null);
            }
            if(person.getAddress() != null){
                address.setText(person.getAddress());
            }else{
                address.setText("");
            }
            if(person.getPhoneNumber() != null){
                phoneNumber.setText(person.getPhoneNumber());
            }else{
                phoneNumber.setText("");
            }
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

    public void save(){
        UserInfo userInfo = HomePageController.user;
        if(userInfo.getRole().equals(Role.ADMIN)){
            if(person == null){
                person = new Manager();
            }
            try {
                List<Faculty> foundFaculties = FacultyManagementController.faculties.stream().
                        filter(faculty -> faculty.getName() != null &&
                                faculty.getName().equals(facultyName.getText().trim())).
                        collect(Collectors.toList());
                List<Manager> foundByNationalId = PersonalInfoController.managerRepository.findByNationalId(nationalId.getText().trim());
                List<Manager> foundByEmail = PersonalInfoController.managerRepository.findByEmail(email.getText().trim());
                if(firstName.getText().trim().length() < 2){
                    if(BaseController.getLanguage().equals("English")){
                        makeErrorMassage("First Name must be at least 2 characters!");
                    } else if (BaseController.getLanguage().equals("Persian")){
                        makeErrorMassage("نام کوچک باید حداقل 2 کاراکتر باشد");
                    }
                } else if(lastName.getText().length() < 2){
                    if(BaseController.getLanguage().equals("English")){
                        makeErrorMassage("Last Name must be at least 2 characters!");
                    } else if (BaseController.getLanguage().equals("Persian")){
                        makeErrorMassage("نام خانوادگی باید حداقل 2 کاراکتر باشد");
                    }
                } else if(foundFaculties.isEmpty()){
                    if(BaseController.getLanguage().equals("English")){
                        makeErrorMassage("There is no faculty with this name!");
                    } else if (BaseController.getLanguage().equals("Persian")){
                        makeErrorMassage("دانشکده ایی با این اسم وجود ندارد");
                    }
                } else if(foundFaculties.size() > 1){
                    if(BaseController.getLanguage().equals("English")){
                        makeErrorMassage("There is more than one faculty with this name!" + " Please inform the admin!");
                    } else if (BaseController.getLanguage().equals("Persian")){
                        makeErrorMassage("بیش از یک دانشکده با این اسم وجود دارد! لطفا به مسئول آموزش اطلاع دهید!");
                    }
                } else if(isNotValidEmail(email.getText().trim())) {
                    if(BaseController.getLanguage().equals("English")){
                        makeErrorMassage("Email address is not valid!");
                    } else if (BaseController.getLanguage().equals("Persian")){
                        makeErrorMassage("ایمیل وارد شده صحیح نیست");
                    }
                } else if(foundByEmail.size() > 1 || (foundByEmail.size() > 0 &&
                        !foundByEmail.get(0).getId().equals(person.getId()))) {
                    if(BaseController.getLanguage().equals("English")){
                        makeErrorMassage("Email address is already taken!");
                    } else if (BaseController.getLanguage().equals("Persian")){
                        makeErrorMassage("ایمیل وارد شده قبلا در سیستم وارد شده است!");
                    }
                } else if(foundByNationalId.size() > 1 || (foundByNationalId.size() > 0 &&
                        !foundByNationalId.get(0).getId().equals(person.getId()))) {
                    if(BaseController.getLanguage().equals("English")){
                        makeErrorMassage("National ID is already taken!");
                    } else if (BaseController.getLanguage().equals("Persian")){
                        makeErrorMassage("کد ملی وارد شده قبلا در سیستم وارد شده است!");
                    }
                } else {
                    person.setFirstName(firstName.getText().trim());
                    person.setLastName(lastName.getText().trim());
                    person.setBirthdate(birthdate.getValue());
                    person.setEmail(email.getText().trim());
                    person.setAddress(address.getText().trim());
                    person.setPhoneNumber(phoneNumber.getText().trim());
                    person.setFaculty(foundFaculties.get(0));
                    person.setUserInfo(userInfo);
                    person.setNationalId(nationalId.getText().trim());
                    new Thread(() -> PersonalInfoController.managerRepository.save((Manager) person)).start();
                    PersonalInfoController.person = person;
                    firstName.setText("");
                    lastName.setText("");
                    birthdate.setValue(null);
                    email.setText("");
                    address.setText("");
                    phoneNumber.setText("");
                    nationalId.setText("");
                    facultyName.setText("");
                    username.setText("");
                    new AdminEditPersonalInfoController(menuButton.getScene()).start();
                    Image successNot = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/GeneralIcons/successNot.png"))));
                    if(BaseController.getLanguage().equals("English")){
                        Notifications notification = Notifications.create()
                                .title("Successfully Edited ! ")
                                .text("You can see it now !!!!")
                                .graphic(new ImageView(successNot))
                                .hideAfter(Duration.seconds(10))
                                .position(Pos.BOTTOM_RIGHT);
                        notification.show();
                    } else if (BaseController.getLanguage().equals("Persian")){
                        Notifications notification = Notifications.create()
                                .title("با موفقیت ثبت شد")
                                .text("می توانید تغییرات را ببینید!!!!")
                                .graphic(new ImageView(successNot))
                                .hideAfter(Duration.seconds(10))
                                .position(Pos.BOTTOM_RIGHT);
                        notification.show();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("Please wait a few seconds and try again!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("لطفا چند لحظه صبر کنید و بعد دوباره تلاش کنید");
                }
            }
        }
    }

    public static void findUser(){
        UserInfo userInfo = HomePageController.user;
        if(userInfo != null) {
            if (userInfo.getRole().equals(Role.STUDENT)) {
                List<Student> students;
                if(StudentManagementController.students != null) {
                    students = StudentManagementController.students.stream().
                            filter(student -> student.getUserInfo().getId().equals(userInfo.getId())).
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
                            filter(instructor -> instructor.getUserInfo().getId().equals(userInfo.getId())).
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
                List<Manager> managers = PersonalInfoController.managerRepository.findByUserInfoId(userInfo.getId());
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
        fxmlID.add("userNameLabel");
        fxmlID.add("firstNameLabel");
        fxmlID.add("lastNameLabel");
        fxmlID.add("addressLabel");
        fxmlID.add("nationalIdLabel");
        fxmlID.add("emailLabel");
        fxmlID.add("birthDayLabel");
        fxmlID.add("phoneNumberLabel");
        fxmlID.add("facultyNameLabel");
        fxmlID.add("editPersonalInfoHeader");
        fxmlID.add("save");
        fxmlID.add("universityManagementHeader");

        new LanguageConfiguration(fxmlID,text,"ViewPersonalInfo");

        homePageButton.setText(text.get(0));
        viewPersonalInfoButton.setText(text.get(1));
        selected.setText(text.get(2));
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
        editPersonalInfoHeader.setText(text.get(14));
        save.setText(text.get(15));
        universityManagementHeader.setText(text.get(16));
    }

}
