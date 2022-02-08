package ir.ac.kntu.universityManagement.controllers.deleters;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.UserController;
import ir.ac.kntu.universityManagement.controllers.managers.InstructorManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.StudentManagementController;
import ir.ac.kntu.universityManagement.controllers.managers.UserManagementController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@NoArgsConstructor
public class DeleteUserController extends UserController implements Initializable {


    //----------------------------------------------------------------Constructor
    public DeleteUserController(Scene scene) {
        super("DeleteUser", scene);
    }
    //----------------------------------------------------------------


    //--------------------------------------------------------Tableview
    @FXML private TableView<UserInfo> userTableView;
    @FXML private TableColumn<UserInfo, String> userUsername;
    @FXML private TableColumn<UserInfo, Role> userRole;
    @FXML private TextField userSearchBox;
    @FXML private ComboBox<String> userSearchBy;
    //----------------------------------------------------------------


    //----------------------------------------------------------------User interface
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private AnchorPane areYouSure;
    @FXML private Button goBackToHomePage;
    @FXML private Button goToUserManagement;
    @FXML private Button goToEditUser;
    @FXML private Button goToDeleteUser;
    @FXML private Button gotoViewAllUser;
    @FXML private Button selected;
    @FXML private Button delete;
    @FXML private Label userManagementHeader;
    @FXML private Button refreshTablesButton;
    @FXML private Label youSure;
    @FXML private Button yes;
    @FXML private Button no;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------



    //----------------------------------------------------------------This controller variables
    public static ObservableList<UserInfo> users;
    public static UserInfo user;
    public static boolean isUpTODate = true;
    //----------------------------------------------------------------



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillUserTable(userUsername,userRole);
        fillUserSearch(userSearchBox,userSearchBy, userTableView);
        refreshUsers(UserManagementController.users,userTableView);

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
            }
        });
    }

    public void  deleteUser(){
        new Thread(() -> UserManagementController.userInfoRepository.deleteById(user.getId())).start();
        UserManagementController.users.remove(user);
        InstructorManagementController.isUpTODate = false;
        StudentManagementController.isUpTODate = false;
        InstructorManagementController.refillInstructors();
        StudentManagementController.refillStudents();
        areYouSure.setVisible(false);
    }

    public void delete(){
        if(user != null) {
            areYouSure.setVisible(true);
        }
    }

    public void no(){
        areYouSure.setVisible(false);
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

        fxmlID.add("delete");
        fxmlID.add("youSure");
        fxmlID.add("yes");
        fxmlID.add("no");

        fxmlID.add("universityManagementHeader");



        new LanguageConfiguration(fxmlID,text,"UserManagement");

        goBackToHomePage.setText(text.get(0));
        goToUserManagement.setText(text.get(1));
        goToEditUser.setText(text.get(2));
        selected.setText(text.get(3));
        gotoViewAllUser.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));
        userManagementHeader.setText(text.get(6));

        userUsername.setText(text.get(7));
        userRole.setText(text.get(8));
        userSearchBy.setPromptText(text.get(9));

        delete.setText(text.get(10));
        youSure.setText(text.get(11));
        yes.setText(text.get(12));
        no.setText(text.get(13));

        universityManagementHeader.setText(text.get(14));

    }
}
