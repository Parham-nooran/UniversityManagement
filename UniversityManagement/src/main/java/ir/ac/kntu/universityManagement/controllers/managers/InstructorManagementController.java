package ir.ac.kntu.universityManagement.controllers.managers;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.FreeTimeDeclarationController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.InstructorController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.repositories.individuals.InstructorRepository;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@NoArgsConstructor
public class InstructorManagementController extends InstructorController implements Initializable {

    //----------------------------------------------------------------Constructor
    public InstructorManagementController(Scene scene) {
        super("InstructorManagement", scene);
    }
    //----------------------------------------------------------------


    //----------------------------------------------------------------Instructor TableView
    @FXML private TableView<Instructor> instructorTableView;
    @FXML private TableColumn<Instructor, String> instructorId;
    @FXML private TableColumn<Instructor, String> instructorFirstName;
    @FXML private TableColumn<Instructor, String> instructorLastName;
    @FXML private TableColumn<Instructor, String> instructorPhoneNumber;
    @FXML private TableColumn<Instructor, String> instructorEmail;
    @FXML private TableColumn<Instructor, String> instructorAddress;
    @FXML private TableColumn<Instructor, String> instructorFaculty;
    @FXML private TableColumn<Instructor, String> instructorNationalId;
    @FXML private TableColumn<Instructor, String> instructorUserInfo;
    @FXML private TableColumn<Instructor, String> instructorBirthdate;
    @FXML private TableColumn<Instructor, String> instructorEntranceDate;
    @FXML private TextField instructorSearchBox;
    @FXML private ComboBox<String> instructorSearchBy;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User TableView
    @FXML private TableView<UserInfo> userTableview;
    @FXML private TextField userSearchBox;
    @FXML private ComboBox<String> userSearchBy;
    @FXML private TableColumn<UserInfo, String> userUsername;
    @FXML private TableColumn<UserInfo, Role> userRole;
    //----------------------------------------------------------------


    //----------------------------------------------------------------Faculty TableView
    @FXML private TableView<Faculty> facultyTableView;
    @FXML private TextField facultySearchBox;
    @FXML private ComboBox<String> facultySearchBy;
    @FXML private TableColumn<Faculty, String> facultyName;
    //----------------------------------------------------------------


    //----------------------------------------------------------------Adding instructor field attributes
    @FXML private TextField addNationalId;
    @FXML private TextField addFirstName;
    @FXML private TextField addPhoneNumber;
    @FXML private TextField addEmail;
    @FXML private TextField addLastName;
    @FXML private DatePicker addBirthdate;
    @FXML private TextField addAddress;
    @FXML public TextField addFacultyName;
    @FXML public TextField addUsername;
    @FXML public DatePicker addEntranceDate;
    //----------------------------------------------------------------

    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToInstructorManagement;
    @FXML private Button selected;
    @FXML private Button goToEditInstructor;
    @FXML private Button goToDeleteInstructor;
    @FXML private Button gotoViewAllInstructors;
    @FXML private Button refreshTablesButton;

    @FXML private Label addInstructorHeader;
    @FXML private Label addNationalIdLabel;
    @FXML private Label addFirstNameLabel;
    @FXML private Label addPhoneNumberLabel;
    @FXML private Label addEmailLabel;
    @FXML private Label addLastNameLabel;
    @FXML private Label addBirthdateLabel;
    @FXML private Label addAddressLabel;
    @FXML private Label addFacultyNameLabel;
    @FXML private Label addUsernameLabel;
    @FXML private Label addEntranceDateLabel;

    @FXML private Button save;
    @FXML private Button showFreeTimes;
    @FXML private Label instructorManagementHeader;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------

    //----------------------------------------------------------------This controller variables
    public static ObservableList<Instructor> instructors;
    public static ObservableList<Faculty> faculties;
    public static InstructorRepository instructorRepository;
    public static boolean isUpTODate = true;
    //----------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fillInstructorTable(instructorId,instructorFirstName, instructorLastName, instructorNationalId,
                instructorPhoneNumber, instructorEmail, instructorAddress, instructorFaculty, instructorUserInfo,
                instructorBirthdate, instructorEntranceDate);
        fillInstructorSearch(instructorSearchBox,instructorSearchBy,instructorTableView);
        refreshInstructors(InstructorManagementController.instructors,instructorTableView);

        fillUserTable(userUsername,userRole);
        fillUserSearch(userSearchBox,userSearchBy,userTableview);
        refreshUsers(UserManagementController.users,userTableview);

        fillFacultyTable(facultyName);
        fillFacultySearch(facultySearchBox,facultySearchBy,facultyTableView);
        refreshFaculties(FacultyManagementController.faculties,facultyTableView);

        fillUserInterface(goBackToHomePage,goToInstructorManagement,goToEditInstructor,goToDeleteInstructor,gotoViewAllInstructors,menuButton,logOut);

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------
        if(showFreeTimes != null){
            showFreeTimes.setOnAction((ActionEvent) -> {
                FreeTimeDeclarationController freeTimeDeclarationController =
                        new FreeTimeDeclarationController(menuButton.getScene());
                FreeTimeDeclarationController.calledFromHomePage = false;
                FreeTimeDeclarationController.readOnly = true;
                FreeTimeDeclarationController.instructor = instructorTableView.getSelectionModel().getSelectedItem();
                if(FreeTimeDeclarationController.calendarEntries == null){
                    FreeTimeDeclarationController.calendarEntries = FreeTimeDeclarationController.instructor.getFreeTimes();
                }
                try {
                    freeTimeDeclarationController.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void refillInstructors(){
        Thread thread = new Thread(() -> {
            instructors = FXCollections.observableArrayList(instructorRepository.
                    findAll());
            isUpTODate = true;
        });
        thread.setPriority(7);
        thread.start();
    }


    public void save() throws Exception {

        if(checkInformation()) {
            List<Faculty> foundFaculties = FacultyManagementController.faculties.stream().
                    filter(faculty -> faculty.getName() != null &&
                            faculty.getName().equals(addFacultyName.getText().trim())).collect(Collectors.toList());
            List<UserInfo> foundUsers = Objects.requireNonNull(UserManagementController.getUsers()).stream().
                    filter(userInfo -> userInfo.getUsername() != null &&
                            userInfo.getUsername().equals(addUsername.getText().trim())).collect(Collectors.toList());
            if (foundUsers.isEmpty()) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is no user with this ID!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("کاربری با این آی دی وجود ندارد!");
                }
            } else if (foundUsers.size() > 1) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is more than one user with this username!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("بیش از یک کاربر با این نام وجود دارد!");
                }
            } else if (foundFaculties.isEmpty()) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is no faculty with this name!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("دانشکده ایی با این نام وجود ندارد!");
                }
            } else if (foundFaculties.size() > 1) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is more than one\n faculty with this name!\nPlease inform the admin!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("بیش از یک دانشکده با این نام وجود دارد!");
                }
            } else if (isNotValidEmail(addEmail.getText())) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("Email address is not\n valid!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("ایمیل وارد شده قابل قبول نیست!");
                }
            } else if (!foundUsers.get(0).getRole().equals(Role.INSTRUCTOR)) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("Selected user is not an instructor!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("کاربر انتخاب شده استاد نیست!");
                }
            } else {
                Instructor instructor = new Instructor(addNationalId.getText(), addFirstName.getText(),
                        addLastName.getText(), addEmail.getText(), addPhoneNumber.getText(), addBirthdate.getValue(),
                        addEntranceDate.getValue(), addAddress.getText(), foundUsers.get(0), foundFaculties.get(0));
                new Thread(() -> InstructorManagementController.instructorRepository.save(instructor)).start();
                InstructorManagementController.instructors.add(instructor);
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

    private Boolean checkInformation(){
        List<Instructor> foundInstructorsByNationalId = InstructorManagementController.instructors.stream().
                filter(instructor -> instructor.getNationalId() != null &&
                        instructor.getNationalId().equals(addNationalId.getText().trim())).
                collect(Collectors.toList());
        List<Instructor> foundInstructorsByEmail = InstructorManagementController.instructors.stream().
                filter(instructor -> instructor.getEmail() != null &&
                        instructor.getEmail().equals(addEmail.getText().trim())).
                collect(Collectors.toList());
        List<Instructor> foundByUsername = InstructorManagementController.instructors.stream().
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
        }  else if(!foundByUsername.isEmpty()){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Username is assigned to another instructor!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام کاربری به استاد دیگری نسبت داده شده است!");
            }
        } else if(!foundInstructorsByEmail.isEmpty()) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Email address is already taken!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("ایمیل وارد شده در سیستم وجود دارد!");
            }
        } else if(!foundInstructorsByNationalId.isEmpty()){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("National ID is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("کدملی قابل قبول نیست!");
            }
        } else{
            return true;
        }
        return false;
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

    public static long getInstructorsSize(){
        return instructorRepository.count();
    }

    public void setRefreshTables(){
        refreshTables(null,instructorTableView,null,facultyTableView,userTableview);
    }

    private void persianTranslate(ArrayList<String> fxmlID,ArrayList<String> text){

        fxmlID.add("goBackToHomePage");
        fxmlID.add("goToInstructorManagement");
        fxmlID.add("goToEditInstructor");
        fxmlID.add("goToDeleteInstructor");
        fxmlID.add("gotoViewAllInstructors");
        fxmlID.add("refreshTablesButton");

        fxmlID.add("instructorId");
        fxmlID.add("instructorFirstName");
        fxmlID.add("instructorLastName");
        fxmlID.add("instructorPhoneNumber");
        fxmlID.add("instructorEmail");
        fxmlID.add("instructorAddress");
        fxmlID.add("instructorFaculty");
        fxmlID.add("instructorNationalId");
        fxmlID.add("instructorUserInfo");
        fxmlID.add("instructorBirthdate");
        fxmlID.add("instructorEntranceDate");
        fxmlID.add("instructorSearchBy");

        fxmlID.add("userSearchBy");
        fxmlID.add("userUsername");
        fxmlID.add("userRole");

        fxmlID.add("facultySearchBy");
        fxmlID.add("facultyName");

        fxmlID.add("addInstructorHeader");
        fxmlID.add("addNationalIdLabel");
        fxmlID.add("addFirstNameLabel");
        fxmlID.add("addPhoneNumberLabel");
        fxmlID.add("addEmailLabel");
        fxmlID.add("addLastNameLabel");
        fxmlID.add("addBirthdateLabel");
        fxmlID.add("addAddressLabel");
        fxmlID.add("addFacultyNameLabel");
        fxmlID.add("addUsernameLabel");
        fxmlID.add("addEntranceDateLabel");
        fxmlID.add("save");
        fxmlID.add("instructorManagementHeader");
        fxmlID.add("universityManagementHeader");



        new LanguageConfiguration(fxmlID,text,"InstructorManagement");


        goBackToHomePage.setText(text.get(0));
        selected.setText(text.get(1));
        goToEditInstructor.setText(text.get(2));
        goToDeleteInstructor.setText(text.get(3));
        gotoViewAllInstructors.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));

        instructorId.setText(text.get(6));
        instructorFirstName.setText(text.get(7));
        instructorLastName.setText(text.get(8));
        instructorPhoneNumber.setText(text.get(9));
        instructorEmail.setText(text.get(10));
        instructorAddress.setText(text.get(11));
        instructorFaculty.setText(text.get(12));
        instructorNationalId.setText(text.get(13));
        instructorUserInfo.setText(text.get(14));
        instructorBirthdate.setText(text.get(15));
        instructorEntranceDate.setText(text.get(16));
        instructorSearchBy.setPromptText(text.get(17));


        userSearchBy.setPromptText(text.get(18));
        userUsername.setText(text.get(19));
        userRole.setText(text.get(20));

        facultySearchBy.setPromptText(text.get(21));
        facultyName.setText(text.get(22));

        addInstructorHeader.setText(text.get(23));
        addNationalIdLabel.setText(text.get(24));
        addFirstNameLabel.setText(text.get(25));
        addPhoneNumberLabel.setText(text.get(26));
        addEmailLabel.setText(text.get(27));
        addLastNameLabel.setText(text.get(28));
        addBirthdateLabel.setText(text.get(29));
        addAddressLabel.setText(text.get(30));
        addFacultyNameLabel.setText(text.get(31));
        addUsernameLabel.setText(text.get(32));
        addEntranceDateLabel.setText(text.get(33));

        save.setText(text.get(34));
        instructorManagementHeader.setText(text.get(35));
        universityManagementHeader.setText(text.get(36));
    }
}
