package ir.ac.kntu.universityManagement.controllers;

import ir.ac.kntu.universityManagement.UniversityManagementApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.HashMap;
import java.util.Map;


public class LoginController extends Application {

    private ConfigurableApplicationContext applicationContext;

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    private final Map<String, String> passwd = new HashMap<String, String>(){{
        put("Parham", "1234");
        put("Test", "testy");
    }};

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(UniversityManagementApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    public void checkAuthentication() throws Exception {
        String message = "";
        if (!passwd.containsKey(userName.getText())){
            message ="The user name is not correct!";
        }
        else if(!passwd.get(userName.getText()).equals(password.getText())){
            message = "The password is incorrect";
        }
        if (message.equals("")){
            System.out.println(userName.getScene().getWindow() == null);
            new HomePageController((Stage)userName.getScene().getWindow()).start();
        }
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


