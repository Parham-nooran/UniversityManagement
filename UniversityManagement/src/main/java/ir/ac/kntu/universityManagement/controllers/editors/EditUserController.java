package ir.ac.kntu.universityManagement.controllers.editors;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.managers.InstructorManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.StudentManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.UserManagementController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.UserController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.individuals.Instructor;
import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.settings.Language;
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
import lombok.Setter;
import org.controlsfx.control.Notifications;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@NoArgsConstructor
@Setter
public class EditUserController extends UserController implements Initializable {

    //----------------------------------------------------------------Constructor
    public EditUserController(Scene scene) {super("EditUser", scene);}
    //----------------------------------------------------------------


    //----------------------------------------------------------------Table view
    @FXML private TableView<UserInfo> userTableView;
    @FXML private TableColumn<UserInfo, String> userUsername;
    @FXML private TableColumn<UserInfo, Role> userRole;
    @FXML private TextField userSearchBox;
    @FXML private ComboBox<String> userSearchBy;
    //----------------------------------------------------------------


    //-----------------------------------------------------------------Edit user
    @FXML private TextField editUsername;
    @FXML private PasswordField editPassword;
    @FXML private PasswordField editRepeatPassword;
    @FXML private ComboBox<String> editRole;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToUserManagement;
    @FXML private Button goToEditUser;
    @FXML private Button goToDeleteUser;
    @FXML private Button gotoViewAllUser;
    @FXML private Button selected;
    @FXML private Button save;
    @FXML private Label userManagementHeader;
    @FXML private Button refreshTablesButton;
    @FXML private Label editUserHeaderLabel;
    @FXML private Label editUserRoleLabel;
    @FXML private Label editRepeatPasswordLabel;
    @FXML private Label editUserNameLabel;
    @FXML private Label editPasswordLabel;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static UserInfo userInfo;
    public static UserInfo user;
    public static ObservableList<UserInfo> users;
    public static boolean isUpTODate = true;
    //----------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillUserTable(userUsername,userRole);
        fillUserSearch(userSearchBox,userSearchBy, userTableView);
        refreshUsers(UserManagementController.users,userTableView);
        editRole.getItems().addAll(Role.getValues());
        fillUserInterface(goBackToHomePage,goToUserManagement,goToEditUser,goToDeleteUser,gotoViewAllUser,menuButton,logOut);

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------

        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                user = userTableView.getSelectionModel().getSelectedItem();
                if (user.getUsername() != null){
                    editUsername.setText(user.getUsername());
                }else{
                    editUsername.setText("");
                }
                if (user.getRole() != null){
                    editRole.setValue(String.valueOf(user.getRole()));
                }else{
                    editRole.setValue(null);
                }

            }
        });

    }

    public void edit() throws Exception {
        if(checkInformation()) {
        user.setUsername(editUsername.getText().trim());
        user.setPassword(BCrypt.hashpw(editPassword.getText().trim(), BCrypt.gensalt(10)));
        user.setRole(Role.valueOf(editRole.getValue().toUpperCase()));
        new Thread(() -> UserManagementController.userInfoRepository.save(user)).start();
        editUsername.setText("");
        editPassword.setText("");
        editRepeatPassword.setText("");
        editRole.setValue(null);
        new EditUserController(userTableView.getScene()).start();
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

    private Boolean checkInformation() {
        List<UserInfo> foundUsers = Objects.requireNonNull(UserManagementController.getUsers()).stream().
                filter(userInfo1 -> userInfo1.getUsername() != null &&
                        userInfo1.getUsername().equals(editUsername.getText().trim())).
                collect(Collectors.toList());
        List<Instructor> foundInstructors = InstructorManagementController.instructors.stream().
                filter(instructor -> instructor.getUserInfo() != null &&
                        instructor.getUserInfo().getUsername().equals(editUsername.getText().trim())).
                collect(Collectors.toList());
        List<Student> foundStudents = StudentManagementController.students.stream().
                filter(student -> student.getUserInfo() != null &&
                        student.getUserInfo().getUsername().equals(editUsername.getText().trim())).
                collect(Collectors.toList());
        if (!editPassword.getText().equals(editRepeatPassword.getText())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Password doesn't match the repeated password!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("رمز وارد شده با رمز تکرار شده برار نیستند!");
            }
        } else if (editUsername.getText().trim().equals("")) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Username is not valid");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("یوسرنیم قابل قبول نیست!");
            }
        } else if (editPassword.getText().trim().length() < 4) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Password must be at  least 4 characters!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("رمز وارد شده حداقل باید 4 حرف باشد!");
            }
        } else if (foundUsers.size() > 1 && !foundUsers.get(0).getId().equals(userInfo.getId())) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Username is already taken!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام کاربری قبلا استفاده شده است!");
            }
        } else if (editRole.getValue() == null) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Role is not specified!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نقش مشخص نشده است!");
            }
        } else if (!foundInstructors.isEmpty() &&
                !Role.valueOf(editRole.getValue().toUpperCase()).equals(Role.INSTRUCTOR)) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Could not change role because username is assigned to an instructor!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نمی توان نقش را تغییر داد چون نام کاربری به عنوان استاد وارد سیستم شده است!");
            }
        } else if (!foundStudents.isEmpty() &&
                !Role.valueOf(editRole.getValue().toUpperCase()).equals(Role.STUDENT)) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Could not change role because username is assigned to a student!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نمی توان نقش را تغییر داد چون نام کاربری به عنوان دانشجو وارد سیستم شده است!");
            }
        } else {
            return true;
        }
        return false;
    }


    private static Image ErrorNot = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/GeneralIcons/ErrorNot.png"))));
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
        refreshTables(null,null,null,null,userTableView);
    }

    private void persianTranslate(ArrayList<String> fxmlID,ArrayList<String> text){

        fxmlID.add("goBackToHomePage");
        fxmlID.add("goToUserManagement");
        fxmlID.add("goToEditUser");
        fxmlID.add("goToDeleteUser");
        fxmlID.add("gotoViewAllUser");
        fxmlID.add("refreshTablesButton");
        fxmlID.add("userManagementHeader");

        fxmlID.add("username");
        fxmlID.add("userRole");
        fxmlID.add("userSearchBy");

        fxmlID.add("addUserHeaderLabel");
        fxmlID.add("addUserRoleLabel");
        fxmlID.add("addRepeatPasswordLabel");
        fxmlID.add("addUserNameLabel");
        fxmlID.add("addPasswordLabel");
        fxmlID.add("save");
        fxmlID.add("universityManagementHeader");


        new LanguageConfiguration(fxmlID,text,"UserManagement");

        goBackToHomePage.setText(text.get(0));
        goToUserManagement.setText(text.get(1));
        selected.setText(text.get(2));
        goToDeleteUser.setText(text.get(3));
        gotoViewAllUser.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));
        userManagementHeader.setText(text.get(6));

        userUsername.setText(text.get(7));
        userRole.setText(text.get(8));
        userSearchBy.setPromptText(text.get(9));

        editUserHeaderLabel.setText(text.get(10));
        editUserRoleLabel.setText(text.get(11));
        editRepeatPasswordLabel.setText(text.get(12));
        editUserNameLabel.setText(text.get(13));
        editPasswordLabel.setText(text.get(14));
        save.setText(text.get(15));
        universityManagementHeader.setText(text.get(16));
    }
}
