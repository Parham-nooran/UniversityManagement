package ir.ac.kntu.universityManagement.controllers.general;

import ir.ac.kntu.universityManagement.UniversityManagementApplication;
import ir.ac.kntu.universityManagement.controllers.managers.UserManagementController;
import ir.ac.kntu.universityManagement.exceptions.InvalidLoginException;
import ir.ac.kntu.universityManagement.models.settings.Language;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.Notifications;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.util.Objects;

@Getter
@Setter
public class LoginController extends Application implements CallbackHandler {

    @FXML
    private TextArea errorMessage;
    @FXML
    private AnchorPane loginError;

    private ConfigurableApplicationContext applicationContext;
    private final LoginContext loginContext;

    @FXML
    public TextField username;
    @FXML
    public PasswordField password;

    public LoginController() throws LoginException {
        System.setProperty("java.security.auth.login.config", "jaasauth.config");
        loginContext = new LoginContext( "UM", this);
    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(UniversityManagementApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().
                getResourceAsStream("/icons/GeneralIcons/Logo1.jpg"))));
        stage.setTitle("University Management");
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    @Override
    public void handle(Callback[] callbacks) {
        NameCallback nameCallback;
        PasswordCallback passwordCallback;
        int counter = 0;
        while (counter < callbacks.length){
            if (callbacks[counter] instanceof  NameCallback){
                nameCallback = (NameCallback) callbacks[counter++];
                nameCallback.setName(username.getText());
            } else if (callbacks[counter] instanceof PasswordCallback){
                passwordCallback = (PasswordCallback) callbacks[counter++];
                passwordCallback.setPassword(password.getText().toCharArray());
            }
        }
    }

    public void login(){
        try {
            loginContext.login();
            new HomePageController(username.getScene()).start();
        } catch (InvalidLoginException invalidLoginException){
            Image ErrorNot = new Image(Objects.requireNonNull((HomePageController.class.getResourceAsStream("/icons/GeneralIcons/ErrorNot.png"))));
            if(BaseController.getLanguage().equals("English")) {
                Notifications notification = Notifications.create()
                        .title(" Error ! ")
                        .text(invalidLoginException.getMessage())
                        .graphic(new ImageView(ErrorNot))
                        .hideAfter(Duration.seconds(10))
                        .position(Pos.CENTER);
                notification.show();
            } else if (BaseController.getLanguage().equals("Persian")){
                Notifications notification = Notifications.create()
                        .title(" Error ! ")
                        .text("پسورد یا یوسرنیم وارد شده صحیح نیست")
                        .graphic(new ImageView(ErrorNot))
                        .hideAfter(Duration.seconds(10))
                        .position(Pos.CENTER);
                notification.show();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void signUp() throws Exception {
        new UserManagementController(username.getScene()).start();
    }

    public static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Object source) {
            super(source);
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}


