package ir.ac.kntu.universityManagement.controllers.partSpecfic;

import com.zaxxer.hikari.util.ConcurrentBag;
import ir.ac.kntu.universityManagement.controllers.deleters.DeleteUserController;
import ir.ac.kntu.universityManagement.controllers.editors.EditUserController;
import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.controllers.managers.UserManagementController;
import ir.ac.kntu.universityManagement.controllers.viewLists.ViewAllUsersController;
import ir.ac.kntu.universityManagement.models.auth.Role;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.entities.BaseEntity;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserController extends BaseController {

    public UserController(String viewName, Scene scene) {super(viewName,scene);}


    public void fillUserTable(TableColumn<UserInfo, String> userUsername, TableColumn<UserInfo , Role> userRole){
        userUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    public void fillUserSearch(TextField userSearchBox, ComboBox<String> userSearchBy, TableView<UserInfo> userTableview){
        if(BaseController.getLanguage().equals("English")) {
            userSearchBy.getItems().addAll(FXCollections.observableArrayList("Username", "Role"));
        } else if (BaseController.getLanguage().equals("Persian")){
            userSearchBy.getItems().addAll(FXCollections.observableArrayList("یوسرنیم", "نقش"));
        }
        userSearchBox.textProperty().addListener((observable, oldValue, newValue) -> userSearch(userSearchBox,userSearchBy,userTableview));
    }

    public void fillUserInterface(Button goBackToHomePage, Button goToUserManagement, Button goToEditUser, Button goToDeleteUser, Button goToViewAllUser, MenuButton menuButton, MenuItem logOut){
        UserInfo userInfo = HomePageController.user;
        if(userInfo != null) {
            menuButton.setText(userInfo.getUsername());
        }

        if(goBackToHomePage != null) {
            goBackToHomePage.setOnAction((ActionEvent) -> {
                try {
                    goBackToHomePage(menuButton.getScene());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToUserManagement != null) {
            goToUserManagement.setOnAction((ActionEvent) -> {
                try {
                    goToUserManagement(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToEditUser != null) {
            goToEditUser.setOnAction((ActionEvent) -> {
                try {
                    goToEditUser(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToDeleteUser != null) {
            goToDeleteUser.setOnAction((ActionEvent) -> {
                try {
                    goToDeleteUser(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(goToViewAllUser != null) {
            goToViewAllUser.setOnAction((ActionEvent) -> {
                try {
                    goToViewAllUser(menuButton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if (BaseController.getLanguage().equals("English")){
            logOut.setText("LogOut");
        } else if (BaseController.getLanguage().equals("Persian")){
            logOut.setText("خروج از حساب کاربری");
        }

        logOut.setOnAction((ActionEvent event) -> logOut(menuButton));
    }

    public void goToUserManagement(MenuButton menuButton) throws Exception {
        new UserManagementController(menuButton.getScene()).start();
    }

    public void goToEditUser(MenuButton menuButton) throws Exception {
        new EditUserController(menuButton.getScene()).start();
    }

    public void goToDeleteUser(MenuButton menuButton) throws Exception{
        new DeleteUserController(menuButton.getScene()).start();
    }

    public void goToViewAllUser(MenuButton menuButton) throws Exception{
        new ViewAllUsersController(menuButton.getScene()).start();
    }

}
