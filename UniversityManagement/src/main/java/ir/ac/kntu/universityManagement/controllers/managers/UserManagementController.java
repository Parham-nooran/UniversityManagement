package ir.ac.kntu.universityManagement.controllers.managers;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.UserController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.repositories.UserInfoRepository;
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
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@NoArgsConstructor
public class UserManagementController extends UserController implements Initializable {

    //----------------------------------------------------------------Constructor
    public UserManagementController(Scene scene) {
        super("UserManagement", scene);
    }
    //----------------------------------------------------------------


    //--------------------------------------------------------User TableView
    @FXML private TableView<UserInfo> userTableView;
    @FXML private TableColumn<UserInfo, String> username;
    @FXML private TableColumn<UserInfo, Role> userRole;
    @FXML private TextField userSearchBox;
    @FXML private ComboBox<String> userSearchBy;
    //----------------------------------------------------------------


    //-------------------------------------------------------Adding user attributes
    @FXML private ComboBox<String> addUserRole;
    @FXML private TextField addRepeatPassword;
    @FXML private TextField addUserName;
    @FXML private TextField addPassword;
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
    @FXML private Label addUserHeaderLabel;
    @FXML private Label addUserRoleLabel;
    @FXML private Label addRepeatPasswordLabel;
    @FXML private Label addUserNameLabel;
    @FXML private Label addPasswordLabel;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static ObservableList<UserInfo> users;
    public static UserInfoRepository userInfoRepository;
    public static boolean isUpTODate = true;
    //----------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillUserTable(username,userRole);
        fillUserSearch(userSearchBox,userSearchBy, userTableView);
        addUserRole.getItems().addAll(Role.getValues());
        refreshUsers(UserManagementController.users,userTableView);
        fillUserInterface(goBackToHomePage, goToUserManagement, goToEditUser, goToDeleteUser, gotoViewAllUser,
                menuButton, logOut);

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            persianTranslate(fxmlID,text);
        }
        //---------------------------------------------
    }

    public static void refillUsers(){
        new Thread(() -> {
            users = FXCollections.observableArrayList(userInfoRepository.findAll());
            isUpTODate = true;
        }).start();
    }

    public void save() throws Exception {
        if(checkInformation()){
            UserInfo userInfo = new UserInfo(addUserName.getText().trim(),
                    BCrypt.hashpw(addPassword.getText().trim(), BCrypt.gensalt(10)),
                    Role.valueOf(addUserRole.getValue().toUpperCase()));
            new Thread(() -> userInfoRepository.save(userInfo)).start();
            UserManagementController.users.add(userInfo);
            new UserManagementController(userTableView.getScene());
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


    private Boolean checkInformation(){
        if(UserManagementController.getUsers() == null){
            UserManagementController.users = FXCollections.observableArrayList();
        }
        List<UserInfo> foundUsers = Objects.requireNonNull(UserManagementController.getUsers()).stream().
                filter(userInfo1 -> userInfo1.getUsername() != null &&
                        userInfo1.getUsername().equals(addUserName.getText().trim())).
                collect(Collectors.toList());
        if(!addPassword.getText().equals(addRepeatPassword.getText())){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Password doesn't match the repeated password!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("رمز تکرار شده با رمز وارد شده برابر نیست!");
            }
        } else if(addUserRole.getValue() == null){
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Role is not specified!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نقش مشخص نشده نمی تواند خالی باشد!");
            }
        } else if(addUserName.getText().trim().equals("")) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Username is not valid");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام کاربری قابل قبول نیست!");
            }
        } else if(addPassword.getText().trim().length() < 4) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Password must be at least 4 characters!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("رمز باید حداقل 4 حرف باشد!");
            }
        } else if(!foundUsers.isEmpty()) {
            if(BaseController.getLanguage().equals("English")){
                makeErrorMassage("Username is already taken!");
            } else if (BaseController.getLanguage().equals("Persian")){
                makeErrorMassage("نام کاربری به کاربر دیگری نسبت داده شده است!");
            }
        } else {
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

    public static List<UserInfo> getUsers(){
        return users != null ? new ArrayList<>(users) : null;
    }
    public static long getUsersSize(){
        return userInfoRepository.count();
    }
    public static long getNumberOfUsers(){
        return Long.parseLong(users.size()+"");
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
        selected.setText(text.get(1));
        goToEditUser.setText(text.get(2));
        goToDeleteUser.setText(text.get(3));
        gotoViewAllUser.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));
        userManagementHeader.setText(text.get(6));

        username.setText(text.get(7));
        userRole.setText(text.get(8));
        userSearchBy.setPromptText(text.get(9));

        addUserHeaderLabel.setText(text.get(10));
        addUserRoleLabel.setText(text.get(11));
        addRepeatPasswordLabel.setText(text.get(12));
        addUserNameLabel.setText(text.get(13));
        addPasswordLabel.setText(text.get(14));
        save.setText(text.get(15));
        universityManagementHeader.setText(text.get(16));
    }
}
