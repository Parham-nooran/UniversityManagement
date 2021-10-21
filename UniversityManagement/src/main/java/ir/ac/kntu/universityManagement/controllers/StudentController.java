package ir.ac.kntu.universityManagement.controllers;

import ir.ac.kntu.universityManagement.models.entities.individuals.Student;
import ir.ac.kntu.universityManagement.models.repositories.StudentRepository;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Setter
@NoArgsConstructor
public class StudentController {

    public static StudentRepository studentRepository;

    private Stage stage;

    public StudentController(Stage stage){
        this.stage = stage;
    }


    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;




    public  void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/StudentView.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }



    public void saveStudent(){
        studentRepository.save(new Student(this.firstName.getText(), this.lastName.getText(), this.email.getText()));
    }



}
