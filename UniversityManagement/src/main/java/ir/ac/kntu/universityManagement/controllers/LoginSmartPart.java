package ir.ac.kntu.universityManagement.controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


public class LoginSmartPart extends Application {

    public static void main(String[] args){
        launch(args);
    }


    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    private Map<String, String> passwd = new HashMap<String, String>(){{
        put("Parham", "1234");
        put("Test", "testy");
    }};

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginSmartPart.fxml"));
        StudentSmartPart.stage = stage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void checkAuthentication() throws Exception {
        String message = "";
        if (!passwd.containsKey(userName.getText())){message ="The user name is not correct!";}
        else if(!passwd.get(userName.getText()).equals(password.getText()))
        {message = "The password is incorrect";}
        if (message.equals("")){StudentSmartPart.start();}
    }



}
