package ir.ac.kntu.universityManagement.controllers.viewLists;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.managers.UserManagementController;
import ir.ac.kntu.universityManagement.controllers.partSpecfic.UserController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@NoArgsConstructor
public class ViewAllUsersController extends UserController implements Initializable {

    //----------------------------------------------------------------Constructor
    public ViewAllUsersController(Scene scene){super("ViewAllUsers",scene);}
    //----------------------------------------------------------------


    //----------------------------------------------------------------Table view of Users
    @FXML private TableView<UserInfo> userTableView;
    @FXML private TableColumn<UserInfo, String> username;
    @FXML private TableColumn<UserInfo, Role> userRole;
    @FXML private TextField userSearchBox;
    @FXML private ComboBox<String> userSearchBy;
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
    @FXML private Label userManagementHeader;
    @FXML private Button refreshTablesButton;
    @FXML private Label universityManagementHeader;
    //----------------------------------------------------------------


    //----------------------------------------------------------------This controller variables
    public static boolean isUpTODate = true;
    //----------------------------------------------------------------



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillUserTable(username,userRole);
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
        fxmlID.add("universityManagementHeader");

        new LanguageConfiguration(fxmlID,text,"UserManagement");

        goBackToHomePage.setText(text.get(0));
        goToUserManagement.setText(text.get(1));
        goToEditUser.setText(text.get(2));
        goToDeleteUser.setText(text.get(3));
        selected.setText(text.get(4));
        refreshTablesButton.setText(text.get(5));
        userManagementHeader.setText(text.get(6));

        username.setText(text.get(7));
        userRole.setText(text.get(8));
        userSearchBy.setPromptText(text.get(9));
        universityManagementHeader.setText(text.get(10));
    }
}
