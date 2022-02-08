package ir.ac.kntu.universityManagement.controllers.managers;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.StudentController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.repositories.individuals.StudentRepository;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


@NoArgsConstructor
public class StudentManagementController extends StudentController implements Initializable {

    //----------------------------------------------------------------Constructor
    public StudentManagementController(Scene scene) {
        super("StudentManagement", scene);
    }
    //----------------------------------------------------------------


    //----------------------------------------------------------------Studetns TableView
    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, String> studentNumber;
    @FXML private TableColumn<Student, String> studentFirstName;
    @FXML private TableColumn<Student, String> studentLastName;
    @FXML private TableColumn<Student, String> studentNationalId;
    @FXML private TableColumn<Student, String> studentBirthdate;
    @FXML private TableColumn<Student, String> studentEmail;
    @FXML private TableColumn<Student, String> studentPhoneNumber;
    @FXML private TableColumn<Student, String> studentAddress;
    @FXML private TableColumn<Student, String> studentUserInfo;
    @FXML private TableColumn<Student, String> studentEntranceDate;
    @FXML private TableColumn<Student, String> studentFaculty;
    @FXML private ComboBox<String> studentSearchBy;
    @FXML private TextField studentSearchBox;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User TableView
    @FXML private TableView<UserInfo> userTableView;
    @FXML private ComboBox<String> userSearchBy;
    @FXML private TextField userSearchBox;
    @FXML private TableColumn<UserInfo, String> userUsername;
    @FXML private TableColumn<UserInfo, Role> userRole;
    //----------------------------------------------------------------


    //----------------------------------------------------------------Faculty TableView
    @FXML private TableView<Faculty> facultyTableView;
    @FXML private ComboBox<String> facultySearchBy;
    @FXML private TextField facultySearchBox;
    @FXML private TableColumn<Faculty, String> facultyName;
    //----------------------------------------------------------------


    //----------------------------------------------------------------Adding student attributes
    @FXML private TextField addFacultyName;
    @FXML private TextField addNationalId;
    @FXML private TextField addFirstName;
    @FXML private TextField addLastName;
    @FXML private DatePicker addBirthdate;
    @FXML private TextField addEmail;
    @FXML private TextField addPhoneNumber;
    @FXML private TextField addAddress;
    @FXML private TextField addUsername;
    @FXML private DatePicker addEntranceDate;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToStudentManagement;
    @FXML private Button goToEditStudent;
    @FXML private Button goToDeleteStudent;
    @FXML private Button gotoViewAllStudent;
    @FXML private Button refreshTablesButton;
    @FXML private Button selected;
    @FXML private Button save;
    @FXML private Label studentManagementHeader;
    @FXML private Label addFacultyNameLabel;
    @FXML private Label addNationalIdLabel;
    @FXML private Label addFirstNameLabel;
    @FXML private Label addLastNameLabel;
    @FXML private Label addBirthdateLabel;
    @FXML private Label addEmailLabel;
    @FXML private Label addPhoneNumberLabel;
    @FXML private Label addAddressLabel;
    @FXML private Label addUsernameLabel;
    @FXML private Label addEntranceDateLabel;
    @FXML private Label addStudentHeader;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static ObservableList<Student> students;
    public static StudentRepository studentRepository;
    public static boolean isUpTODate = true;
    //----------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillStudentTable(studentNumber, studentFirstName, studentLastName, studentNationalId, studentBirthdate,
                studentPhoneNumber, studentEmail, studentAddress, studentUserInfo, studentFaculty, studentEntranceDate);
        fillStudentSearch(studentSearchBox,studentSearchBy,studentTableView);
        refreshStudents(StudentManagementController.students,studentTableView);

        fillUserTable(userUsername,userRole);
        fillUserSearch(userSearchBox,userSearchBy, userTableView);
        refreshUsers(UserManagementController.users, userTableView);

        fillFacultyTable(facultyName);
        fillFacultySearch(facultySearchBox,facultySearchBy,facultyTableView);
        refreshFaculties(FacultyManagementController.faculties,facultyTableView);

        fillUserInterface(goBackToHomePage,goToStudentManagement, goToEditStudent, goToDeleteStudent, gotoViewAllStudent,menuButton,logOut);

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------
    }

    public static void refillStudents(){
        Thread thread = new Thread(() -> {
            students = FXCollections.observableArrayList(studentRepository.findAll());
            isUpTODate = true;

        });
        thread.setPriority(7);
        thread.start();
    }

    public void save() throws Exception {

        if(checkConstraints()) {
            List<UserInfo> foundUsers = Objects.requireNonNull(UserManagementController.getUsers()).stream().
                    filter(userInfo -> userInfo.getUsername() != null &&
                            userInfo.getUsername().equals(addUsername.getText().trim())).
                    collect(Collectors.toList());
            List<Faculty> foundFaculties = FacultyManagementController.faculties.stream().
                    filter(faculty -> faculty.getName() != null &&
                            faculty.getName().equals(addFacultyName.getText().trim())).
                    collect(Collectors.toList());
            if (foundUsers.isEmpty()) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is no user with this ID!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("کاربری با این آی دی وجود تدارد!");
                }
            } else if (foundFaculties.isEmpty()) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is no faculty with this name!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("دانشکده ایی با این نام وجود دارد!");
                }
            } else if (foundFaculties.size() > 1) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is more than one faculty with this name!\nPlease inform the admin!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("بیش از یک دانشکده با این نام وجود دارد!");
                }
            } else if (foundUsers.size() > 1) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is more than one user with this ID!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("بیش از یک کاربر با این آی دی وجود دارد!");
                }
            } else if (isNotValidEmail(addEmail.getText())) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("Email address is not valid!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("ایمیل وارد شده قابل قبول نیست!");
                }
            } else if (!foundUsers.get(0).getRole().equals(Role.STUDENT)) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("Selected user is not a student!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("کاربر انتخاب شده دانشجو نیست!");
                }
            } else {
                Student student = new Student(addNationalId.getText(), addFirstName.getText(), addLastName.getText(),
                        addEmail.getText(), addPhoneNumber.getText(), addBirthdate.getValue(),
                        addEntranceDate.getValue(), addAddress.getText(), foundUsers.get(0), foundFaculties.get(0));
                new Thread(() -> studentRepository.save(student)).start();
                StudentManagementController.students.add(student);
                clear();
                new StudentManagementController(addBirthdate.getScene()).start();
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
    }
    private Boolean checkConstraints(){
        List<Student> foundStudentsByNationalId = StudentManagementController.students.stream().
                filter(instructor -> instructor.getNationalId() != null &&
                        instructor.getNationalId().equals(addNationalId.getText().trim())).
                collect(Collectors.toList());
        List<Student> foundStudentsByEmail = StudentManagementController.students.stream().
                filter(instructor -> instructor.getEmail() != null &&
                        instructor.getEmail().equals(addEmail.getText().trim())).
                collect(Collectors.toList());
        List<Student> foundStudentsByUsername = StudentManagementController.students.stream().
                filter(instructor -> instructor.getUserInfo() != null &&
                        instructor.getUserInfo().getUsername().equals(addUsername.getText().trim())).
                collect(Collectors.toList());
        if(addFirstName.getText().trim().length() < 2){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("First Name must be at least 2 characters!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام کوچک باید حداقل 2 حرف باشد!");
            }
        } else if(addLastName.getText().length() < 2){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Last Name must be at least 2 characters!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام خانوادگی باید حداقل 2 حرف باشد!");
            }
        } else if(!foundStudentsByUsername.isEmpty()){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Username is assigned to another student!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام کاربری به دانشجوی دیگری نسبت داده شده است!");
            }
        } else if(!foundStudentsByEmail.isEmpty()) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Email address is already taken!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("ایمیل وارد شده در سیستم وجود دارد!");
            }
        } else if(!foundStudentsByNationalId.isEmpty()){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("National ID is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("کدملی قابل قبول نیست!");
            }
        } else {
            return true;
        }
        return false;
    }


    private void clear(){
        clearTextFields(addNationalId, addFacultyName, addFirstName, addLastName, addEmail, addPhoneNumber,
                addAddress, addUsername);
        addEntranceDate.setValue(null);
        addBirthdate.setValue(null);
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

    public static long getStudentsSize(){
        return studentRepository.count();
    }

    public void setRefreshTables(){
        refreshTables(null,null,studentTableView,facultyTableView,userTableView);
    }

    private void persianTranslate(ArrayList<String> fxmlID,ArrayList<String> text){
        fxmlID.add("goBackToHomePage");
        fxmlID.add("goToStudentManagement");
        fxmlID.add("goToEditStudent");
        fxmlID.add("goToDeleteStudent");
        fxmlID.add("gotoViewAllStudent");
        fxmlID.add("refreshTablesButton");
        fxmlID.add("studentManagementHeader");

        fxmlID.add("studentNumber");
        fxmlID.add("studentFirstName");
        fxmlID.add("studentLastName");
        fxmlID.add("studentNationalId");
        fxmlID.add("studentBirthdate");
        fxmlID.add("studentEmail");
        fxmlID.add("studentPhoneNumber");
        fxmlID.add("studentAddress");
        fxmlID.add("studentUserInfo");
        fxmlID.add("studentEntranceDate");
        fxmlID.add("studentFaculty");
        fxmlID.add("studentSearchBy");

        fxmlID.add("userSearchBy");
        fxmlID.add("userUsername");
        fxmlID.add("userRole");
        fxmlID.add("facultySearchBy");
        fxmlID.add("facultyName");

        fxmlID.add("addFacultyName");
        fxmlID.add("addNationalId");
        fxmlID.add("addFirstName");
        fxmlID.add("addLastName");
        fxmlID.add("addBirthdate");
        fxmlID.add("addEmail");
        fxmlID.add("addPhoneNumber");
        fxmlID.add("addAddress");
        fxmlID.add("addUsername");
        fxmlID.add("addEntranceDate");

        fxmlID.add("save");
        fxmlID.add("addStudentHeader");
        fxmlID.add("universityManagementHeader");



        new LanguageConfiguration(fxmlID,text,"StudentManagement");

        goBackToHomePage.setText(text.get(0));
        selected.setText(text.get(1));
        goToEditStudent.setText(text.get(2));
        goToDeleteStudent.setText(text.get(3));
        gotoViewAllStudent.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));
        studentManagementHeader.setText(text.get(6));

        studentNumber.setText(text.get(7));
        studentFirstName.setText(text.get(8));
        studentLastName.setText(text.get(9));
        studentNationalId.setText(text.get(10));
        studentBirthdate.setText(text.get(11));
        studentEmail.setText(text.get(12));
        studentPhoneNumber.setText(text.get(13));
        studentAddress.setText(text.get(14));
        studentUserInfo.setText(text.get(15));
        studentEntranceDate.setText(text.get(16));
        studentFaculty.setText(text.get(17));
        studentSearchBy.setPromptText(text.get(18));

        userSearchBy.setPromptText(text.get(19));
        userUsername.setText(text.get(20));
        userRole.setText(text.get(21));

        facultySearchBy.setPromptText(text.get(22));
        facultyName.setText(text.get(23));

        addFacultyNameLabel.setText(text.get(24));
        addNationalIdLabel.setText(text.get(25));
        addFirstNameLabel.setText(text.get(26));
        addLastNameLabel.setText(text.get(27));
        addBirthdateLabel.setText(text.get(28));
        addEmailLabel.setText(text.get(29));
        addPhoneNumberLabel.setText(text.get(30));
        addAddressLabel.setText(text.get(31));
        addUsernameLabel.setText(text.get(32));
        addEntranceDateLabel.setText(text.get(33));

        save.setText(text.get(34));
        addStudentHeader.setText(text.get(35));
        universityManagementHeader.setText(text.get(36));

    }
}
