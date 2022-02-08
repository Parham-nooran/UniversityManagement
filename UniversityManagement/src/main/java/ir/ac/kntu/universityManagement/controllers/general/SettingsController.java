package ir.ac.kntu.universityManagement.controllers.general;

import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class SettingsController extends BaseController implements Initializable {

    //--------------------------------------------------Constructor
    public SettingsController(Scene scene){super("Setting",scene);}
    //--------------------------------------------------


    @FXML private Button homePageButton;
    @FXML private Button darkThemeButton;
    @FXML private Button lightThemeButton;
    @FXML private Button persianLanguageButton;
    @FXML private Button englishLanguageButton;
    @FXML private Button selected;
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Label settingHeaderLabel;
    @FXML private Label universityManagementHeader;
    @FXML private Button largeFontSizeButton;
    @FXML private Button mediumFontSizeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UserInfo userInfo = HomePageController.user;
        menuButton.setText(userInfo.getUsername());

        if (BaseController.getLanguage().equals("Persian")){
            darkThemeButton.setText("پوسته تاریک");
            lightThemeButton.setText("پوسته روشن");
            homePageButton.setText("خانه");
            logOut.setText("خروج از حساب کاربری");
            selected.setText("تنظیمات");
            settingHeaderLabel.setText("تنظیمات");
            universityManagementHeader.setText("مدیریت\nدانشگاه");
            largeFontSizeButton.setText("فونت بزرگ");
            mediumFontSizeButton.setText("فونت متوسط");
        }

    }

    public void changeLanguageToPersian() throws Exception {
        BaseController.setLanguage("Persian");
        new SettingsController(menuButton.getScene()).start();
    }

    public void changeLanguageToEnglish() throws Exception {
        BaseController.setLanguage("English");
        new SettingsController(menuButton.getScene()).start();
    }

    public void changeToDarkTheme() throws Exception {
        BaseController.setTheme("darkTheme");
        new SettingsController(menuButton.getScene()).start();
    }

    public void changeToLightTheme() throws Exception {
        BaseController.setTheme("lightTheme");
        new SettingsController(menuButton.getScene()).start();
    }

    public void changeFontSizeToLarge() throws Exception{
        BaseController.setFontSizeName("largeFont");
        new SettingsController(menuButton.getScene()).start();
    }

    public void changeFontSizeToMedium() throws Exception{
        BaseController.setFontSizeName("mediumFont");
        new SettingsController(menuButton.getScene()).start();
    }

    public void setHomePageButton() throws Exception {
        goBackToHomePage(menuButton.getScene());
    }

    public void setLogOut(){
        logOut(menuButton);
    }
}
