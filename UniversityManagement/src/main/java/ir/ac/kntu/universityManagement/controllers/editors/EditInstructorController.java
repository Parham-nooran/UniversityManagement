package ir.ac.kntu.universityManagement.controllers.editors;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.FreeTimeDeclarationController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.managers.FacultyManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.InstructorManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.UserManagementController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.InstructorController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.universityEntities.Faculty;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
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
public class EditInstructorController extends InstructorController implements Initializable {

    //----------------------------------------------------------------Constructor
    public EditInstructorController(Scene scene) {
        super("EditInstructor",scene);
    }
    //----------------------------------------------------------------Constructor



    //----------------------------------------------------------------Instructor tableview
    @FXML private TableView<Instructor> instructorTableView;
    @FXML private TextField instructorSearchBox;
    @FXML private TableColumn<Instructor, String> instructorId;
    @FXML private TableColumn<Instructor, String> instructorFirstName;
    @FXML private TableColumn<Instructor, String> instructorLastName;
    @FXML private TableColumn<Instructor, String> instructorPhoneNumber;
    @FXML private TableColumn<Instructor, String> instructorEmail;
    @FXML private TableColumn<Instructor, String> instructorEntranceDate;
    @FXML private TableColumn<Instructor, String> instructorAddress;
    @FXML private TableColumn<Instructor, String> instructorFaculty;
    @FXML private TableColumn<Instructor, String> instructorNationalId;
    @FXML private TableColumn<Instructor, String> instructorUserInfo;
    @FXML private TableColumn<Instructor, String> instructorBirthdate;
    @FXML private ComboBox<String> instructorSearchBy;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User tableview
    @FXML private TableView<UserInfo> userTableView;
    @FXML private TextField userSearchBox;
    @FXML private ComboBox<String> userSearchBy;
    @FXML private TableColumn<UserInfo, String> userUsername;
    @FXML private TableColumn<UserInfo, Role> userRole;
    //----------------------------------------------------------------


    //----------------------------------------------------------------Faculty tableview
    @FXML private TableView<Faculty> facultyTableView;
    @FXML private TextField facultySearchBox;
    @FXML private ComboBox<String> facultySearchBy;
    @FXML private TableColumn<Faculty, String> facultyName;
    //----------------------------------------------------------------


    //------------------------------------------------Edit instructor
    @FXML private TextField editInstructorId;
    @FXML private TextField editNationalId;
    @FXML private TextField editFirstName;
    @FXML private TextField editPhoneNumber;
    @FXML private TextField editEmail;
    @FXML private TextField editLastName;
    @FXML private DatePicker editBirthdate;
    @FXML public DatePicker editEntranceDate;
    @FXML private TextField editAddress;
    @FXML public TextField editFacultyName;
    @FXML public TextField editUsername;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToInstructorManagement;
    @FXML private Button goToEditInstructor;
    @FXML private Button selected;
    @FXML private Button goToDeleteInstructor;
    @FXML private Button gotoViewAllInstructors;
    @FXML private Button refreshTablesButton;


    @FXML private Label editInstructorHeader;
    @FXML private Label editNationalIdLabel;
    @FXML private Label editFirstNameLabel;
    @FXML private Label editPhoneNumberLabel;
    @FXML private Label editEmailLabel;
    @FXML private Label editLastNameLabel;
    @FXML private Label editBirthdateLabel;
    @FXML private Label editAddressLabel;
    @FXML private Label editFacultyNameLabel;
    @FXML private Label editUsernameLabel;
    @FXML private Label editEntranceDateLabel;
    @FXML private Label editInstructorIdLabel;

    @FXML private Button save;
    @FXML private Button showFreeTimes;
    @FXML private Label instructorManagementHeader;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static Instructor instructor;
    public static ObservableList<Instructor> instructors;
    public static boolean isUpTODate = true;
    public static ObservableList<Faculty> faculties;
    //----------------------------------------------------------------



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillInstructorTable(instructorId, instructorFirstName, instructorLastName, instructorNationalId,
                instructorPhoneNumber, instructorEmail, instructorAddress, instructorFaculty, instructorUserInfo,
                instructorBirthdate, instructorEntranceDate);
        fillInstructorSearch(instructorSearchBox, instructorSearchBy, instructorTableView);
        refreshInstructors(InstructorManagementController.instructors, instructorTableView);

        fillUserTable(userUsername,userRole);
        fillUserSearch(userSearchBox,userSearchBy,userTableView);
        refreshUsers(UserManagementController.users,userTableView);

        fillFacultyTable(facultyName);
        fillFacultySearch(facultySearchBox,facultySearchBy,facultyTableView);
        refreshFaculties(FacultyManagementController.faculties,facultyTableView);

        fillUserInterface(goBackToHomePage, goToInstructorManagement, goToEditInstructor, goToDeleteInstructor,
                gotoViewAllInstructors,menuButton,logOut);

        refreshUsers(UserManagementController.users,userTableView);
        refreshInstructors(InstructorManagementController.instructors,instructorTableView);
        refreshFaculties(FacultyManagementController.faculties,facultyTableView);

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------


        instructorTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                instructor = instructorTableView.getSelectionModel().getSelectedItem();
                editInstructorId.setText(instructor.getInstructorId() != null ? instructor.getInstructorId() : "");
                editNationalId.setText(instructor.getNationalId() != null ? instructor.getNationalId() : "");
                editFirstName.setText(instructor.getFirstName() != null ? instructor.getFirstName() : "");
                editLastName.setText(instructor.getLastName() != null ? instructor.getLastName() : "");
                editPhoneNumber.setText(instructor.getPhoneNumber() != null ? instructor.getPhoneNumber() : "");
                editEmail.setText(instructor.getEmail() != null ? instructor.getEmail() :"");

                if (instructor.getAddress() != null) {
                    editAddress.setText(instructor.getAddress());
                }else{
                    editAddress.setText("");
                }
                editBirthdate.setValue(instructor.getBirthdate());
                if (instructor.getUserInfo() != null) {
                    editUsername.setText(instructor.getUserInfo().getUsername());
                }else{
                    editUsername.setText("");
                }
                if (instructor.getFaculty() != null) {
                    editFacultyName.setText(instructor.getFaculty().getName());
                }else{
                    editFacultyName.setText("");
                }
                editEntranceDate.setValue(instructor.getEntranceDate());
            }
        });
        if(showFreeTimes != null){
            showFreeTimes.setOnAction((ActionEvent) -> {
                FreeTimeDeclarationController freeTimeDeclarationController =
                        new FreeTimeDeclarationController(menuButton.getScene());
                FreeTimeDeclarationController.calledFromHomePage = false;
                FreeTimeDeclarationController.readOnly = true;
                FreeTimeDeclarationController.instructor = instructorTableView.getSelectionModel().getSelectedItem();
                if(FreeTimeDeclarationController.calendarEntries == null){
                    FreeTimeDeclarationController.calendarEntries = instructor.getFreeTimes();
                }
                try {
                    freeTimeDeclarationController.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void edit() throws Exception {
        List<UserInfo> foundUsers = Objects.requireNonNull(UserManagementController.getUsers()).stream().
                filter(userInfo -> userInfo.getUsername() != null &&
                        userInfo.getUsername().equals(editUsername.getText().trim()))
                .collect(Collectors.toList());
        List<Faculty> foundFaculties = FacultyManagementController.faculties.stream().
                filter(faculty -> faculty.getName() != null &&
                        faculty.getName().equals(editFacultyName.getText().trim())).
                collect(Collectors.toList());
        if(checkConstraints()) {
            if (foundUsers.isEmpty()) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is no user with this ID!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("کاربری با این آی دی موجود نیست!");
                }
            } else if (foundUsers.size() > 1) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is more than one user with this ID!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("بیش از یک کاربر با این آی دی وجود دارد!");
                }
            } else if (foundFaculties.isEmpty()) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is no faculty with this name!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("دانشکده ایی با این نام وجود ندارد!");
                }
            } else if (foundFaculties.size() > 1) {
                if(BaseController.getLanguage().equals("English")){
                    makeErrorMassage("There is more than one faculty with this name!");
                } else if (BaseController.getLanguage().equals("Persian")){
                    makeErrorMassage("بیش از یک دانشکده با این نام وجود دارد!");
                }
            } else {
                instructor.setFaculty(foundFaculties.get(0));
                instructor.setUserInfo(foundUsers.get(0));
                setValues();
                new Thread(() -> InstructorManagementController.instructorRepository.save(instructor)).start();
                clear();
                new EditInstructorController(instructorTableView.getScene()).start();
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
        List<Instructor> foundInstructors = InstructorManagementController.instructors.stream().
                filter(instructor1 -> instructor1.getUserInfo() != null &&
                        instructor1.getUserInfo().getUsername().equals(editUsername.getText().trim())).
                collect(Collectors.toList());
        List<Instructor> foundInstructorsNationalId = InstructorManagementController.instructors.stream().
                filter(instructor -> instructor.getNationalId() != null &&
                        instructor.getNationalId().equalsIgnoreCase(editNationalId.getText().trim())).
                collect(Collectors.toList());
        if(editFirstName.getText().trim().length() < 2){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("First Name must be at least 2 characters!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام کوچک باید حداقل 2 حرف باشد!");
            }
        } else if(editLastName.getText().length() < 2){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Last Name must be at least 2 characters!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام خانوادگی باید حداقل 2 حرف باشد!");
            }
        } else  if(isNotValidEmail(editEmail.getText())){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Email address is not valid!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("ایمیل وارد شده قابل قبول نیست!");
            }
        } else if(foundInstructors.size() > 1 && !foundInstructors.get(0).getId().
                equals(instructor.getId())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Username is assigned to another instructor!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("یوسرنیم به کاربر دیگری داده شده است!");
            }
        } else if(foundInstructorsNationalId.size() > 1 && !foundInstructorsNationalId.get(0).getId().
                equals(instructor.getId())){
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
        instructor.setInstructorId(editInstructorId.getText().trim());
        instructor.setFirstName(editFirstName.getText().trim());
        instructor.setLastName(editLastName.getText().trim());
        instructor.setEmail(editEmail.getText().trim());
        instructor.setAddress(editAddress.getText().trim());
        instructor.setPhoneNumber(editPhoneNumber.getText().trim());
        instructor.setNationalId(editNationalId.getText());
        instructor.setBirthdate(editBirthdate.getValue());
        instructor.setEntranceDate(editEntranceDate.getValue());
    }

    private void clear(){
        clearTextFields(editInstructorId, editFacultyName, editFirstName, editLastName, editEmail, editAddress,
                editPhoneNumber, editNationalId);
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
        refreshTables(null,instructorTableView,null,facultyTableView,userTableView);
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

        fxmlID.add("editInstructorHeader");
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
        fxmlID.add("editInstructorIdLabel");
        fxmlID.add("showFreeTimes");
        fxmlID.add("universityManagementHeader");

        new LanguageConfiguration(fxmlID,text,"InstructorManagement");

        goBackToHomePage.setText(text.get(0));
        goToInstructorManagement.setText(text.get(1));
        selected.setText(text.get(2));
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

        editInstructorHeader.setText(text.get(23));
        editNationalIdLabel.setText(text.get(24));
        editFirstNameLabel.setText(text.get(25));
        editPhoneNumberLabel.setText(text.get(26));
        editEmailLabel.setText(text.get(27));
        editLastNameLabel.setText(text.get(28));
        editBirthdateLabel.setText(text.get(29));
        editAddressLabel.setText(text.get(30));
        editFacultyNameLabel.setText(text.get(31));
        editUsernameLabel.setText(text.get(32));
        editEntranceDateLabel.setText(text.get(33));

        save.setText(text.get(34));
        instructorManagementHeader.setText(text.get(35));
        editInstructorIdLabel.setText(text.get(36));

        showFreeTimes.setText(text.get(37));
        showFreeTimes.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        universityManagementHeader.setText(text.get(38));
    }
}
