package ir.ac.kntu.universityManagement.controllers.general;

import ir.ac.kntu.universityManagement.configuration.LanguageConfiguration;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static ir.ac.kntu.universityManagement.controllers.general.HomePageController.applicationContext;

@NoArgsConstructor
public class AboutController extends BaseController implements Initializable {

    //-------------------------------------------------Constructor
    public AboutController(Stage stage, Scene scene) {super("About" ,scene);}
    //-------------------------------------------------

    //-------------------------------------------------View components
    @FXML private Label aboutUsHeader;
    @FXML private Button homePageButton;
    @FXML private Button aboutUsButton;
    @FXML private Label aboutUsAppName;
    @FXML private Label aboutAppHeader;
    @FXML private Label aboutUsEmail;
    @FXML private Label aboutUsPhoneNumber;
    @FXML private TextArea textArea;
    @FXML private MenuButton menuButton;
    @FXML private MenuItem logOut;
    @FXML private Label universityManagementHeader;
    //-------------------------------------------------


    public void goBack() throws Exception {
        new HomePageController(textArea.getScene()).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserInfo userInfo = HomePageController.user;
        logOut.setOnAction((ActionEvent event) -> {
            abort();
            applicationContext.publishEvent(new LoginController.StageReadyEvent(textArea.getScene().getWindow()));
        });

        menuButton.setText(userInfo.getUsername());

        //---------------------------------------------Persian translate part
        ArrayList<String> fxmlID = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>();

        if(BaseController.getLanguage().equals("Persian")) {
            fxmlID.add("aboutUsHeader");
            fxmlID.add("homePageButton");
            fxmlID.add("aboutUsButton");
            fxmlID.add("aboutUsAppName");
            fxmlID.add("aboutAppHeader");
            fxmlID.add("aboutTextArea");
            fxmlID.add("aboutUsEmail");
            fxmlID.add("aboutUsPhoneNumber");

            new LanguageConfiguration(fxmlID, text, "About");

            aboutUsHeader.setText(text.get(0));
            homePageButton.setText(text.get(1));
            aboutUsButton.setText(text.get(2));
            aboutUsAppName.setText(text.get(3));
            aboutAppHeader.setText(text.get(4));
            aboutAppHeader.setAlignment(Pos.CENTER_RIGHT);
            textArea.setText(text.get(5));
            textArea.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            aboutUsEmail.setText(text.get(6));
            aboutUsPhoneNumber.setText(text.get(7));
            logOut.setText("خروج از حساب کاربری");
            universityManagementHeader.setText("مدیریت\nدانشگاه");
        }
    }
}
