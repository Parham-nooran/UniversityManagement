package ir.ac.kntu.universityManagement.controllers.editors;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.managers.FacultyManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.StudentManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.UserManagementController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.StudentController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.repositories.individuals.StudentRepository;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@NoArgsConstructor
public class EditStudentController extends StudentController implements Initializable {

    //----------------------------------------------------------------Constructor
    public EditStudentController(Scene scene) {
        super("EditStudent", scene);
    }
    //----------------------------------------------------------------


    //----------------------------------------------------------------------Student TableView
    @FXML private TableView<Student> studentTableView;
    @FXML private ComboBox<String> studentSearchBy;
    @FXML private TextField studentSearchBox;
    @FXML private TableColumn<Student, String> studentNumber;
    @FXML private TableColumn<Student, String> studentFirstName;
    @FXML private TableColumn<Student, String> studentLastName;
    @FXML private TableColumn<Student, String> studentNationalId;
    @FXML private TableColumn<Student, String> studentBirthdate;
    @FXML private TableColumn<Student, String> studentEmail;
    @FXML private TableColumn<Student, String> studentPhoneNumber;
    @FXML private TableColumn<Student, String> studentAddress;
    @FXML private TableColumn<Student, String> studentUserInfo;
    @FXML private TableColumn<Student, String> studentFaculty;
    @FXML private TableColumn<Student, String> studentEntranceDate;
    //----------------------------------------------------------------


    //----------------------------------------------------------------------User TableView
    @FXML private TableView<UserInfo> userTableView;
    @FXML private ComboBox<String> userSearchBy;
    @FXML private TextField userSearchBox;
    @FXML private TableColumn<UserInfo, String> userUsername;
    @FXML private TableColumn<UserInfo, Role> userRole;
    //----------------------------------------------------------------


    //----------------------------------------------------------------------Faculty TableView
    @FXML private TableView<Faculty> facultyTableView;
    @FXML private ComboBox<String> facultySearchBy;
    @FXML private TextField facultySearchBox;
    @FXML private TableColumn<Faculty, String> facultyName;
    //----------------------------------------------------------------


    //----------------------------------------------------------------------Editing student attributes
    @FXML private TextField editFacultyName;
    @FXML private AnchorPane editLoginError;
    @FXML private TextField editStudentId;
    @FXML private TextField editNationalId;
    @FXML private TextField editFirstName;
    @FXML private TextField editLastName;
    @FXML private DatePicker editBirthdate;
    @FXML private TextField editEmail;
    @FXML private TextField editPhoneNumber;
    @FXML private TextField editAddress;
    @FXML private TextField editUsername;
    @FXML private DatePicker editEntranceDate;
    @FXML private TextArea editErrorMessage;
    //----------------------------------------------------------------

    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToStudentManagement;
    @FXML private Button goToEditStudent;
    @FXML private Button goToDeleteStudent;
    @FXML private Button gotoViewAllStudent;

    @FXML private Button selected;
    @FXML private Button refreshTablesButton;
    @FXML private Label studentManagementHeader;


    @FXML private Button save;
    @FXML private Label editFacultyNameLabel;
    @FXML private Label editNationalIdLabel;
    @FXML private Label editFirstNameLabel;
    @FXML private Label editLastNameLabel;
    @FXML private Label editBirthdateLabel;
    @FXML private Label editEmailLabel;
    @FXML private Label editPhoneNumberLabel;
    @FXML private Label editAddressLabel;
    @FXML private Label editUsernameLabel;
    @FXML private Label editEntranceDateLabel;
    @FXML private Label editStudentHeader;
    @FXML private Label editStudentID;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------

    //----------------------------------------------------------------This controller variables
    public static boolean isUpTODate = true;
    public static Student student = null;
    public static StudentRepository studentRepository;
    //----------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillStudentTable(studentNumber, studentFirstName, studentLastName, studentNationalId, studentBirthdate,
                studentPhoneNumber, studentEmail, studentAddress, studentUserInfo, studentFaculty, studentEntranceDate);
        fillStudentSearch(studentSearchBox,studentSearchBy,studentTableView);
        refreshStudents(StudentManagementController.students,studentTableView);

        fillUserTable(userUsername,userRole);
        fillUserSearch(userSearchBox,userSearchBy,userTableView);
        refreshUsers(UserManagementController.users,userTableView);

        fillFacultyTable(facultyName);
        fillFacultySearch(facultySearchBox,facultySearchBy,facultyTableView);
        refreshFaculties(FacultyManagementController.faculties,facultyTableView);

        fillUserInterface(goBackToHomePage,goToStudentManagement,goToEditStudent,goToDeleteStudent,gotoViewAllStudent,menuButton,logOut);


        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------

        studentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                student = studentTableView.getSelectionModel().getSelectedItem();
                if (student.getStudentNumber() != null) {
                    editStudentId.setText(student.getStudentNumber());
                }else{
                    editStudentId.setText("");
                }
                if (student.getNationalId() != null) {
                    editNationalId.setText(student.getNationalId());
                }else{
                    editNationalId.setText("");
                }
                if (student.getFirstName() != null) {
                    editFirstName.setText(student.getFirstName());
                }else{
                    editFirstName.setText("");
                }
                if (student.getLastName() != null) {
                    editLastName.setText(student.getLastName());
                }else{
                    editLastName.setText("");
                }
                if (student.getPhoneNumber() != null) {
                    editPhoneNumber.setText(student.getPhoneNumber());
                }else{
                    editPhoneNumber.setText("");
                }
                if (student.getEmail() != null) {
                    editEmail.setText(student.getEmail());
                }else{
                    editEmail.setText("");
                }
                if (student.getAddress() != null) {
                    editAddress.setText(student.getAddress());
                }else{
                    editAddress.setText("");
                }
                if (student.getBirthdate() != null) {
                    editBirthdate.setValue(student.getBirthdate());
                }else{
                    editBirthdate.setValue(null);
                }
                if (student.getUserInfo() != null) {
                    editUsername.setText(student.getUserInfo().getUsername());
                }else{
                    editUsername.setText("");
                }
                if(student.getFaculty() != null){
                    editFacultyName.setText(student.getFaculty().getName());
                }else{
                    editFacultyName.setText("");
                }
                if(student.getEntranceDate() != null) {
                    editEntranceDate.setValue(editEntranceDate.getValue());
                }
                else{
                    editEntranceDate.setValue(null);
                }
            }
        });
    }

    public void edit() throws Exception {
        List<UserInfo> foundUsers = Objects.requireNonNull(UserManagementController.getUsers()).stream().
                filter(userInfo -> userInfo.getUsername().equals(editUsername.getText().trim())).
                collect(Collectors.toList());
        List<Faculty> foundFaculties = FacultyManagementController.faculties.stream().
                filter(faculty -> faculty.getName() != null &&
                        faculty.getName().equals(editFacultyName.getText().trim())).
                collect(Collectors.toList());
        if (checkConstraints()) {
            if (foundUsers.isEmpty()) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is no user with this ID!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("کاربری با این آی دی وجود ندارد!");
                }
            } else if (foundUsers.size() > 1) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is more than one user with this ID!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("بیش از یک کاربر با این آی دی در سیستم وجود دارد!");
                }
            } if(foundFaculties.isEmpty()){
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is no faculty with this name!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("دانشکده ایی با این نام وجود ندارد!");
                }
            } else if(foundFaculties.size() > 1){
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is more than one faculty with this name!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("بیش از یک دانشکده با این نام در سیستم وجود دارد!");
                }
            } else{
                student.setUserInfo(foundUsers.get(0));
                student.setFaculty(foundFaculties.get(0));
                setValues();
                new Thread(() -> studentRepository.save(student)).start();
                clear();
                new EditStudentController(studentTableView.getScene()).start();
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
        List<Student> foundStudents = StudentManagementController.students.stream().
                filter(student1 -> student1.getUserInfo() != null &&
                        student1.getUserInfo().getUsername().equals(editUsername.getText().trim())).
                collect(Collectors.toList());
        List<Student> foundStudentsNationalId = StudentManagementController.students.stream().
                filter(instructor -> instructor.getNationalId().equals(editNationalId.getText().trim())).
                collect(Collectors.toList());
        if(editFirstName.getText().trim().length() < 2) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("First Name must be at least 2 characters!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام کوچک باید حداقل 2 حرف باشد!");
            }
        } else if(editLastName.getText().length() < 2) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Last Name must be at least 2 characters!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام خانوادگی باید حداقل 2 حرف باشد!");
            }
        } else if(isNotValidEmail(editEmail.getText())){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Email address is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("ایمیل وارد شده قابل قبول نمی باشد!");
            }
        } else if(foundStudents.size() > 1 && !foundStudents.get(0).getId().equals(student.getId())){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Username is assigned to another student!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("یوسرنیم به دانشجوی دیگری نسبت داده شده است!");
            }
        }  else if(foundStudentsNationalId.size() > 1){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("National ID is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("کد ملی قابل قبول نیست!");
            }
        } else {
            return true;
        }
        return false;
    }

    private void setValues(){
        student.setStudentNumber(editStudentId.getText().trim());
        student.setNationalId(editNationalId.getText().trim());
        student.setFirstName(editFirstName.getText().trim());
        student.setLastName(editLastName.getText().trim());
        student.setEmail(editEmail.getText().trim());
        student.setAddress(editAddress.getText().trim());
        student.setPhoneNumber(editPhoneNumber.getText().trim());
        student.setBirthdate(editBirthdate.getValue());
        student.setAddress(editAddress.getText());
        student.setBirthdate(editBirthdate.getValue());
        student.setEntranceDate(editEntranceDate.getValue());
    }

    private void clear(){
        clearTextFields(editStudentId, editNationalId, editFirstName, editLastName, editEmail, editAddress,
                editPhoneNumber, editAddress, editUsername, editFacultyName);
        editBirthdate.setValue(null);
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

    public void setRefreshTables(){
        refreshTables(null,null,studentTableView,facultyTableView,userTableView);
    }

    private void persianTranslate(ArrayList<String> fxmlID, ArrayList<String> text){
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

        fxmlID.add("editStudentID");
        fxmlID.add("universityManagementHeader");




        new LanguageConfiguration(fxmlID,text,"StudentManagement");

        goBackToHomePage.setText(text.get(0));
        goToStudentManagement.setText(text.get(1));
        selected.setText(text.get(2));
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

        editFacultyNameLabel.setText(text.get(24));
        editNationalIdLabel.setText(text.get(25));
        editFirstNameLabel.setText(text.get(26));
        editLastNameLabel.setText(text.get(27));
        editBirthdateLabel.setText(text.get(28));
        editEmailLabel.setText(text.get(29));
        editPhoneNumberLabel.setText(text.get(30));
        editAddressLabel.setText(text.get(31));
        editUsernameLabel.setText(text.get(32));
        editEntranceDateLabel.setText(text.get(33));

        save.setText(text.get(34));
        editStudentHeader.setText(text.get(35));

        editStudentID.setText(text.get(36));
        universityManagementHeader.setText(text.get(37));

    }

}
