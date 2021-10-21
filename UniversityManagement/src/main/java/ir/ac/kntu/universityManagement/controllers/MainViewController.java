package ir.ac.kntu.universityManagement.controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Setter
@NoArgsConstructor
public class MainViewController{

    @FXML
    private Button students;

    private Stage stage;


    public MainViewController(Stage stage) {
        this.stage = stage;
    }



    public void goToStudents() throws IOException {
        new StudentController((Stage)students.getScene().getWindow()).start();
    }



    public void start() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
